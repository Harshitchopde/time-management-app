package com.example.time_management_app.LoginRegister;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.time_management_app.databinding.ActivitySignUpBinding;

import com.google.android.material.button.MaterialButton;


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