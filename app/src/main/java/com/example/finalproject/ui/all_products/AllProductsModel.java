package com.example.finalproject.ui.all_products;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AllProductsModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AllProductsModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is all products fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}