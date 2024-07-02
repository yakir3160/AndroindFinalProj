package com.example.finalproject.ui.shopping_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.finalproject.data.ProductRepository;
import com.example.finalproject.databinding.FragmentShoppingListBinding;

import java.util.ArrayList;

// This fragment displays the user's shopping list
public class ShoppingListFragment extends Fragment {

    private FragmentShoppingListBinding binding;
    private ShoppingListViewModel shoppingListViewModel;
    private ShoppingListAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment using view binding
        binding = FragmentShoppingListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize the ViewModel
        shoppingListViewModel = new ViewModelProvider(this).get(ShoppingListViewModel.class);
        shoppingListViewModel.init(requireActivity().getApplication());

        // Set up the RecyclerView
        setupRecyclerView();

        // Observe changes to the shopping list
        observeShoppingList();

        return root;
    }

    // Configure the RecyclerView and its adapter
    private void setupRecyclerView() {
        adapter = new ShoppingListAdapter(requireContext(), new ArrayList<>());
        binding.recyclerViewShoppingList.setAdapter(adapter);
        binding.recyclerViewShoppingList.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Set up the click listener for the remove button
        adapter.setOnRemoveButtonClickListener(product -> {
            shoppingListViewModel.removeProductFromShoppingList(product);
        });
    }

    // Observe changes to the shopping list and update the UI accordingly
    private void observeShoppingList() {
        shoppingListViewModel.getShoppingList().observe(getViewLifecycleOwner(), products -> {
            adapter.setShoppingList(products);
        });
    }

    // Clean up the binding when the view is destroyed
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}