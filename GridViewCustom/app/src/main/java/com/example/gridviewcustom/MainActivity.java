package com.example.gridviewcustom;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import Adapter.MonHocAdapter;
import Module.MonHoc;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList <MonHoc> arrayList;
    MonHocAdapter adapter;
    EditText editText;
    Button buttonAdd, buttonUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title not the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//int flag, int mask
        setContentView(R.layout.activity_main);

        AnhXa();

        adapter = new MonHocAdapter(this,R.layout.row_monhoc,arrayList);
        gridView.setAdapter(adapter);

        //long click item in grid view
        gridView.setOnItemLongClickListener((parent, view, position, id) -> {
            Toast.makeText(MainActivity.this, arrayList.get(position).getName(), Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void AnhXa() {
        gridView = (GridView) findViewById(R.id.gridview);
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