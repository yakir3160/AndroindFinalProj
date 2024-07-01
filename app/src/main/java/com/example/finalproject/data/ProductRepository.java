package com.example.finalproject.data;

import android.content.Context;
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

    private static ProductRepository instance;
    private FirebaseFirestore db;
    private MutableLiveData<List<Product>> productsLiveData;
    private MutableLiveData<List<Product>> shoppingListLiveData;
    private CollectionReference productsCollection;
    private CollectionReference shoppingListCollection;
    private Context context;

    private ProductRepository(Context context) {
        this.context = context.getApplicationContext(); // Use application context
        db = FirebaseFirestore.getInstance();
        productsCollection = db.collection("products");
        shoppingListCollection = db.collection("shoppingList");
        productsLiveData = new MutableLiveData<>();
        shoppingListLiveData = new MutableLiveData<>();
        getProducts();
        getShoppingList();
    }

    public static synchronized ProductRepository getInstance(Context context) {
        if (instance == null) {
            instance = new ProductRepository(context);
        }
        return instance;
    }

    public LiveData<List<Product>> getProductsLiveData() {
        return productsLiveData;
    }

    public LiveData<List<Product>> getShoppingListLiveData() {
        return shoppingListLiveData;
    }

    private void getProducts() {
        productsCollection.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Product> products = new ArrayList<>();
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Product product = documentSnapshot.toObject(Product.class);
                        products.add(product);
                    }
                    productsLiveData.setValue(products);
                    showToast("Products fetched successfully");
                })
                .addOnFailureListener(e -> {
                    showToast("Failed to fetch products");
                });
    }

    private void getShoppingList() {
        shoppingListCollection.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Product> shoppingList = new ArrayList<>();
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Product product = documentSnapshot.toObject(Product.class);
                        shoppingList.add(product);
                    }
                    shoppingListLiveData.setValue(shoppingList);
                    showToast("Shopping list fetched successfully");
                })
                .addOnFailureListener(e -> {
                    showToast("Failed to fetch shopping list");
                });
    }

    public void addProductToDb(final Product product) {
        productsCollection.whereEqualTo("name", product.getName()).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        // Create a new document with an auto-generated ID
                        String id = productsCollection.document().getId();
                        product.setId(id); // Set the document ID to the product

                        // Add the product with the generated document ID
                        productsCollection.document(id).set(product)
                                .addOnSuccessListener(aVoid -> {
                                    showToast("Product added successfully");
                                    getProducts(); // Refresh the product list
                                })
                                .addOnFailureListener(e -> {
                                    showToast("Failed to add product");
                                });
                    } else {
                        showToast("Product with the same name already exists");
                    }
                })
                .addOnFailureListener(e -> {
                    showToast("Failed to check if product exists");
                });
    }

    public void addProductToShoppingList(Product product) {
        // Check if product already exists in shopping list
        shoppingListCollection.document(product.getId()).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        showToast("Product is already in the shopping list");
                    } else {
                        // Add product to shopping list
                        shoppingListCollection.document(product.getId()).set(product)
                                .addOnSuccessListener(aVoid -> {
                                    showToast("Product added to shopping list");
                                    getShoppingList(); // Refresh the shopping list
                                })
                                .addOnFailureListener(e -> {
                                    showToast("Failed to add product to shopping list");
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    showToast("Failed to check if product exists in shopping list");
                });
    }

    public void removeProductFromShoppingList(Product product) {
        shoppingListCollection.document(product.getId()).delete()
                .addOnSuccessListener(aVoid -> {
                    showToast("Product removed from shopping list");
                    getShoppingList(); // Refresh the shopping list
                })
                .addOnFailureListener(e -> {
                    showToast("Failed to remove product from shopping list");
                });
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
