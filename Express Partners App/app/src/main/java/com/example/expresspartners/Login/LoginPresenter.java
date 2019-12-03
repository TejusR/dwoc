package com.example.expresspartners.Login;

public interface LoginPresenter {
    void setLoginView(LoginView view);

    void authenticateLogin(String email, String password);
}
