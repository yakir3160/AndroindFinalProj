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

// MainActivity is the entry point of the application
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Use View Binding to inflate the layout
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Set up the bottom navigation view
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Configure the app bar
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_shopping_list, R.id.navigation_all_products, R.id.navigation_add_product)
                .build();

        // Set up navigation controller
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        // Set up the action bar with the nav controller
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Connect the bottom navigation view with the nav controller
        NavigationUI.setupWithNavController(binding.navView, navController);
    }


}