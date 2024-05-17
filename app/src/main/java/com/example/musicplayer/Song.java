package com.example.musicplayer;

import java.io.Serializable;

public class Song implements Serializable {

    private String title;
    private String artist;
    private int resId;

    public Song(String title, String artist, int resId) {
        this.title = title;
        this.artist = artist;
        this.resId = resId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getResId() {
        return resId;
    }

    public void setPath(int resId) {
        this.resId = resId;
    }
}
