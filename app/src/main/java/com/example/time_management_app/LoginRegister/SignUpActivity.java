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
import com.example.time_management_app.Utils.RetrofitClient;
import com.example.time_management_app.databinding.ActivitySignUpBinding;

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


public class SignUpActivity extends AppCompatActivity {

   private TextView txtSignUp;
   private EditText use_name,use_email,use_password1,use_cmpassword;
   private MaterialButton sign_up;
   private ProgressBar progressBar;
    private
    ActivitySignUpBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initVariable();

       // if user exist cookies then go to home page
        signUp();




        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void signUp() {
        sign_up.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            final  String User_name = use_name.getText().toString().trim();
            final  String User_email = use_email.getText().toString().trim();
            final  String User_cmpswd = use_cmpassword.getText().toString().trim();
            final  String User_pswd = use_password1.getText().toString().trim();
            if (TextUtils.isEmpty(User_name)){
                use_name.setError("Name is Require");
                return;
            }
            if (TextUtils.isEmpty(User_email)){
                use_email.setError("Email is Require");
                return;
            }
            if(TextUtils.isEmpty(User_pswd)){
                use_password1.setError("Password is Require");
                return;
            }
            if(TextUtils.isEmpty(User_cmpswd)){
                use_cmpassword.setError("Password is Require");
                return;
            }
            if (!(User_pswd.equals(User_cmpswd))){
                use_cmpassword.setError("not match with above");
                use_cmpassword.setText("");
                return;
            }
            if (User_pswd.length()<6){
                use_password1.setError("Password must be greater than 6 char ");
                return;
            }
            Toast.makeText(this, "Register processing", Toast.LENGTH_SHORT).show();
            createUser(User_name,User_email,User_pswd,User_cmpswd);
        });
    }

    private void createUser(String user_name, String user_email, String user_pswd, String user_cmpswd) {
        Map<String,String> params = new HashMap<>();
        try {
            params.put("firstName",user_name);
            params.put("email",user_email);
            params.put("password",user_pswd);
            params.put("confromPassword",user_cmpswd);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ApiService apiService = RetrofitClient.getApiService(this);

        Call<ResponseBody> call = apiService.signUp(params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Handle success
                    try {
                        String jsonResponse = response.body().string();
                        JSONObject jsonObject = new JSONObject(jsonResponse);
                        Log.e(TAG, "onResponse: maze aagye "+jsonObject.toString() );

                    } catch (Exception e) {
                        Log.e(TAG, "onResponse: error hogya 22"+e.toString() );
                        throw new RuntimeException(e);
                    }
                    ResponseBody responseObject = response.body();
                    Log.e(TAG, "onResponse: Successfully fetched" + responseObject);
                    progressBar.setVisibility(View.INVISIBLE);
                    sign_up.setVisibility(View.VISIBLE);
                    Toast.makeText(SignUpActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    sign_up.setVisibility(View.VISIBLE);
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                } else {
                    // Handle error
                    Log.e(TAG, "onResponse: Error occurred: " + response.code());
                    Log.e(TAG, "onResponse: "+response.toString() );
                    try {
                        Log.e(TAG, "onResponse: "+response.errorBody().string() );
                        Log.e(TAG, "onResponse: "+call.toString() );
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    sign_up.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: Error occurred", t);
                progressBar.setVisibility(View.INVISIBLE);
                sign_up.setVisibility(View.VISIBLE);
            }
        });

    }

    private
    void initVariable() {
        txtSignUp = binding.txtSignUp;
        use_name=binding.edtSignUpFullName;
        use_password1=binding.edtSignUpPassword;
        use_cmpassword = binding.edtSignUpConfirmPassword;
        use_email = binding.edtSignUpEmail;
        sign_up = binding.btnSignUp;
        progressBar = binding.signUpProgressBar;

    }
}