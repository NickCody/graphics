package com.primordia.model;

import java.util.ArrayList;
import java.util.List;

public class WindowParams {
    private String title = "App";
    private Integer width = 1920;
    private Integer height = 1080;
    private Color backgroundColor = Color.Whites_whitesmoke;
    private String[] icons = {};
    private int multiSamples = 4;

    public static WindowParams defaultWindowParams() {
        return new WindowParams("App", 1920, 1080, Color.Whites_whitesmoke, new String[]{"Green32x32.png", "Green64x64.png"}, 4);
    }

    public WindowParams() {}

    public WindowParams(String title) {
        this.title = title;
    }

    public WindowParams(String title, Integer width, Integer height, Color backgroundColor, String[] icons, int multiSamples) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.backgroundColor = backgroundColor;
        this.icons = icons;
        this.multiSamples = multiSamples;
    }

    public String toString() {
        return "title = " + title +
                ", width = " + width +
                ", height = " + height +
                ", backgroundColor = " + backgroundColor +
                ", icons = " + java.util.Arrays.toString(icons) +
                ", multiSamples = " + multiSamples;
    }

    public String getTitle() {
        return title;
    }

    public void getTitle(String title) {
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

    public int getMultiSamples() {
        return multiSamples;
    }

    public void setMultiSamples(int multiSamples) {
        this.multiSamples = multiSamples;
    }

    public WindowParams title(String title) {
        this.title = title;
        return this;
    }

    public WindowParams width(int width) {
        this.width = width;
        return this;
    }
    public WindowParams height(int height) {
        this.height = height;
        return this;
    }
    public WindowParams backgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }
    public WindowParams icons(String[] icons) {
        this.icons = icons;
        return this;
    }

    public WindowParams multiSamples(int multiSamples) {
        this.multiSamples = multiSamples;
        return this;
    }

}
