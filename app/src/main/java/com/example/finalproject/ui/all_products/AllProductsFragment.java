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
        productList.add(new Product("Zero coke"));
        productList.add(new Product("Buns"));

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
