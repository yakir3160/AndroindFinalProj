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
    private ProductClickListener listener;

    public ProductAdapter() {
        this.products = new ArrayList<>();
    }

    public void setProductClickListener(ProductClickListener listener) {
        this.listener = listener;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private Button addToShoppingListButton;
        private ProductClickListener listener;

        public ProductViewHolder(@NonNull View itemView, ProductClickListener listener) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.productNameTextView);
            addToShoppingListButton = itemView.findViewById(R.id.addToShoppingListButton);
            this.listener = listener;
        }

        public void bind(final Product product) {
            nameTextView.setText(product.getName());
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

    public interface ProductClickListener {
        void onAddToShoppingListClicked(Product product);
    }
}