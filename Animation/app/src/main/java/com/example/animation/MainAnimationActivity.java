package com.example.animation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import Adapter.CustomAnimationAdapter;


public class MainAnimationActivity extends AppCompatActivity {

    private RecyclerView rvItems;
    private Button btnAddItem;
    private CustomAnimationAdapter mAdapter;
    private List<String> mDataList;
    private int itemCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_animation);

        rvItems = findViewById(R.id.rv_items);
        btnAddItem = findViewById(R.id.btn_add_item);

        mDataList = new ArrayList<>();
        mDataList.add("Item 0");
        mDataList.add("Item 1");
        mDataList.add("Item 2");
        itemCounter = 3;

        mAdapter = new CustomAnimationAdapter(this, mDataList);

        rvItems.setAdapter(mAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        rvItems.setItemAnimator(new DefaultItemAnimator());

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newItem = "Item " + itemCounter;
                itemCounter++;
                mAdapter.addItem(newItem);
            }
        });
    }
}