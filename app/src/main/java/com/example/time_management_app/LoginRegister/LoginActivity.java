package com.example.time_management_app.LoginRegister;

import static android.service.controls.ControlsProviderService.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.time_management_app.Interface.ApiService;
import com.example.time_management_app.MainActivity;
import com.example.time_management_app.Utils.RetrofitClient;
import com.example.time_management_app.Utils.TokenManage;
import com.example.time_management_app.databinding.ActivityLoginBinding;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {


    private TextView txtSignIn;
    private EditText use_email_enter,use_password_enter;
    private MaterialButton sign_in;
    private ProgressBar progressBar;


ActivityLoginBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       initvariable();
        sign_in.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            final  String User_email = use_email_enter.getText().toString().trim();
            final  String User_pswd = use_password_enter.getText().toString().trim();

            if (TextUtils.isEmpty(User_email)){
                use_email_enter.setError("Email is Require");
                return;
            }
            if(TextUtils.isEmpty(User_pswd)){
                use_password_enter.setError("Password is Require");
                return;
            }


            if (User_pswd.length()<6){
                use_password_enter.setError("Password must be greater than 6 char ");
                return;
            }
            loginToAccount(User_email,User_pswd);

        });



        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }



    private void loginToAccount(String user_email, String use_pasword) {
        Map<String, String> params = new HashMap<>();

        try {
            params.put("password",use_pasword);
            params.put("email",user_email);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ApiService apiService = RetrofitClient.getApiService(this);

        Call<ResponseBody> call = apiService.login(params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Handle success
                    try {
                        String jsonResponse = response.body().string();
                        JSONObject jsonObject = new JSONObject(jsonResponse);
                        String token = jsonObject.getString("token");
                        Log.e(TAG, "onResponse: Token ye hai kya : "+token );
                        TokenManage.saveToken(getApplicationContext(),token);
//                        Log.e(TAG, "onResponse: maze aagye "+jsonResponse.toString() );

                    } catch (Exception e) {
                        Log.e(TAG, "onResponse: error hogya 22"+e.toString() );
                        throw new RuntimeException(e);
                    }




                    progressBar.setVisibility(View.INVISIBLE);
                    sign_in.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    sign_in.setVisibility(View.VISIBLE);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    // Handle error
                    Log.e(TAG, "onResponse: Error occurred: " + response.code());
                    try {
                        Log.e(TAG, "onResponse: "+response.errorBody().string() );
                        Log.e(TAG, "onResponse: "+call.toString() );
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Log.e(TAG, "onResponse: "+response.toString() );
                    progressBar.setVisibility(View.INVISIBLE);
                    sign_in.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: Error occurred", t);
                progressBar.setVisibility(View.INVISIBLE);
                sign_in.setVisibility(View.VISIBLE);
            }
        });
    }

    private
    void initvariable() {
        txtSignIn=binding.txtSignIn;


        use_password_enter=binding.edtSignInPassword;
        use_email_enter = binding.edtSignInEmail;
        sign_in = binding.btnSignIn;
        progressBar = binding.signInProgressBar;

    }
}