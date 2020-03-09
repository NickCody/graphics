package com.primordia.core;

import com.primordia.model.AppContext;
import com.primordia.model.Color;
import com.primordia.model.WindowParams;
import com.primordia.util.WindowIconLoader;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;

import org.lwjgl.opengl.GLCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.lwjgl.system.MemoryUtil.NULL;

public class AppFactory {

    protected static Logger log = LoggerFactory.getLogger(AppFactory.class);


    public static AppContext createAppContext(String title) {
        return AppFactory.createAppContext(WindowParams.defaultWindowParams().title(title));
    }

    public static AppContext createAppContext(WindowParams windowParams) {

        log.info("Creating AppContext with windowParams: " + windowParams.toString());

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

        long window = glfwCreateWindow(windowParams.getWidth(), windowParams.getHeight(), windowParams.getTitle(), NULL, NULL);

        if (window == NULL)
            throw new IllegalStateException("Failed to create the GLFW window");

        WindowIconLoader.setIcons(window, windowParams.getIcons());

        glfwMakeContextCurrent(window);

        GLCapabilities caps = createCapabilities();

        log.info("GLCapabilities: " + caps.toString());

        return new AppContext(window, caps, windowParams);
    }
}
