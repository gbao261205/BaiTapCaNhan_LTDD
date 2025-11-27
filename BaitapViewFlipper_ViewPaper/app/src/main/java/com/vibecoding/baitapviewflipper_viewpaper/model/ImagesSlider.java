
package com.vibecoding.baitapviewflipper_viewpaper.model;

import java.io.Serializable;

public class ImagesSlider implements Serializable {
    private int id;
    private int position;
    private String avatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
