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

public class AllProductsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private ProductRepository productRepository;

    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_all_products, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProductAdapter();
        recyclerView.setAdapter(adapter);

        // Initialize ProductRepository and observe changes
        productRepository = ProductRepository.getInstance();
        productRepository.getProductsLiveData().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                adapter.setProducts(products);
            }
        });

        return root;
    }
}
