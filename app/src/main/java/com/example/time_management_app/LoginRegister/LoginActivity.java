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


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.time_management_app.MainActivity;
import com.example.time_management_app.Utils.Utils;
import com.example.time_management_app.databinding.ActivityLoginBinding;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;


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
            // login to your account
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

    private void loginToAccount(String user_email, String user_pswd) {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("email",user_email);
            jsonObject.put("password",user_pswd);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        try {
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS) // Set the connection timeout
                    .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)    // Set the read timeout
                    .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)   // Set the write timeout
                    .build();
            AndroidNetworking.post(Utils.BASE_URL+"auth/login")
                    .addJSONObjectBody(jsonObject)
                    .setPriority(Priority.HIGH)
                    .setOkHttpClient(okHttpClient)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e(TAG, "onResponse: SuccessFully frtch"+response.toString() );
                            Toast.makeText(LoginActivity.this, "Login SuccessFully ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.e(TAG, "onError: Error 2 hogya bhai : "+anError.toString() );
                        }
                    });
            progressBar.setVisibility(View.INVISIBLE);



        }catch (Exception e){
            Log.e(TAG, "loginToAccount: "+e.toString() );
        }
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