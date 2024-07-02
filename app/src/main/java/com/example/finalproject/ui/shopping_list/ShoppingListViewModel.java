package com.example.finalproject.ui.shopping_list;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.finalproject.Product;
import com.example.finalproject.data.ProductRepository;

import java.util.List;

public class ShoppingListViewModel extends ViewModel {
    private ProductRepository productRepository;

    public ShoppingListViewModel() {
        productRepository = ProductRepository.getInstance(null);
    }

    public void init(Application application) {
        if (productRepository == null) {
            productRepository = ProductRepository.getInstance(application);
        }
    }

    public LiveData<List<Product>> getShoppingList() {
        return productRepository.getShoppingListLiveData();
    }

    public void removeProductFromShoppingList(Product product) {
        productRepository.removeProductFromShoppingList(product);
    }
}