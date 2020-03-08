package com.primordia.model;

import org.lwjgl.opengl.GLCapabilities;

public class AppContext {
    private Long window;
    private GLCapabilities glCaps;
    private WindowParams windowParams;

    public AppContext(Long window, GLCapabilities glCaps, WindowParams windowParams) {
        this.window = window;
        this.glCaps = glCaps;
        this.windowParams = windowParams;
    }

    public Long getWindow() {
        return window;
    }

    public void setWindow(Long window) {
        this.window = window;
    }

    public GLCapabilities getGlCaps() {
        return glCaps;
    }

    public void setGlCaps(GLCapabilities glCaps) {
        this.glCaps = glCaps;
    }

    public WindowParams getWindowParams() {
        return windowParams;
    }

    public void setWindowParams(WindowParams windowParams) {
        this.windowParams = windowParams;
    }
}
