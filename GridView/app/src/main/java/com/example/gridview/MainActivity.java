package com.example.gridview;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList <String> arrayList;
    EditText editText;
    Button buttonAdd, buttonUpdate;
    int updatePosition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title not the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//int flag, int mask
        setContentView(R.layout.activity_main);

        //ánh xạ
        gridView = (GridView) findViewById(R.id.gridview);
        //Thêm dữ liệu vào List
        arrayList = new ArrayList<>();
        arrayList.add("Java");
        arrayList.add("C#");
        arrayList.add("PHP");
        arrayList.add("Kotlin");
        arrayList.add("Dart");

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        gridView.setAdapter(adapter);

        gridView.setAdapter(adapter);

        //click
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, arrayList.get(position), Toast.LENGTH_SHORT).show();
                updatePosition = position;
                editText.setText(arrayList.get(position));
            }
        });

        //hold
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Long Click " + arrayList.get(position), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        editText = (EditText) findViewById(R.id.editText);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        // click button add
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.add(editText.getText().toString());
                editText.setText("");
                adapter.notifyDataSetChanged();
            }
        });
        //click button update
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.set(updatePosition, editText.getText().toString());
                editText.setText("");
                updatePosition = -1;
                adapter.notifyDataSetChanged();
            }
        });
    }
}