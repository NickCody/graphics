package com.primordia.model;

import java.util.ArrayList;
import java.util.List;

public class WindowParams {
    private String title = "App";
    private Integer width = 1920;
    private Integer height = 1080;
    private Color backgroundColor = Color.Whites_whitesmoke;
    private String[] icons = {};

    public WindowParams(String title) {
        this.title = title;
    }

    public WindowParams(String title, Integer width, Integer height, Color backgroundColor, String[] icons) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.backgroundColor = backgroundColor;
        this.icons = icons;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String[] getIcons() {
        return icons;
    }

    public void setIcons(String[] icons) {
        this.icons = icons;
    }
}
