package com.example.finalproject.ui.predefined_lists;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PredefinedListsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PredefinedListsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is predefined lists fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}