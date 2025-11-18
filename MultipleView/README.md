# MultipleView - Android Project

Dự án này là một ví dụ về cách triển khai `RecyclerView` với nhiều loại view khác nhau trong một danh sách. Ứng dụng hiển thị một danh sách bao gồm ba loại mục khác nhau: văn bản, hình ảnh và thông tin người dùng.

## Cấu trúc Project

Dưới đây là mô tả chi tiết về các file quan trọng trong project:

### 1. `app/src/main/java/com/example/multipleview/`

#### `MainActivity.java`

*   **Mục đích:** Đây là Activity khởi chạy của ứng dụng.
*   **Chức năng:** Trong phiên bản hiện tại, `MainActivity` sẽ ngay lập tức khởi chạy `UserActivity` để hiển thị danh sách.

#### `UserActivity.java`

*   **Mục đích:** Activity chính hiển thị `RecyclerView` với nhiều loại view.
*   **Chức năng:**
    *   **`onCreate()`**:
        *   Gắn layout `activity_user.xml`.
        *   Khởi tạo `RecyclerView`.
        *   Tạo một danh sách dữ liệu (`mData`) chứa các đối tượng khác nhau: `String` (văn bản), `Integer` (ID của ảnh drawable), và `UserModel` (thông tin người dùng).
        *   Khởi tạo `MultipleViewAdapter` và truyền vào danh sách dữ liệu.
        *   Gắn adapter cho `RecyclerView` và thiết lập `LinearLayoutManager`.

### 2. `app/src/main/java/Adapter/`

#### `MultipleViewAdapter.java`

*   **Mục đích:** Adapter tùy chỉnh cho `RecyclerView` để xử lý và hiển thị nhiều loại view.
*   **Các hàm chính:**
    *   **`getItemViewType(int position)`**:
        *   Xác định loại view cho một vị trí cụ thể trong danh sách.
        *   Trả về một hằng số (`TEXT`, `IMAGE`, hoặc `USER`) dựa trên kiểu của đối tượng dữ liệu (`String`, `Integer`, hoặc `UserModel`).
    *   **`onCreateViewHolder(ViewGroup parent, int viewType)`**:
        *   Tạo một `ViewHolder` mới dựa trên `viewType`.
        *   Sử dụng một `switch-case` để inflate layout tương ứng (`row_text.xml`, `row_image.xml`, hoặc `row_user.xml`) và trả về `ViewHolder` tương ứng (`TextViewHolder`, `ImageViewHolder`, hoặc `UserViewHolder`).
    *   **`onBindViewHolder(RecyclerView.ViewHolder holder, int position)`**:
        *   Gắn dữ liệu vào `ViewHolder` tại một vị trí cụ thể.
        *   Sử dụng `switch-case` dựa trên `getItemViewType()` để xác định loại `ViewHolder` và gắn dữ liệu tương ứng.
*   **Các lớp ViewHolder bên trong:**
    *   **`TextViewHolder`**: Giữ các view cho `row_text.xml` (một `TextView`).
    *   **`ImageViewHolder`**: Giữ các view cho `row_image.xml` (một `ImageView`).
    *   **`UserViewHolder`**: Giữ các view cho `row_user.xml` (hai `TextView` cho tên và địa chỉ).

### 3. `app/src/main/java/Model/`

#### `UserModel.java`

*   **Mục đích:** Lớp dữ liệu đơn giản (POJO) để biểu diễn một người dùng.
*   **Thuộc tính:**
    *   `name` (String): Tên người dùng.
    *   `address` (String): Địa chỉ người dùng.

### 4. `app/src/main/res/layout/`

*   **`activity_main.xml`**: Layout cho `MainActivity`, hiện tại đang trống.
*   **`activity_user.xml`**: Layout cho `UserActivity`, chứa một `RecyclerView`.
*   **`row_text.xml`**: Layout cho một mục văn bản trong `RecyclerView`.
*   **`row_image.xml`**: Layout cho một mục hình ảnh trong `RecyclerView`.
*   **`row_user.xml`**: Layout cho một mục người dùng trong `RecyclerView`.

### 5. `app/src/main/AndroidManifest.xml`

*   **Mục đích:** File manifest của ứng dụng.
*   **Cấu hình:**
    *   Khai báo `UserActivity` là Activity khởi chạy (`LAUNCHER`). Điều này có nghĩa là khi người dùng mở ứng dụng, `UserActivity` sẽ được hiển thị đầu tiên.

## Luồng hoạt động của ứng dụng

1.  **Khởi chạy:** Người dùng nhấn vào biểu tượng ứng dụng.
2.  **AndroidManifest.xml:** Hệ thống Android đọc file manifest và xác định `UserActivity` là Activity cần khởi chạy.
3.  **UserActivity `onCreate()`:**
    *   `UserActivity` được tạo.
    *   Layout `activity_user.xml` được gắn, hiển thị `RecyclerView`.
    *   Một danh sách `mData` được tạo với các loại dữ liệu khác nhau.
4.  **Adapter và RecyclerView:**
    *   `MultipleViewAdapter` được khởi tạo với danh sách dữ liệu.
    *   `RecyclerView` nhận adapter và bắt đầu quá trình hiển thị các mục.
    *   Đối với mỗi mục trong danh sách, `RecyclerView` gọi `getItemViewType()` của adapter để biết loại view cần hiển thị.
    *   Sau đó, `onCreateViewHolder()` được gọi để tạo `ViewHolder` tương ứng với loại view.
    *   Cuối cùng, `onBindViewHolder()` được gọi để gắn dữ liệu từ danh sách vào các view trong `ViewHolder`.
5.  **Hiển thị:** `RecyclerView` hiển thị danh sách các mục với các layout khác nhau trên màn hình.
