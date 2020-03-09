package com.primordia.core;

import com.primordia.model.AppContext;
import com.primordia.model.Color;
import com.primordia.model.WindowParams;
import com.primordia.util.WindowIconLoader;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GLCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.lwjgl.system.MemoryUtil.NULL;

public class AppFactory {

    protected static Logger log = LoggerFactory.getLogger(AppFactory.class);
    protected static GLFWErrorCallback errCallback;

    public static AppContext createAppContext(String title) {
        return AppFactory.createAppContext(WindowParams.defaultWindowParams().title(title));
    }

    public static AppContext createAppContext(WindowParams windowParams) {

        log.info("Creating AppContext with windowParams: " + windowParams.toString());

        glfwSetErrorCallback(errCallback = GLFWErrorCallback.createPrint(System.err));

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_SAMPLES, windowParams.getMultiSamples());

        long window = 0;
        long mon = glfwGetPrimaryMonitor();
        GLFWVidMode vidmode = glfwGetVideoMode(mon);

        if (windowParams.getFullScreen()) {
            window = glfwCreateWindow(
                    vidmode.width(),
                    vidmode.height(),
                    windowParams.getTitle(), mon, NULL);
        } else {
            window = glfwCreateWindow(
                    Math.min(vidmode.width(), windowParams.getWidth()),
                    Math.min(vidmode.height(), windowParams.getHeight()),
                    windowParams.getTitle(), NULL, NULL);
        }

        if (window == NULL)
            throw new IllegalStateException("Failed to create the GLFW window");

        WindowIconLoader.setIcons(window, windowParams.getIcons());

        glfwMakeContextCurrent(window);

        GLCapabilities caps = createCapabilities();

        log.info("GLCapabilities: " + caps.toString());

        return new AppContext(window, caps, windowParams);
    }
}
