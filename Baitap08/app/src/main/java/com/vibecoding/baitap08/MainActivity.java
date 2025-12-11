package com.vibecoding.baitap08;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vibecoding.baitap08.Adapter.VideoAdapter;
import com.vibecoding.baitap08.Model.VideoModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_VIDEO_REQUEST = 1;
    private ViewPager2 viewPagerVideos;
    private FloatingActionButton fabUpload;
    private ProgressBar progressBar;
    private Button buttonLogout; // Thêm nút Logout

    private List<VideoModel> videoList;
    private VideoAdapter videoAdapter;
    private Uri videoUri;
    private FirebaseAuth mAuth; // Thêm FirebaseAuth

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Ánh xạ Views
        viewPagerVideos = findViewById(R.id.viewPagerVideos);
        fabUpload = findViewById(R.id.fabUpload);
        progressBar = findViewById(R.id.progressBar);
        buttonLogout = findViewById(R.id.buttonLogout); // Ánh xạ nút Logout

        videoList = new ArrayList<>();
        videoAdapter = new VideoAdapter(videoList);
        viewPagerVideos.setAdapter(videoAdapter);

        loadVideosFromFirestore();

        fabUpload.setOnClickListener(v -> openVideoPicker());

        // Thêm sự kiện cho nút Logout
        buttonLogout.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Kiểm tra nếu người dùng chưa đăng nhập, chuyển về màn hình Login
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }

    private void openVideoPicker() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_VIDEO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_VIDEO_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            videoUri = data.getData();
            uploadVideoToCloudinary();
        }
    }

    private void uploadVideoToCloudinary() {
        if (videoUri != null) {
            progressBar.setVisibility(View.VISIBLE);
            String timestamp = String.valueOf(System.currentTimeMillis());
            String publicId = "videos/video_" + timestamp;

            MediaManager.get().upload(videoUri)
                    .option("resource_type", "video")
                    .option("public_id", publicId)
                    .callback(new UploadCallback() {
                        @Override
                        public void onStart(String requestId) {
                            // Bắt đầu upload
                        }

                        @Override
                        public void onProgress(String requestId, long bytes, long totalBytes) {
                            // Cập nhật progress bar nếu cần
                        }

                        @Override
                        public void onSuccess(String requestId, Map resultData) {
                            String downloadUrl = (String) resultData.get("secure_url");
                            saveVideoInfoToFirestore(downloadUrl, "Video " + timestamp);
                        }

                        @Override
                        public void onError(String requestId, ErrorInfo error) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this, "Lỗi upload: " + error.getDescription(), Toast.LENGTH_SHORT).show();
                            Log.e("CloudinaryUpload", "Error: " + error.getDescription());
                        }

                        @Override
                        public void onReschedule(String requestId, ErrorInfo error) {
                            // Reschedule
                        }
                    }).dispatch();
        }
    }

    // Thêm lại phương thức saveVideoInfoToFirestore
    private void saveVideoInfoToFirestore(String url, String title) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        VideoModel video = new VideoModel(url, title);

        db.collection("videos").add(video)
                .addOnSuccessListener(documentReference -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Upload thành công!", Toast.LENGTH_SHORT).show();
                    // Thêm video mới vào đầu danh sách để hiển thị ngay lập tức
                    videoList.add(0, video);
                    videoAdapter.notifyItemInserted(0);
                    viewPagerVideos.setCurrentItem(0);
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Lỗi khi lưu thông tin video: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // Thêm lại phương thức loadVideosFromFirestore
    private void loadVideosFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("videos").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        videoList.clear(); // Xóa danh sách cũ trước khi thêm mới
                        for (DocumentSnapshot doc : queryDocumentSnapshots) {
                            VideoModel video = doc.toObject(VideoModel.class);
                            videoList.add(video);
                        }
                        videoAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MainActivity.this, "Lỗi khi tải video: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}