package com.example.expresspartners.Login;

import com.example.expresspartners.API.Api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginPresenterImpl implements LoginPresenter {
    LoginView view;

    @Override
    public void setLoginView(LoginView view) {
        this.view = view;
    }

    @Override
    public void authenticateLogin(final String email, String password) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        final Api myapi=retrofit.create(Api.class);
        Call<LoginResponseModel> call=myapi.requestLogin(email,password);
        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                if(response.body().getRes()=="success login")
                    view.loginSuccess(email);
                else
                    view.loginFailure();

            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {

            }
        });
    }
}
