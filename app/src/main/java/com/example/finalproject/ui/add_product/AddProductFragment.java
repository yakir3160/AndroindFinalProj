package com.example.finalproject.ui.add_product;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
        // Instantiate ViewModel
        AddProductViewModel addProductViewModel =
                new ViewModelProvider(this).get(AddProductViewModel.class);

        // Use View Binding to inflate the layout
        binding = FragmentAddProductBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Setup Spinner with adapter
        Spinner spinner = binding.spinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Set OnClickListener for the button to add product to the database
        binding.addToDbButton.setOnClickListener(v -> {
            String productName = binding.addProductNameText.getText().toString();
            String productCategory = spinner.getSelectedItem().toString();

            // Simple input validation
            if (!productName.isEmpty() && !productCategory.isEmpty()) {
                // Reset red border if validation succeeds
                resetEditTextBorder(binding.addProductNameText);

                Product product = new Product(productName, productCategory);
                ProductRepository.getInstance(getContext()).addProductToDb(product);

                // Clear input fields after adding the product
                binding.addProductNameText.setText("");
                spinner.setSelection(0); // Reset spinner to the first item (if applicable)

                // Optionally show a confirmation message
                Toast.makeText(getContext(), "Product added successfully", Toast.LENGTH_SHORT).show();
            } else {
                // Show a toast message if validation fails
                Toast.makeText(getContext(), "Please enter product name and select a category", Toast.LENGTH_SHORT).show();

                // Highlight the product name field with a red border
                highlightEditTextBorder(binding.addProductNameText);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Set binding to null to prevent memory leaks
        binding = null;
    }
    private void highlightEditTextBorder(View editText) {
        // Set a red border around the EditText
        Drawable drawable = ContextCompat.getDrawable(requireContext(), R.drawable.edit_text_error_background);
        editText.setBackground(drawable);
    }



    private void resetEditTextBorder(View editText) {
        editText.setBackground(null); // Reset to default background
    }
}
