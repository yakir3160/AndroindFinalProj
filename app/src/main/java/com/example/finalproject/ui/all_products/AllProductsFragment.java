package com.example.finalproject.ui.all_products;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finalproject.Product;
import com.example.finalproject.R;

import com.example.finalproject.ProductAdapter;
import com.example.finalproject.data.ProductRepository;

import java.util.List;

/**
 * AllProductsFragment displays a list of all available products and allows users
 * to add products to their shopping list.
 */
public class AllProductsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private ProductRepository productRepository;

    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_all_products, container, false);

        // Initialize RecyclerView and its adapter
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProductAdapter();

        // Set up click listener for adding products to shopping list
        adapter.setProductClickListener(new ProductAdapter.ProductClickListener() {
            @Override
            public void onAddToShoppingListClicked(Product product) {
                productRepository.addProductToShoppingList(product);
            }
        });
        recyclerView.setAdapter(adapter);

        // Get instance of ProductRepository
        productRepository = ProductRepository.getInstance(getContext());

        // Observe changes in the products list
        productRepository.getProductsLiveData().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                updateAdapter(products, productRepository.getShoppingListLiveData().getValue());
            }
        });

        // Observe changes in the shopping list
        productRepository.getShoppingListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> shoppingList) {
                updateAdapter(productRepository.getProductsLiveData().getValue(), shoppingList);
            }
        });

        return root;
    }

    /**
     * Updates the adapter with the latest product and shopping list data.
     * This ensures that the UI reflects the current state of both lists,
     * particularly for disabling "Add to Shopping List" buttons for products
     * already in the shopping list.
     *
     * @param products The list of all available products
     * @param shoppingList The current shopping list
     */
    private void updateAdapter(List<Product> products, List<Product> shoppingList) {
        if (products != null) {
            adapter.setProducts(products);
        }
        if (shoppingList != null) {
            adapter.setShoppingList(shoppingList);
        }
    }
}