package com.example.srmhack1;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface UserApi {
    @FormUrlEncoded
    @POST("register")
    Call<String> requestRegister(@Field("email") String email, @Field("username") String username, @Field("fullname") String fullname, @Field("age") int age, @Field("gender") char gender, @Field("phone_number") String phone_number, @Field("home_location") String home_location, @Field("password") String password, @Field("password2") String password2);

    @FormUrlEncoded
    @POST("login")
    Call<String> requestLogin(@Field("email") String email,@Field("password") String password);

    @FormUrlEncoded
    @POST("properties/update")
    Call<String> requestUpdate(@Field("email") String email,@Field("CO2") String CO2,@Field("rating") String rating,@Field("distance") String distance,@Field("status") String status);

    @GET("logout")
    Call<String> requestLogout();

    @GET("properties")
    Call<User> requestProperties(@Query("email") String email);

    @FormUrlEncoded
    @POST("ride_setup")
    Call<String> requestSetup(@Field("email") String email,@Field("user_type") String user_type,@Field("nop") int nop,@Field("start_location") String start_location,@Field("end_location") String end_location,@Field("start_time") String start_time,@Field("end_time") String end_time);

    @GET("json")
    Call<String> requestDistance(@Query("key") String key);
}
