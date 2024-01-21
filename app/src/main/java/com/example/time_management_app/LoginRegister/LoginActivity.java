package com.example.time_management_app.LoginRegister;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.time_management_app.databinding.ActivityLoginBinding;
import com.google.android.material.button.MaterialButton;


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

    private
    void initvariable() {
        txtSignIn=binding.txtSignIn;


        use_password_enter=binding.edtSignInPassword;
        use_email_enter = binding.edtSignInEmail;
        sign_in = binding.btnSignIn;
        progressBar = binding.signInProgressBar;

    }
}