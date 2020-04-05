package com.primordia.graphics.model;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Callback;

public class AppContext {
    private Long window;
    private GLCapabilities glCaps;
    private WindowParams windowParams;
    private GLFWErrorCallback errCallback;
    private Callback debugMessageCallback;

    public AppContext(Long window, GLCapabilities glCaps, WindowParams windowParams, GLFWErrorCallback errCallback, Callback debugMessageCallback) {
        this.window = window;
        this.glCaps = glCaps;
        this.windowParams = windowParams;
        this.errCallback = errCallback;
        this.debugMessageCallback = debugMessageCallback;
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

    public GLFWErrorCallback getErrCallback() {
        return errCallback;
    }

    public Callback getDebugMessageCallback() {
        return debugMessageCallback;
    }
}
