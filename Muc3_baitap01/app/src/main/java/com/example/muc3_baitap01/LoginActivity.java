package com.example.muc3_baitap01;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText editTextTextEmail;
    EditText editTextTextPassword;
    ImageButton btnArrow;
    LinearLayout btnFacebook;
    LinearLayout btnGoogle;
    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title not the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//int flag, int mask
        setContentView(R.layout.activity_login);

        editTextTextEmail = findViewById(R.id.editTextTextEmail);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        btnArrow = findViewById(R.id.btnArrow);
        btnFacebook = findViewById(R.id.btnFacebook);
        btnGoogle = findViewById(R.id.btnGoogle);
        tvRegister = findViewById(R.id.tvRegister);

        //Click Button Arrow
        btnArrow.setOnClickListener(view -> {
            editTextTextEmail.setText("");
            editTextTextPassword.setText("");
        });
        //Click Button Facebook
        btnFacebook.setOnClickListener(view -> {
            editTextTextEmail.setText("");
            editTextTextPassword.setText("");
        });
        //Click Button Google
        btnGoogle.setOnClickListener(view -> {
            editTextTextEmail.setText("");
            editTextTextPassword.setText("");
        });
        //Click Text Register
        tvRegister.setOnClickListener(view -> {
            //Direct to Register Page
            editTextTextEmail.setText("");
            editTextTextPassword.setText("");
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}