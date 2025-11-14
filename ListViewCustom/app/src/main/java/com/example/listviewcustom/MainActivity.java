package com.example.listviewcustom;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import Adapter.MonHocAdapter;
import Module.MonHoc;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    EditText editText;
    Button buttonAdd, buttonUpdate;
    ArrayList <MonHoc> arrayList;
    MonHocAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();

        adapter = new MonHocAdapter(this,R.layout.row_monhoc,arrayList);
        listView.setAdapter(adapter);

    }

    private void AnhXa() {
        listView = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.editText);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        //Thêm dữ liệu vào List
        arrayList = new ArrayList<>();
        arrayList.add(new MonHoc("Java","Java 1",R.drawable.java1));
        arrayList.add(new MonHoc("C#","C# 1",R.drawable.c));
        arrayList.add(new MonHoc("PHP","PHP 1",R.drawable.php));
        arrayList.add(new MonHoc("Kotlin","Kotlin 1",R.drawable.kotlin));
        arrayList.add(new MonHoc("Dart","Dart 1",R.drawable.dart));
    }
}