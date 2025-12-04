package com.vibecoding.imagesupload;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.List;

import Model.ImageUpload;
import Service.APIService;
import Service.RetrofitClient;
import Utils.RealPathUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private static final int MY_REQUEST_CODE = 10;

    // Khai báo View
    EditText editTextUserName;
    TextView textViewUsername;
    ImageView imageViewChoose;
    Button btnChoose, btnUpload, btnBack;

    // Biến xử lý logic
    private Uri mUri;
    private ProgressDialog mProgressDialog;

    private APIService serviceapi;

    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e(TAG, "onActivityResult");
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imageViewChoose.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();

        //Xử lý nút back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please wait upload...");

        serviceapi = RetrofitClient.getClient().create(APIService.class);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckPermission();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUri != null) {
                    UploadImage1();
                } else {
                    Toast.makeText(MainActivity.this, "Vui lòng chọn ảnh trước!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void AnhXa() {
        btnBack = findViewById(R.id.btnBack);
        btnChoose = findViewById(R.id.btnSelectImage);
        btnUpload = findViewById(R.id.btnUpload);
        imageViewChoose = findViewById(R.id.ivImage);
        editTextUserName = findViewById(R.id.edtUsername);
        textViewUsername = findViewById(R.id.tvUsername);
    }

    private void CheckPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }

        String[] permissions;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+
            permissions = new String[]{Manifest.permission.READ_MEDIA_IMAGES};
        } else {
            // Below Android 13
            permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
        }

        // Check if permission is already granted
        if (checkSelfPermission(permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            // Request permission
            requestPermissions(permissions, MY_REQUEST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(this, "Permission denied. Cannot open gallery.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    private void UploadImage1() {
        mProgressDialog.show();

        String username = editTextUserName.getText().toString().trim();

        String IMAGE_PATH = RealPathUtil.getRealPath(this, mUri);
        Log.e("Upload", "Image Path: " + IMAGE_PATH);

        if (IMAGE_PATH == null) {
            mProgressDialog.dismiss();
            Toast.makeText(this, "Không tìm thấy đường dẫn file!", Toast.LENGTH_LONG).show();
            return;
        }

        File file = new File(IMAGE_PATH);

        RequestBody requestUsername = RequestBody.create(MediaType.parse("multipart/form-data"), username);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part partbodyavatar = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);

        serviceapi.upload(requestUsername, partbodyavatar).enqueue(new Callback<List<ImageUpload>>() {
            @Override
            public void onResponse(Call<List<ImageUpload>> call, Response<List<ImageUpload>> response) {
                mProgressDialog.dismiss();
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Upload thành công!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Upload thất bại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ImageUpload>> call, Throwable t) {
                mProgressDialog.dismiss();
                Log.e(TAG, t.toString());
                Toast.makeText(MainActivity.this, "Gọi API lỗi: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
