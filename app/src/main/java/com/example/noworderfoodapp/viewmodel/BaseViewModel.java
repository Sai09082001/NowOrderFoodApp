package com.example.noworderfoodapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BaseViewModel extends ViewModel {
    protected final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    protected MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void setStatus(String id,String status){
        DatabaseReference reference = firebaseDatabase.getReference().child("users-reg")
                .child(id).child("status");
        reference.setValue(status);

    }

}
