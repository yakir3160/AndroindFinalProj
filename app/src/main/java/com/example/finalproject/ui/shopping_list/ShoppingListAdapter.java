package com.example.finalproject.ui.shopping_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Product;
import com.example.finalproject.R;

import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {

    private List<Product> shoppingList;
    private Context context;
    private OnRemoveButtonClickListener removeButtonClickListener;

    public ShoppingListAdapter(Context context, List<Product> shoppingList) {
        this.context = context;
        this.shoppingList = shoppingList;
    }

    public interface OnRemoveButtonClickListener {
        void onRemoveButtonClick(Product product);
    }

    public void setOnRemoveButtonClickListener(OnRemoveButtonClickListener listener) {
        this.removeButtonClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopping_list_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = shoppingList.get(position);
        holder.productNameTextView.setText(product.getName());

        holder.removeFromShoppingListButton.setOnClickListener(v -> {
            if (removeButtonClickListener != null) {
                removeButtonClickListener.onRemoveButtonClick(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shoppingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView;
        Button removeFromShoppingListButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            removeFromShoppingListButton = itemView.findViewById(R.id.removeFromShoppingListButton);
        }
    }
}
