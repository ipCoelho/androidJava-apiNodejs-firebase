package com.example.java_nodejs_firebase.remote;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ImageInterface {
    @FormUrlEncoded

    @POST("/upload")
    Call<String>uploadImage(
            @Field("img") String file,
            @Field("title") String titulo
    );
}
