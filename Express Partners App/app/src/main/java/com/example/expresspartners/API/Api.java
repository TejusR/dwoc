package com.example.expresspartners.API;

import com.example.expresspartners.Login.LoginResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    String BASE_URL = "localhost:8000/";

    @FormUrlEncoded
    @POST("register")
    Call<String> requestRegister(@Field("email") String email, @Field("username") String username, @Field("fullname") String fullname, @Field("age") int age, @Field("gender") char gender, @Field("phone_number") String phone_number, @Field("home_location") String home_location, @Field("password") String password, @Field("password2") String password2);

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponseModel> requestLogin(@Field("email") String email, @Field("password") String password);
}
