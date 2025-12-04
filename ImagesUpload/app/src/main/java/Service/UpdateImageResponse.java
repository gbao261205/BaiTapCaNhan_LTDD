package Service;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateImageResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    private List<UserResult> result;

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<UserResult> getResult() {
        return result;
    }

    // Class con để hứng dữ liệu bên trong "result"
    public static class UserResult {
        @SerializedName("id")
        private String id;

        @SerializedName("username")
        private String username;

        @SerializedName("fname")
        private String fname;

        @SerializedName("email")
        private String email;

        @SerializedName("gender")
        private String gender;

        @SerializedName("images")
        private String images;

        // Getter cho images để hiển thị
        public String getImages() {
            return images;
        }

        public String getUsername() {
            return username;
        }
    }
}
