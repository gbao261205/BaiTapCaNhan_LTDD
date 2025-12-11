package com.vibecoding.baitap08.Model;

public class VideoModel {
    private String videoUrl;
    private String title;

    // Constructor rỗng cần thiết cho Firebase
    public VideoModel() {}

    public VideoModel(String videoUrl, String title) {
        this.videoUrl = videoUrl;
        this.title = title;
    }

    public String getVideoUrl() { return videoUrl; }
    public String getTitle() { return title; }
}
