package com.example.finalproject.ui.add_product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.finalproject.databinding.FragmentAddProductBinding;

public class AddProductFragment extends Fragment {

    private FragmentAddProductBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddProductViewModel predefinedListsViewModel =
                new ViewModelProvider(this).get(AddProductViewModel.class);

        binding = FragmentAddProductBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textAddProduct;
        predefinedListsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}