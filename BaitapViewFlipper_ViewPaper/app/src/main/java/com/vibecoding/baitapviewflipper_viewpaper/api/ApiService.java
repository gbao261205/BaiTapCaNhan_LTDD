package com.vibecoding.baitapviewflipper_viewpaper.api;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    private static final String BASE_URL = "http://app.iotstar.vn/appfoods/";
    private static ApiService instance;
    private Retrofit retrofit;

    private ApiService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized ApiService getInstance() {
        if (instance == null) {
            instance = new ApiService();
        }
        return instance;
    }

    public ApiRequest getApi() {
        return retrofit.create(ApiRequest.class);
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }
}