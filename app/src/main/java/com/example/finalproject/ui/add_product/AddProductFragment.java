package com.example.finalproject.ui.add_product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.finalproject.R;
import com.example.finalproject.data.ProductRepository;
import com.example.finalproject.databinding.FragmentAddProductBinding;
import com.example.finalproject.Product;

public class AddProductFragment extends Fragment {

    private FragmentAddProductBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddProductViewModel addProductViewModel =
                new ViewModelProvider(this).get(AddProductViewModel.class);

        binding = FragmentAddProductBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Spinner spinner = binding.spinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        binding.addToDbButton.setOnClickListener(v -> {
            String productName = binding.addProductNameText.getText().toString();
            String productCategory = spinner.getSelectedItem().toString();

            if (!productName.isEmpty() && !productCategory.isEmpty()) {
                Product product = new Product(productName, productCategory);
                ProductRepository.getInstance(getContext()).addProductToDb(product);
            } else {
                Toast.makeText(getContext(), "Please enter product name and select a category", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
