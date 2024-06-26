package com.example.finalproject.ui.all_products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.databinding.FragmentAllProductsBinding;
import com.example.finalproject.ProductAdapter;
import com.example.finalproject.Product;

import java.util.ArrayList;
import java.util.List;

public class AllProductsFragment extends Fragment {

    private AllProductsModel allProductsModel;
    private FragmentAllProductsBinding binding;
    private ProductAdapter productAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAllProductsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set up RecyclerView
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Example list of products
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Zero Coke"));
        productList.add(new Product("Buns"));
        productList.add(new Product("Milk"));
        productList.add(new Product("Eggs"));
        productList.add(new Product("Bread"));
        productList.add(new Product("Butter"));
        productList.add(new Product("Cheese"));
        productList.add(new Product("Yogurt"));
        productList.add(new Product("Apples"));
        productList.add(new Product("Bananas"));
        productList.add(new Product("Oranges"));
        productList.add(new Product("Tomatoes"));
        productList.add(new Product("Potatoes"));
        productList.add(new Product("Chicken"));
        productList.add(new Product("Beef"));
        productList.add(new Product("Pasta"));
        productList.add(new Product("Rice"));
        productList.add(new Product("Cereal"));
        productList.add(new Product("Juice"));
        productList.add(new Product("Water"));


        // Set up adapter
        productAdapter = new ProductAdapter(productList, getContext());
        recyclerView.setAdapter(productAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Release binding when fragment view is destroyed
    }
}
