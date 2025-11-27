
package com.vibecoding.baitapviewflipper_viewpaper;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.MaterialToolbar;
import com.vibecoding.baitapviewflipper_viewpaper.adapter.ImagesSliderPagerAdapter;
import com.vibecoding.baitapviewflipper_viewpaper.api.ApiRequest;
import com.vibecoding.baitapviewflipper_viewpaper.api.ApiService;
import com.vibecoding.baitapviewflipper_viewpaper.model.ImagesSlider;
import com.vibecoding.baitapviewflipper_viewpaper.model.MessageModel;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private ImagesSliderPagerAdapter adapter;
    private List<ImagesSlider> imagesList = new ArrayList<>();

    // Auto run giống slide CircleIndicator:contentReference[oaicite:5]{index=5}
    private Handler handler = new Handler();
    private Runnable runnable;
    private int delayMillis = 3000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        circleIndicator = findViewById(R.id.circle_indicator);

        // (optional) toolbar nếu muốn giống slide Fragment+ViewPager2:contentReference[oaicite:6]{index=6}
//        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
//        if (toolbar != null) {
//            setSupportActionBar(toolbar);
//        }

        adapter = new ImagesSliderPagerAdapter(this, imagesList);
        viewPager.setAdapter(adapter);
        circleIndicator.setViewPager(viewPager);

        loadImagesFromApi(1);  // position = 1 (có thể đổi theo đề)
        setupAutoRun();
    }

    private void loadImagesFromApi(int position) {
        ApiRequest api = ApiService.getInstance().getApi();
        api.LoadImageSlider(position).enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MessageModel messageModel = response.body();
                    if (messageModel.isSuccess()) {
                        imagesList.clear();
                        imagesList.addAll(messageModel.getResult());
                        adapter.notifyDataSetChanged();
                        circleIndicator.setViewPager(viewPager);
                    } else {
                        Toast.makeText(MainActivity.this,
                                messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this,
                            "Response error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
                Toast.makeText(MainActivity.this,
                        "Call API failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupAutoRun() {
        runnable = new Runnable() {
            @Override
            public void run() {
                if (imagesList == null || imagesList.isEmpty()) {
                    handler.postDelayed(this, delayMillis);
                    return;
                }

                int current = viewPager.getCurrentItem();
                if (current == imagesList.size() - 1) {
                    viewPager.setCurrentItem(0);
                } else {
                    viewPager.setCurrentItem(current + 1);
                }

                handler.postDelayed(this, delayMillis);
            }
        };

        // Lắng nghe khi user vuốt để reset timer – giống slide autorun
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override public void onPageSelected(int position) {
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, delayMillis);
            }
            @Override public void onPageScrollStateChanged(int state) {}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, delayMillis);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }
}