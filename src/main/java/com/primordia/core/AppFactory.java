package com.primordia.core;

import com.primordia.model.AppContext;
import com.primordia.model.WindowParams;
import com.primordia.util.WindowIconLoader;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;

import org.lwjgl.opengl.GLCapabilities;
import static org.lwjgl.system.MemoryUtil.NULL;

public class AppFactory {
    public static AppContext createAppContext(String title) {
        return AppFactory.createAppContext(new WindowParams(title));
    }

    public static AppContext createAppContext(WindowParams windowParams) {

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

        Long window = glfwCreateWindow(windowParams.getWidth(), windowParams.getHeight(), windowParams.getTitle(), NULL, NULL);

        if (window == NULL)
            throw new IllegalStateException("Failed to create the GLFW window");

        WindowIconLoader.setIcons(window, windowParams.getIcons());

        glfwMakeContextCurrent(window);

        GLCapabilities caps = createCapabilities();

        return new AppContext(window, caps, windowParams);
    }
}
