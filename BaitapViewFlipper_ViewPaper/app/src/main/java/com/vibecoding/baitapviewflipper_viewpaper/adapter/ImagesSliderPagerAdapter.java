package com.vibecoding.baitapviewflipper_viewpaper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.vibecoding.baitapviewflipper_viewpaper.R;
import com.vibecoding.baitapviewflipper_viewpaper.api.ApiService;
import com.vibecoding.baitapviewflipper_viewpaper.model.ImagesSlider;

import java.util.List;

public class ImagesSliderPagerAdapter extends PagerAdapter {

    private Context context;
    private List<ImagesSlider> imagesList;

    public ImagesSliderPagerAdapter(Context context, List<ImagesSlider> imagesList) {
        this.context = context;
        this.imagesList = imagesList;
    }

    @Override
    public int getCount() {
        return imagesList != null ? imagesList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_images, container, false);

        ImageView imgSlider = view.findViewById(R.id.img_slider);

        ImagesSlider item = imagesList.get(position);

        // Nếu server trả avatar là tên file: "banner1.png"
        String imageUrl = ApiService.getBaseUrl() + "images/" + item.getAvatar();

        // Nếu avatar đã là full url thì chỉ cần Glide.with(...).load(item.getAvatar())
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(imgSlider);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}