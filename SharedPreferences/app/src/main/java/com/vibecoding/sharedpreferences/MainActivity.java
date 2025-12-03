package com.vibecoding.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Khai báo các view
    EditText etUsername, etPassword;
    CheckBox cbRemember;
    Button btnLogin;

    // Khai báo SharedPreferences và tên file lưu trữ
    SharedPreferences sharedPreferences;
    // Tên file: "LoginPrefs" (tự đặt)
    private static final String PREFS_NAME = "LoginPrefs";

    // Các Key để lưu dữ liệu
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_REMEMBER = "isRemember";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Ánh xạ View
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        cbRemember = findViewById(R.id.cbRemember);
        btnLogin = findViewById(R.id.btnLogin);

        // 2. Khởi tạo SharedPreferences với chế độ MODE_PRIVATE [cite: 3074, 3078]
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // 3. Kiểm tra và lấy dữ liệu đã lưu (nếu có) [cite: 3088, 3089]
        loadData();

        // 4. Bắt sự kiện nút Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = etUsername.getText().toString();
                String pass = etPassword.getText().toString();

                if (validateLogin(user, pass)) {
                    // Nếu người dùng tick chọn "Remember Me"
                    if (cbRemember.isChecked()) {
                        saveData(user, pass, true);
                        Toast.makeText(MainActivity.this, "Đăng nhập & Đã lưu!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Nếu bỏ chọn thì xóa dữ liệu cũ đi
                        clearData();
                        Toast.makeText(MainActivity.this, "Đăng nhập thành công (Không lưu)", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Hàm lấy dữ liệu từ SharedPreferences và hiển thị lên View
    private void loadData() {
        // Lấy trạng thái checkbox, mặc định là false nếu không tìm thấy [cite: 3089]
        boolean isRemember = sharedPreferences.getBoolean(KEY_REMEMBER, false);

        if (isRemember) {
            // Lấy username và password, mặc định là chuỗi rỗng "" [cite: 3090]
            String savedUser = sharedPreferences.getString(KEY_USERNAME, "");
            String savedPass = sharedPreferences.getString(KEY_PASSWORD, "");

            // Hiển thị lên giao diện
            etUsername.setText(savedUser);
            etPassword.setText(savedPass);
            cbRemember.setChecked(true);
        }
    }

    // Hàm lưu dữ liệu vào SharedPreferences
    private void saveData(String username, String password, boolean isRemember) {
        // Gọi đối tượng Editor để chỉnh sửa [cite: 3083]
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Đưa dữ liệu vào Editor
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.putBoolean(KEY_REMEMBER, isRemember);

        // Xác nhận lưu xuống file
        editor.apply(); // Dùng apply() thay vì commit() để chạy ngầm, tránh lag UI
    }

    // Hàm xóa dữ liệu (khi người dùng bỏ tích Remember Me)
    private void clearData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Xóa toàn bộ dữ liệu trong file này
        editor.apply();
    }

    // Hàm kiểm tra nhập liệu đơn giản
    private boolean validateLogin(String u, String p) {
        return !u.isEmpty() && !p.isEmpty();
    }
}