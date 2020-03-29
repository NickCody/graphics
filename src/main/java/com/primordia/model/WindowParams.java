package com.primordia.model;

public class WindowParams {
    private String title = "App";
    private Integer width = 960;
    private Integer height = 540;
    private Color backgroundColor = Color.Whites_whitesmoke;
    private String[] icons = new String[]{"Green32x32.png", "Green64x64.png"};
    private int multiSamples = 8;
    private boolean fullScreen = System.getProperty("fullscreen", "false").equals("true");

    public static WindowParams defaultWindowParams() {
        return new WindowParams();
    }

    public WindowParams() {}

    public WindowParams(String title) {
        this.title = title;
    }

    public WindowParams(String title, Integer width, Integer height, Color backgroundColor, String[] icons, int multiSamples, boolean fullScreen) {
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
                ", multiSamples = " + multiSamples +
                ", fullScreen = " + fullScreen;
    }

    //
    // Getters / Setters
    //

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

    public int getMultiSamples() {
        return multiSamples;
    }

    public void setMultiSamples(int multiSamples) {
        this.multiSamples = multiSamples;
    }

    public boolean getFullScreen() {
        return fullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    //
    // Builders
    //

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

    public WindowParams fullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
        return this;
    }

}
