package com.example.finalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> products;
    private List<Product> shoppingList;
    private ProductClickListener listener;

    public ProductAdapter() {
        // Initialize product and shopping list arrays
        this.products = new ArrayList<>();
        this.shoppingList = new ArrayList<>();
    }

    // Set click listener for handling product actions
    public void setProductClickListener(ProductClickListener listener) {
        this.listener = listener;
    }

    // Update product list and notify the adapter
    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    // Update shopping list and notify the adapter
    public void setShoppingList(List<Product> shoppingList) {
        this.shoppingList = shoppingList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the product item layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        // Bind the product data to the view holder
        Product product = products.get(position);
        holder.bind(product, isInShoppingList(product));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    // Check if a product is in the shopping list
    private boolean isInShoppingList(Product product) {
        for (Product shoppingItem : shoppingList) {
            if (shoppingItem.getId().equals(product.getId())) {
                return true;
            }
        }
        return false;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private Button addToShoppingListButton;
        private ProductClickListener listener;

        public ProductViewHolder(@NonNull View itemView, ProductClickListener listener) {
            super(itemView);
            // Initialize views
            nameTextView = itemView.findViewById(R.id.productNameTextView);
            addToShoppingListButton = itemView.findViewById(R.id.addToShoppingListButton);
            this.listener = listener;
        }

        // Bind product data to views and handle button state
        public void bind(final Product product, boolean isInShoppingList) {
            nameTextView.setText(product.getName());

            if (isInShoppingList) {
                // Disable button if product is already in the shopping list
                addToShoppingListButton.setText("Added");
                addToShoppingListButton.setEnabled(false);
            } else {
                // Enable button and set click listener to add product to shopping list
                addToShoppingListButton.setText("Add to Shopping List");
                addToShoppingListButton.setEnabled(true);
                addToShoppingListButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onAddToShoppingListClicked(product);
                        }
                    }
                });
            }
        }
    }

    // Interface for handling product click actions
    public interface ProductClickListener {
        void onAddToShoppingListClicked(Product product);
    }
}
