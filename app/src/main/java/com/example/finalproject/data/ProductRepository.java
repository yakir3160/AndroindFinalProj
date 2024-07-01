package com.example.finalproject.data;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.finalproject.Product;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private static ProductRepository instance;
    private FirebaseFirestore db;
    private MutableLiveData<List<Product>> productsLiveData;
    private MutableLiveData<List<Product>> shoppingListLiveData;
    private CollectionReference productsCollection;
    private CollectionReference shoppingListCollection;
    private static Context context;

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
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
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
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
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

    public void addProductToShoppingList(Product product) {
        shoppingListCollection.add(product)
                .addOnSuccessListener(documentReference -> {
                    showToast("Product added to shopping list");
                    removeProductFromProducts(product.getId());
                })
                .addOnFailureListener(e -> {
                    showToast("Failed to add product to shopping list");
                });
    }

    private void removeProductFromProducts(String productId) {
        productsCollection.document(productId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    showToast("Product removed from products");
                })
                .addOnFailureListener(e -> {
                    showToast("Failed to remove product from products");
                });
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
