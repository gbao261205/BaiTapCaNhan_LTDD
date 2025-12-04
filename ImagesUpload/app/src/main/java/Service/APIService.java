package Service;

import android.os.Message;

import java.util.List;

import Model.Const;
import Model.ImageUpload;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIService {
    @Multipart
    @POST("upload.php")
    Call<List<ImageUpload>> upload (@Part(Const.MY_USERNAME)RequestBody username, @Part MultipartBody.Part avatar);

    @Multipart
    @POST("upload1.php")
    Call<Message> upload1(@Part(Const.MY_USERNAME)RequestBody username, @Part MultipartBody.Part avatar);

    @Multipart
    @POST("updateimages.php")
    Call<UpdateImageResponse> updateImage(
            @Part("id") RequestBody id,              // Key "id" trong Postman
            @Part MultipartBody.Part images          // Key "images" trong Postman (File)
    );
}
