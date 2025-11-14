package com.example.listview;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList <String> arrayList = new ArrayList<>();
    EditText editText;
    Button buttonAdd;
    Button buttonUpdate;
    Button buttonDelete;
    int positionToDelete;
    int positionToUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title not the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//int flag, int mask
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        arrayList.add("A");
        arrayList.add("B");
        arrayList.add("C");
        arrayList.add("D");
        arrayList.add("E");
        arrayList.add("F");

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(adapter);

        //Touch one line on the listview
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String item = listView.getItemAtPosition(position).toString();
            Toast.makeText(MainActivity.this, ""+ position, Toast.LENGTH_SHORT).show();
            editText.setText(arrayList.get(position));
            buttonAdd.setVisibility(View.GONE);
            buttonDelete.setVisibility(View.VISIBLE);
        });

        // Hold one line on the listView
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            String item = listView.getItemAtPosition(position).toString();
            Toast.makeText(MainActivity.this, "Bạn đang nhấn giữ"+ position, Toast.LENGTH_SHORT).show();
            return false; //false --> sau khi thả ra xem đó là 1 lần click (1 long click + 1 onclick), true --> sau khi thả ra xem đó là 1 lần giữ (1 long click + 1 onlongclick)
        });

        //Add new item
        editText = findViewById(R.id.editText);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(v -> {
            String item = editText.getText().toString();
            arrayList.add(item);
            editText.setText("");
            adapter.notifyDataSetChanged();
        });

        //Update item
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(v -> {
            String item = editText.getText().toString();
            if (item.isEmpty()){
                Toast.makeText(MainActivity.this, "Vui lòng nhập dữ liệu", Toast.LENGTH_SHORT).show();
                return;
            }
            arrayList.set(positionToUpdate,item);
            editText.setText("");
            adapter.notifyDataSetChanged();
        });

        //Delete item
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(v -> {
            arrayList.remove(positionToDelete);
            editText.setText("");
            buttonAdd.setVisibility(View.VISIBLE);
            buttonDelete.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        });
    }
}