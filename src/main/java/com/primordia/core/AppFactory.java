package com.primordia.core;

import com.primordia.model.AppContext;
import com.primordia.model.Color;
import com.primordia.model.WindowParams;
import com.primordia.util.WindowIconLoader;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;

import org.lwjgl.opengl.GLCapabilities;
import static org.lwjgl.system.MemoryUtil.NULL;

public class AppFactory {

    private static WindowParams defaultWindowParams =
            new WindowParams("Triangle", 1920, 1080, Color.Whites_whitesmoke, new String[]{"Green32x32.png", "Green64x64.png"});

    public static AppContext createAppContext(String title) {
        WindowParams customTitleWindowParams = new WindowParams(title, defaultWindowParams.getWidth(), defaultWindowParams.getHeight(), defaultWindowParams.getBackgroundColor(), defaultWindowParams.getIcons());
        return AppFactory.createAppContext(customTitleWindowParams);
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
