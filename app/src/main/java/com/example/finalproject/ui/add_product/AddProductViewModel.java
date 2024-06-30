package com.example.finalproject.ui.add_product;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddProductViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AddProductViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("this is Add Product Fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}