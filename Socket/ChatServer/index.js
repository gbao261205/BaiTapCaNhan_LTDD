// File: index.js
// run: node index.js
const io = require("socket.io")(3000, {
  cors: {
    origin: "*", // Cho phép mọi kết nối (Web/Android)
  },
  // QUAN TRỌNG: Cho phép Android dùng thư viện cũ (v2) kết nối được
  allowEIO3: true 
});

console.log("Server đang chạy tại port 3000...");

io.on("connection", (socket) => {
  console.log("Có người kết nối, ID: " + socket.id);

  // Lắng nghe sự kiện gửi tin nhắn từ Client (Android hoặc Web)
  socket.on("chat_from_client", (data) => {
    // data là JSON object: { senderId: "...", message: "..." }
    console.log("Nhận tin nhắn: ", data);

    // Gửi trả lại cho TẤT CẢ mọi người (để hiển thị lên màn hình)
    io.emit("chat_from_server", data);
  });

  socket.on("disconnect", () => {
    console.log("Người dùng đã thoát: " + socket.id);
  });
});