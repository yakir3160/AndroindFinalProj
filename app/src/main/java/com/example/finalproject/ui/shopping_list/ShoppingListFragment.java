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

public class ShoppingListFragment extends Fragment {

        private FragmentShoppingListBinding binding;
        private ShoppingListViewModel shoppingListViewModel;
        private ShoppingListAdapter adapter;

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            binding = FragmentShoppingListBinding.inflate(inflater, container, false);
            View root = binding.getRoot();

            shoppingListViewModel = new ViewModelProvider(this).get(ShoppingListViewModel.class);
            shoppingListViewModel.init(requireActivity().getApplication());

            setupRecyclerView();
            observeShoppingList();

            return root;
        }

        private void setupRecyclerView() {
            adapter = new ShoppingListAdapter(requireContext(), new ArrayList<>());
            binding.recyclerViewShoppingList.setAdapter(adapter);
            binding.recyclerViewShoppingList.setLayoutManager(new LinearLayoutManager(requireContext()));

            adapter.setOnRemoveButtonClickListener(product -> {
                shoppingListViewModel.removeProductFromShoppingList(product);
            });
        }

        private void observeShoppingList() {
            shoppingListViewModel.getShoppingList().observe(getViewLifecycleOwner(), products -> {
                adapter.setShoppingList(products);
            });
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }
    }