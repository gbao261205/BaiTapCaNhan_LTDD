package com.example.multipleview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Model.UserModel;
import Adapter.MultipleViewAdapter;

public class UserActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView rvMultipleViewType;
    private List<Object> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        rvMultipleViewType = (RecyclerView) findViewById(R.id.rv_multiple_view_type);
        mData = new ArrayList<>();
        mData.add(new UserModel("Nguyen Van Nghia", "Quan 11"));
        mData.add(R.drawable.avatar_1);
        mData.add("Text 0");
        mData.add("Text 1");
        mData.add(new UserModel("Nguyen Hoang Minh","Quan 3"));
        mData.add("Text 2");
        mData.add(R.drawable.avatar_2);
        mData.add(R.drawable.avatar_3);
        mData.add(new UserModel("Pham Nguyen Tam Phu","Quan 10"));
        mData.add("Text 3");
        mData.add("Text 4");
        mData.add(new UserModel("Tran Van Phuc", "Quan 1"));
        mData.add(R.drawable.avatar_4);
        MultipleViewAdapter adapter = new MultipleViewAdapter(this, mData);
        rvMultipleViewType.setAdapter(adapter);
        rvMultipleViewType.setLayoutManager(new LinearLayoutManager(this));
    }

}
