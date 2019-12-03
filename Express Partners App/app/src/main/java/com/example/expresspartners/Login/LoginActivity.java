package com.example.expresspartners.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expresspartners.API.Api;
import com.example.expresspartners.MainActivity;
import com.example.expresspartners.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rengwuxian.materialedittext.MaterialEditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements LoginView{

    @Inject
    LoginPresenter presenter;

    @BindView(R.id.rollNoEditText)
    MaterialEditText email;
    @BindView(R.id.passwordEditText)
    MaterialEditText pass;
    @BindView(R.id.signIn)
    Button login;
    @BindView(R.id.signUp)
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        presenter.setLoginView(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.authenticateLogin(email.getText().toString(),pass.getText().toString());
            }
        });
    }

    @Override
    public void loginSuccess(String user) {
        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("user",user);
        startActivity(intent);
    }

    @Override
    public void loginFailure() {
        Toast.makeText(getApplicationContext(),"Wrong Password",Toast.LENGTH_SHORT).show();
    }
}
