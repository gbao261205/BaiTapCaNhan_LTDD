package com.example.baitap01;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNumbers;
    private Button buttonProcess;

    private EditText editTextReverse;
    private TextView textViewReverseResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNumbers = findViewById(R.id.editTextNumbers);
        buttonProcess = findViewById(R.id.buttonProcess);
        editTextReverse = findViewById(R.id.editTextReverse);
        textViewReverseResult = findViewById(R.id.textViewReverseResult);

        buttonProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processNumbers();
            }
        });

        editTextReverse.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần làm gì ở đây
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // s chính là nội dung văn bản đã thay đổi
                textViewReverseResult.setText(reverseString(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Không cần làm gì ở đây
            }
        });
    }

    private void processNumbers() {
        Boolean check[] = new Boolean[10000];

        String input = editTextNumbers.getText().toString();
        if (input.isEmpty()) {
            return;
        }

        String[] numberStrings = input.split(",");
        ArrayList<Integer> allNumbers = new ArrayList<>();
        ArrayList<Integer> oddNumbers = new ArrayList<>();
        ArrayList<Integer> evenNumbers = new ArrayList<>();

        for (String s : numberStrings) {
            try {
                int number = Integer.parseInt(s.trim());
                allNumbers.add(number);
            } catch (NumberFormatException e) {
                Log.e("MainActivity", "Invalid number format: " + s, e);
            }
        }

        for (int number : allNumbers) {
            if (number % 2 == 0) {
                if (check[number] == null) {
                    check[number] = true;
                    evenNumbers.add(number);
                }
            } else {
                if (check[number] == null) {
                    check[number] = true;
                    oddNumbers.add(number);
                }
            }
        }

        Log.d("MainActivity", "Số lẻ: " + oddNumbers.toString());
        Log.d("MainActivity", "Số chẵn: " + evenNumbers.toString());
    }

    private String reverseString(String text) {
        return new StringBuilder(text).reverse().toString();
    }
}
