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
import com.example.time_management_app.databinding.ActivitySignUpBinding;

import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;


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
            // create user api call
            progressBar.setVisibility(View.VISIBLE);
            createUserWith(User_name,User_email,User_pswd,User_cmpswd);
        });
    }

    private void createUserWith(String user_name, String user_email, String user_pswd, String user_cmpswd) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("firstName",user_name);
            jsonObject.put("email",user_email);
            jsonObject.put("password",user_pswd);
            jsonObject.put("confromPassword",user_cmpswd);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        try {
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS) // Set the connection timeout
                    .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)    // Set the read timeout
                    .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)   // Set the write timeout
                    .build();
            AndroidNetworking.post(Utils.BASE_URL+"auth/signUp")
                    .addJSONObjectBody(jsonObject)
                    .setPriority(Priority.HIGH)
                    .setOkHttpClient(okHttpClient)
//                    .setExecutor(Executors.newSingleThreadExecutor())

                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e(TAG, "onResponse: SuccessFully frtch"+response.toString() );
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(SignUpActivity.this, "SuccessFully Register", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.e(TAG, "onError: Error hogya bhai : "+anError.toString() );
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });


        }catch (Exception exception){
            Log.e(TAG, "createUserWith: "+exception.toString() );
        }

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