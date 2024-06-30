package com.example.finalproject;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.finalproject.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

        private ActivityMainBinding binding;
        private FirebaseFirestore db;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            db = FirebaseFirestore.getInstance();

            BottomNavigationView navView = findViewById(R.id.nav_view);

            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_all_products, R.id.navigation_shopping_list, R.id.navigation_predefined_lists)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(binding.navView, navController);

//                addProductsToFirestore();
        }


//    private void addProductsToFirestore() {
//        String[] productsArray = getResources().getStringArray(R.array.product_list);
//        List<Product> products = new ArrayList<>();
//
//        for (String productInfo : productsArray) {
//            String[] parts = productInfo.split(", ");
//            String name = parts[0];
//            String category = parts[1];
//            products.add(new Product(name, category));
//        }
//
//        for (Product product : products) {
//            db.collection("products").add(product)
//                    .addOnSuccessListener(documentReference -> {
//                        String id = documentReference.getId();
//                        product.setId(id);
//                        documentReference.update("id", id);
//                        Toast.makeText(MainActivity.this, "Product added: " + id, Toast.LENGTH_SHORT).show();
//                    })
//                    .addOnFailureListener(e ->
//                            Toast.makeText(MainActivity.this, "Error adding product: " + e.getMessage(), Toast.LENGTH_SHORT).show()
//                    );
//        }
//        productsAdded = true;
//    }
}
