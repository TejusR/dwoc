package com.example.srmhack1;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserApi {
    @POST("register")
    Call<String> requestRegister(@Body User body);

    @POST("login")
    Call<String> requestLogin(@Body User body);

    @PUT("properties/update")
    Call<String> requestUpdate(@Body User body);
}
