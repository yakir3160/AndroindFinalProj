package com.example.finalproject.ui.predefined_lists;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.finalproject.databinding.FragmentPredefinedListsBinding;

public class PredefinedListsFragment extends Fragment {

    private FragmentPredefinedListsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PredefinedListsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(PredefinedListsViewModel.class);

        binding = FragmentPredefinedListsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textPredefinedLists;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}