package com.example.srmhack1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoginActivity extends AppCompatActivity {
    User user = new User();
    TextView email,pass;
    Button login,signup;
    UserApi userApi;
    Call<String> mCall;
    final Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.4.59.94:8000/account/api/")
                .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        email = (TextView)findViewById(R.id.rollNoEditText);
        pass = (TextView)findViewById(R.id.passwordEditText);
        login = (Button)findViewById(R.id.signIn);
        signup = (Button)findViewById(R.id.signUp);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().equalsIgnoreCase(""))
                    email.setError("Please provide email");
                else if(pass.getText().toString().equalsIgnoreCase(""))
                    pass.setError("Please provide password");
                else {
                    userApi = retrofit.create(UserApi.class);
                    User user1 = new User();
                    user1.username = email.getText().toString();
                    user1.password = pass.getText().toString();
                    mCall = userApi.requestLogin(user1);
                    mCall.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.body()!=null) {
                                String res = response.body();
                                //Toast.makeText(getApplicationContext(),res,Toast.LENGTH_LONG).show();
                                String []arr = res.split(",");
                                String []item = arr[1].split(":");
                                String []item1 = arr[0].split(":");
                                String v = item[1].replace("}","") + " " +  item1[1].replace("}","");
                                Toast.makeText(getApplicationContext(),v,Toast.LENGTH_LONG).show();
                                if(v.contains("Success")){
                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.remove("email");
                                    editor.putString("email",email.getText().toString());
                                    editor.commit();
                                }
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Response Null",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();

                        }
                    });

                }

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });



    }
}
