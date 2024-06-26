package com.example.finalproject.ui.all_products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.finalproject.databinding.FragmentAllProductsBinding;

public class AllProductsFragment extends Fragment {

    private FragmentAllProductsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AllProductsModel homeViewModel =
                new ViewModelProvider(this).get(AllProductsModel.class);

        binding = FragmentAllProductsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.te
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}