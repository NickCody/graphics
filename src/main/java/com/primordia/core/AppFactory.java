package com.primordia.core;

import com.primordia.model.AppContext;
import com.primordia.model.Color;
import com.primordia.model.WindowParams;
import com.primordia.util.WindowIconLoader;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.MemoryStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glGetIntegerv;
import static org.lwjgl.opengl.GL30.GL_MAX_SAMPLES;
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

        // Determine Max Multisamples
        //
        int maxMultiSamples = determineMaxMultisamples();
        int actualMultisamples = Math.min(maxMultiSamples, windowParams.getMultiSamples());
        if (actualMultisamples < windowParams.getMultiSamples()) {
            log.warn("Could not set GLFW_SAMPLES to " + windowParams.getMultiSamples() + ". Using max of " + maxMultiSamples);
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_SAMPLES, actualMultisamples);

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

        log.info("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION));
        log.info("GLCapabilities: " + caps.toString());

        return new AppContext(window, caps, windowParams);
    }

    public static int determineMaxMultisamples() {
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

        long mon = glfwGetPrimaryMonitor();
        long window = glfwCreateWindow(2, 2, "multisample", NULL, NULL);

        if (window == NULL)
            throw new IllegalStateException("Failed to create the GLFW window");



        glfwMakeContextCurrent(window);
        GLCapabilities caps = createCapabilities();

        int maxMultisamples = 1;
        try (MemoryStack frame = MemoryStack.stackPush()) {
            IntBuffer intBuf = frame.mallocInt(1);
            glGetIntegerv(GL_MAX_SAMPLES, intBuf);
            maxMultisamples = intBuf.get();
            glfwDestroyWindow(window);
        }

        return maxMultisamples;
    }
}
