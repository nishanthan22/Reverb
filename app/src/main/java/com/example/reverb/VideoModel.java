package com.example.reverb;

public class VideoModel {

    String id;
    String path;
    String title;
    String size;
    String resolution;
    String duration;
    String disName;
    String width_height;

    public VideoModel(String id, String path, String title, String size, String resolution, String duration, String disName, String width_height) {

        this.id = id;
        this.path = path;
        this.title = title;
        this.size = size;
        this.resolution = resolution;
        this.duration = duration;
        this.disName = disName;
        this.width_height = width_height;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDisName() {
        return disName;
    }

    public void setDisName(String disName) {
        this.disName = disName;
    }

    public String getWidth_height() {
        return width_height;
    }

    public void setWidth_height(String width_height) {
        this.width_height = width_height;
    }
}
