# Project RecyclerView Demo

## Giới thiệu

Đây là một project Android đơn giản nhằm mục đích minh họa cách sử dụng `RecyclerView` để hiển thị một danh sách các bài hát. Ứng dụng sẽ hiển thị tên bài hát, lời bài hát, tên nghệ sĩ và một mã code. Khi người dùng nhấn vào một bài hát bất kỳ, một `Toast` sẽ hiện lên, hiển thị tên của bài hát đó.

## Cấu trúc Project

Project bao gồm các file chính sau:

-   **MainActivity.java**: Activity chính của ứng dụng.
-   **SongModel.java**: Lớp `Model` đại diện cho dữ liệu của một bài hát.
-   **SongAdapter.java**: `Adapter` để kết nối dữ liệu (`List<SongModel>`) với `RecyclerView`.
-   **activity_main.xml**: File layout cho `MainActivity`, chứa `RecyclerView`.
-   **row_item_song.xml**: File layout cho từng item (hàng) trong `RecyclerView`.

## Giải thích chi tiết

### 1. `Model/SongModel.java`

Đây là lớp `Data Model` chứa thông tin cho mỗi bài hát.

| Thuộc tính | Kiểu dữ liệu | Ý nghĩa |
| --- | --- | --- |
| `mTitle` | `String` | Tên bài hát |
| `mLyric` | `String` | Lời bài hát |
| `mArtist` | `String` | Tên nghệ sĩ |
| `mCode` | `String` | Mã bài hát |

### 2. `Adapter/SongAdapter.java`

Đây là `Adapter` tùy chỉnh để quản lý và hiển thị dữ liệu lên `RecyclerView`.

-   **`SongAdapter(Context context, List<SongModel> datas)`**: Hàm khởi tạo, nhận vào `Context` và danh sách các bài hát (`List<SongModel>`).
-   **`onCreateViewHolder(...)`**: Được gọi khi `RecyclerView` cần một `ViewHolder` mới. Hàm này sẽ "thổi phồng" (inflate) layout `row_item_song.xml` để tạo ra một `View` mới cho mỗi item.
-   **`onBindViewHolder(...)`**: "Gắn" dữ liệu từ `SongModel` tại một vị trí (`position`) cụ thể vào các `View` trong `SongViewHolder`.
-   **`getItemCount()`**: Trả về tổng số lượng item trong danh sách.
-   **`SongViewHolder` (lớp nội)**: Đại diện cho một `View` của item trong `RecyclerView`.
    -   **`SongViewHolder(View itemView)`**: Hàm khởi tạo, nơi ánh xạ các `TextView` từ layout (`tv_title`, `tv_lyric`,...) và thiết lập `OnClickListener` cho mỗi item. Khi một item được click, nó sẽ lấy `SongModel` tương ứng và hiển thị tên bài hát bằng `Toast`.

### 3. `com/example/recyclerview/MainActivity.java`

Đây là `Activity` chính của ứng dụng.

-   **`onCreate(...)`**:
    1.  Ánh xạ `RecyclerView` từ file layout `activity_main.xml`.
    2.  Gọi hàm `initData()` để tạo dữ liệu mẫu (danh sách các bài hát).
    3.  Khởi tạo `SongAdapter` với dữ liệu vừa tạo.
    4.  Tạo một `LinearLayoutManager` để xác định cách các item được sắp xếp (ở đây là theo chiều dọc).
    5.  Gán `LayoutManager` và `Adapter` cho `RecyclerView`.

### 4. `res/layout/activity_main.xml`

File layout cho `MainActivity`. Nó chỉ chứa một `RecyclerView` duy nhất chiếm toàn bộ màn hình để hiển thị danh sách.

### 5. `res/layout/row_item_song.xml`

File layout cho từng hàng trong `RecyclerView`.

-   Sử dụng `LinearLayout` với chiều cao là `wrap_content` để mỗi item chỉ chiếm không gian cần thiết.
-   Bên trong chứa các `TextView` để hiển thị thông tin chi tiết của bài hát: `tv_title`, `tv_lyric`, `tv_artist`, và `tv_code`.

## Luồng hoạt động của ứng dụng

1.  **Khởi động**: Ứng dụng khởi chạy `MainActivity`.
2.  **Chuẩn bị dữ liệu**: Trong `MainActivity.onCreate()`, hàm `initData()` được gọi để tạo ra một danh sách `ArrayList<SongModel>` chứa các đối tượng bài hát mẫu.
3.  **Khởi tạo Adapter**: Một `SongAdapter` được tạo ra, nhận danh sách dữ liệu này.
4.  **Cài đặt RecyclerView**:
    -   `MainActivity` thiết lập một `LinearLayoutManager` cho `RecyclerView` để các item được xếp chồng lên nhau theo chiều dọc.
    -   `SongAdapter` được gán cho `RecyclerView`.
5.  **Hiển thị**:
    -   `RecyclerView` yêu cầu `SongAdapter` tạo các `ViewHolder` cần thiết bằng cách gọi `onCreateViewHolder()`.
    -   `Adapter` tạo `View` cho mỗi item bằng cách inflate layout `row_item_song.xml`.
    -   `RecyclerView` sau đó "buộc" dữ liệu vào các `ViewHolder` này thông qua `onBindViewHolder()`. `Adapter` lấy dữ liệu từ `List<SongModel>` tại vị trí tương ứng và đặt nó vào các `TextView` trong `ViewHolder`.
6.  **Tương tác người dùng**:
    -   Người dùng cuộn danh sách, `RecyclerView` sẽ tái sử dụng các `View` đã có và chỉ cần gọi lại `onBindViewHolder()` để cập nhật dữ liệu mới, giúp tiết kiệm bộ nhớ và tăng hiệu suất.
    -   Khi người dùng nhấn vào một item, sự kiện `onClick` đã được đăng ký trong `SongViewHolder` sẽ được kích hoạt.
    -   Một `Toast` sẽ xuất hiện, hiển thị tên của bài hát tương ứng với item được nhấn.

