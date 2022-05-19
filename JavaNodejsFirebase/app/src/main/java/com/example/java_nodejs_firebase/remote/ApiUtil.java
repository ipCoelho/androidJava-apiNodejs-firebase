package com.example.java_nodejs_firebase.remote;

public class ApiUtil {
    public static final String API_URL = "http://10.107.144.8:3000";

    public static ImageInterface getUploadInterface() {
        return RetrofitClient.getClient(API_URL).create(ImageInterface.class);
    }
}
