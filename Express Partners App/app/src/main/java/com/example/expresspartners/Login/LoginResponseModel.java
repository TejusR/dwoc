package com.example.expresspartners.Login;

import com.google.gson.annotations.SerializedName;

public class LoginResponseModel {

    @SerializedName("error_message")
    String errMess;
    @SerializedName("response")
    String res;

    public LoginResponseModel(String errMess, String res) {
        this.errMess = errMess;
        this.res = res;
    }

    public String getErrMess() {
        return errMess;
    }

    public String getRes() {
        return res;
    }
}
