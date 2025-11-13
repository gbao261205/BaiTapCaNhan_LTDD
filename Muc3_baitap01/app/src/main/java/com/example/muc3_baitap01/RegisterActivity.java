package com.example.muc3_baitap01;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editTextEmail;
    EditText editTextPassword;
    ImageButton btnArrow;
    TextView tvForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title not the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//int flag, int mask
        setContentView(R.layout.activity_register);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnArrow = findViewById(R.id.btnArrow);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);

        btnArrow.setOnClickListener(v -> {
            editTextName.setText("");
            editTextEmail.setText("");
            editTextPassword.setText("");
            // Direct back to login activity
            finish();
        });

        tvForgotPassword.setOnClickListener(v -> {
            editTextEmail.setText("");
            editTextPassword.setText("");
            editTextName.setText("");
        });

    }
}