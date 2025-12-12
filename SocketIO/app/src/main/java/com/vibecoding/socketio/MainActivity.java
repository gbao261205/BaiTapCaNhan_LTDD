package com.vibecoding.socketio;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {

    // IP SERVER CỦA BẠN
    private static final String SERVER_URL = "http://172.20.10.2:3000";

    private Socket mSocket;
    private EditText edtMessage;
    private LinearLayout msgContainer;
    private ScrollView scrollView;

    // Biến lưu ID của bản thân
    private String myId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ View
        edtMessage = findViewById(R.id.edtMessage);
        Button btnSend = findViewById(R.id.btnSend);
        msgContainer = findViewById(R.id.msgContainer);
        scrollView = findViewById(R.id.scrollView);

        // Kết nối Socket
        try {
            mSocket = IO.socket(SERVER_URL);

            // Lắng nghe sự kiện kết nối thành công để lấy ID
            mSocket.on(Socket.EVENT_CONNECT, args -> {
                myId = mSocket.id(); // Lưu ID của mình lại
                runOnUiThread(() -> Toast.makeText(this, "Đã kết nối: " + myId, Toast.LENGTH_SHORT).show());
            });

            // Lắng nghe tin nhắn từ Server
            mSocket.on("chat_from_server", onNewMessage);

            mSocket.connect();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // Xử lý nút Gửi
        btnSend.setOnClickListener(v -> sendMessage());
    }

    private void sendMessage() {
        String message = edtMessage.getText().toString().trim();
        if (message.isEmpty()) return;

        // Tạo JSON Object gửi đi
        JSONObject sendData = new JSONObject();
        try {
            sendData.put("senderId", myId);
            sendData.put("message", message);

            mSocket.emit("chat_from_client", sendData);

            edtMessage.setText(""); // Xóa ô nhập
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Listener nhận tin nhắn
    private final Emitter.Listener onNewMessage = args -> runOnUiThread(() -> {
        try {
            JSONObject data = (JSONObject) args[0];
            String senderId = data.getString("senderId");
            String message = data.getString("message");

            // Kiểm tra ID để xác định trái/phải
            boolean isMine = senderId.equals(myId);
            addMessageToLayout(message, isMine);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    });

    // Hàm vẽ tin nhắn lên màn hình
    private void addMessageToLayout(String message, boolean isMine) {
        TextView textView = new TextView(this);
        textView.setText(message);
        textView.setTextSize(16);
        textView.setPadding(30, 20, 30, 20);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(10, 10, 10, 10);

        if (isMine) {
            // Tin nhắn của mình: Bên phải, màu xanh
            params.gravity = Gravity.END;
            textView.setBackgroundColor(Color.parseColor("#0084FF"));
            textView.setTextColor(Color.WHITE);
        } else {
            // Tin nhắn người khác: Bên trái, màu xám
            params.gravity = Gravity.START;
            textView.setBackgroundColor(Color.parseColor("#E4E6EB"));
            textView.setTextColor(Color.BLACK);
        }

        textView.setLayoutParams(params);
        msgContainer.addView(textView);

        // Cuộn xuống dưới cùng
        scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSocket != null) {
            mSocket.disconnect();
            mSocket.off("chat_from_server", onNewMessage);
        }
    }
}