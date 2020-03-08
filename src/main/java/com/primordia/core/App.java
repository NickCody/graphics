package com.primordia.core;

import com.primordia.model.AppContext;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.memAddress;

public abstract class App {

    protected Logger log = LoggerFactory.getLogger(App.class);
    protected Integer windowWidth;
    protected Integer windowHeight;

    protected abstract AppContext getAppContext();

    GLFWFramebufferSizeCallback sizeCallback = new GLFWFramebufferSizeCallback() {
        @Override
        public void invoke(long window, int width, int height) {
            if (width > 0 && height > 0 && (windowWidth != width || windowHeight != height)) {
                windowWidth = width;
                windowHeight = height;
                glViewport(0, 0, windowWidth, windowHeight);
                onRender();
            }
        }
    };

    public void onInit() {
        log.info("GlfwApp::onInit");
    }

    public void  onExit() {
        log.info("GlfwApp::onExit");
    }

    public abstract void onRender();

    public void run() {
        log.info("GlfwApp::run");

        onInit();

        glfwSetFramebufferSizeCallback(getAppContext().getWindow(), sizeCallback);

        glClearColor(
                getAppContext().getWindowParams().getBackgroundColor().getRed(),
                getAppContext().getWindowParams().getBackgroundColor().getGreen(),
                getAppContext().getWindowParams().getBackgroundColor().getBlue(),
                getAppContext().getWindowParams().getBackgroundColor().getAlpha());
        glEnable(GL_DEPTH_TEST);

        // Center App Window
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(
                getAppContext().getWindow(),
                (vidmode.width() - getAppContext().getWindowParams().getWidth()) / 2,
                (vidmode.height() - getAppContext().getWindowParams().getHeight()) / 2
        );

        try (MemoryStack frame = MemoryStack.stackPush()) {
            IntBuffer framebufferSize = frame.mallocInt(2);
            nglfwGetFramebufferSize(getAppContext().getWindow(), memAddress(framebufferSize), memAddress(framebufferSize) + 4);
            windowWidth = framebufferSize.get(0);
            windowHeight = framebufferSize.get(1);
        }

        // Enable v-sync
        glfwSwapInterval(1);

        glfwShowWindow(getAppContext().getWindow());

        while ( !glfwWindowShouldClose(getAppContext().getWindow()) ) {
            glfwPollEvents();
            onRender();
        }

        onExit();

        try {
            sizeCallback.free();
            glfwDestroyWindow(getAppContext().getWindow());
        } catch (Throwable t ){
            t.printStackTrace();
        } finally {
            glfwTerminate();
        }
    }
}
