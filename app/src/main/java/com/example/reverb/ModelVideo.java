package com.example.reverb;

import android.net.Uri;

public class ModelVideo {

    long id;
    Uri data;
    String title, duration,album;


    public ModelVideo(long id, Uri data, String title, String duration,String album) {
        this.id = id;
        this.data = data;
        this.title = title;
        this.duration = duration;
        this.album=album;
        //this.path= path;
    }

    public static void get(int position)
    {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public  Uri getData() {
        return data;
    }

    public void setData(Uri data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    //public static String getPath()
    //{
        //return path;
    //}
    //public void setPath(String path)
    //{
        //this.path=path;
    //}

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
