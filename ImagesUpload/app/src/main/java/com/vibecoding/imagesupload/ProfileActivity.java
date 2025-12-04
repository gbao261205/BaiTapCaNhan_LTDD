package com.vibecoding.imagesupload;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileActivity extends AppCompatActivity {

    ImageView ivProfile;
    TextView tvId;
    TextView tvUsername;
    TextView tvFullName;
    TextView tvEmail;
    TextView tvGender;
    Button btnLogout;

    private static final int USER_ID = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Ánh xạ
        ivProfile = findViewById(R.id.ivProfile);
        tvId = findViewById(R.id.tvId);
        tvUsername = findViewById(R.id.tvUsername);
        tvFullName = findViewById(R.id.tvFullName);
        tvEmail = findViewById(R.id.tvEmail);
        tvGender = findViewById(R.id.tvGender);
        btnLogout = findViewById(R.id.btnLogout);

        //Xử lý khi ấn vào hình
        ivProfile.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            intent.putExtra("USER_ID", USER_ID);
            startActivity(intent);
        });

        PerformLoadData();
    }

    private void PerformLoadData() {

    }
}