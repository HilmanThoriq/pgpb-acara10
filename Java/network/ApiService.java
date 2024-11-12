package com.example.retrofit10.network;
import android.provider.ContactsContract;

import com.example.retrofit10.model.Data;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("5371")
    Call<List<Data>> getAllUsers();

}
