package com.example.retrofit10.model;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Users {
    @SerializedName("data")
    private List<Data> data;

    public List<Data> getData(){
        return data;
    }
}
