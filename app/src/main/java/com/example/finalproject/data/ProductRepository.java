package com.example.finalproject.data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.finalproject.Product;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    // Singleton instance
    private static ProductRepository instance;

    // Firebase and LiveData fields
    private FirebaseFirestore db;
    private MutableLiveData<List<Product>> productsLiveData;
    private MutableLiveData<List<Product>> shoppingListLiveData;
    private CollectionReference productsCollection;
    private CollectionReference shoppingListCollection;
    private Context context;

    // Private constructor for singleton pattern
    private ProductRepository(Context context) {
        if (context != null) {
            this.context = context.getApplicationContext();
        }
        initializeFirebase();
        initializeLiveData();
        fetchInitialData();
    }

    // Singleton getInstance method
    public static synchronized ProductRepository getInstance(Context context) {
        if (instance == null) {
            instance = new ProductRepository(context);
        } else if (context != null && instance.context == null) {
            instance.context = context.getApplicationContext();
        }
        return instance;
    }

    // Initialization methods
    private void initializeFirebase() {
        db = FirebaseFirestore.getInstance();
        productsCollection = db.collection("products");
        shoppingListCollection = db.collection("shoppingList");
    }

    private void initializeLiveData() {
        productsLiveData = new MutableLiveData<>();
        shoppingListLiveData = new MutableLiveData<>();
    }

    private void fetchInitialData() {
        getProducts();
        getShoppingList();
    }

    // LiveData getters
    public LiveData<List<Product>> getProductsLiveData() {
        return productsLiveData;
    }

    public LiveData<List<Product>> getShoppingListLiveData() {
        return shoppingListLiveData;
    }

    // Fetch methods
    private void getProducts() {
        productsCollection.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Product> products = new ArrayList<>();
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Product product = documentSnapshot.toObject(Product.class);
                        if (product != null) {
                            products.add(product);
                        }
                    }
                    productsLiveData.setValue(products);
                })
                .addOnFailureListener(e -> logMessage("Failed to fetch products: " + e.getMessage()));
    }

    private void getShoppingList() {
        shoppingListCollection.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Product> shoppingList = new ArrayList<>();
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Product product = documentSnapshot.toObject(Product.class);
                        if (product != null) {
                            shoppingList.add(product);
                        }
                    }
                    shoppingListLiveData.setValue(shoppingList);
                })
                .addOnFailureListener(e -> logMessage("Failed to fetch shopping list: " + e.getMessage()));
    }

    // Product management methods
    public void addProductToDb(final Product product) {
        productsCollection.whereEqualTo("name", product.getName()).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        String id = productsCollection.document().getId();
                        product.setId(id);
                        productsCollection.document(id).set(product)
                                .addOnSuccessListener(aVoid -> {
                                    logMessage("Product added successfully");
                                    getProducts();
                                })
                                .addOnFailureListener(e -> logMessage("Failed to add product: " + e.getMessage()));
                    } else {
                        logMessage("Product with the same name already exists");
                    }
                })
                .addOnFailureListener(e -> logMessage("Failed to check if product exists: " + e.getMessage()));
    }

    // Shopping list management methods
    public void addProductToShoppingList(Product product) {
        shoppingListCollection.document(product.getId()).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (!documentSnapshot.exists()) {
                        shoppingListCollection.document(product.getId()).set(product)
                                .addOnSuccessListener(aVoid -> {
                                    getShoppingList();
                                })
                                .addOnFailureListener(e -> logMessage("Failed to add product to shopping list: " + e.getMessage()));
                    }
                    // If the product already exists, we do nothing and don't show any message
                })
                .addOnFailureListener(e -> logMessage("Failed to check if product exists in shopping list: " + e.getMessage()));
    }

    public void removeProductFromShoppingList(Product product) {
        shoppingListCollection.document(product.getId()).delete()
                .addOnSuccessListener(aVoid -> {
                    getShoppingList();
                })
                .addOnFailureListener(e -> logMessage("Failed to remove product from shopping list: " + e.getMessage()));
    }

    // Utility method for logging and displaying messages
    private void logMessage(String message) {
        Log.d("ProductRepository", message);
        if (context != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
}