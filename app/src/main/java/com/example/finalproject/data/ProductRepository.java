package com.example.finalproject.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.finalproject.Product;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private static ProductRepository instance;
    private final FirebaseFirestore db;
    private final MutableLiveData<List<Product>> productsLiveData;

    private ProductRepository() {
        db = FirebaseFirestore.getInstance();
        productsLiveData = new MutableLiveData<>();
        fetchProducts();
    }

    public static synchronized ProductRepository getInstance() {
        if (instance == null) {
            instance = new ProductRepository();
        }
        return instance;
    }

    public LiveData<List<Product>> getProductsLiveData() {
        return productsLiveData;
    }

    private void fetchProducts() {
        db.collection("products")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Product> products = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Product product = documentSnapshot.toObject(Product.class);
                        products.add(product);
                    }
                    productsLiveData.setValue(products);
                })
                .addOnFailureListener(e -> {
                    // Handle error fetching products
                });
    }
}
