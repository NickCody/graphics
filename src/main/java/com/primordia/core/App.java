package com.primordia.core;

import com.primordia.model.AppContext;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;
import static org.lwjgl.system.MemoryUtil.memAddress;

//
// Multisampling: https://learnopengl.com/Advanced-OpenGL/Anti-Aliasing
//
public abstract class App {

    protected Logger log = LoggerFactory.getLogger(App.class);
    protected Integer windowWidth;
    protected Integer windowHeight;
    protected Integer mouseX = 0;
    protected Integer mouseY = 0;
    protected Double  currentSeconds = 0.0;

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

    GLFWCursorPosCallback cursorCallback = new GLFWCursorPosCallback() {
        @Override
        public void invoke(long window, double xpos, double ypos) {
            mouseX = (int)xpos;
            mouseY = (int)ypos;
            onRender();
        }
    };

    // Overrides
    public void onBeforeInit() {}
    public void onAfterInit() {}
    public void onExit() {}
    public abstract void onRender();

    private void internalInit() {
        glfwSetFramebufferSizeCallback(getAppContext().getWindow(), sizeCallback);
        glfwSetCursorPosCallback(getAppContext().getWindow(), cursorCallback);
        glClearColor(
                getAppContext().getWindowParams().getBackgroundColor().getRed(),
                getAppContext().getWindowParams().getBackgroundColor().getGreen(),
                getAppContext().getWindowParams().getBackgroundColor().getBlue(),
                getAppContext().getWindowParams().getBackgroundColor().getAlpha());
        glEnable(GL_DEPTH_TEST);

        // Center App Window
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        if (!getAppContext().getWindowParams().getFullScreen()) {
            glfwSetWindowPos(
                    getAppContext().getWindow(),
                    (vidmode.width() - getAppContext().getWindowParams().getWidth()) / 2,
                    (vidmode.height() - getAppContext().getWindowParams().getHeight()) / 2
            );
        }

        try (MemoryStack frame = MemoryStack.stackPush()) {
            IntBuffer framebufferSize = frame.mallocInt(2);
            nglfwGetFramebufferSize(getAppContext().getWindow(), memAddress(framebufferSize), memAddress(framebufferSize) + 4);
            windowWidth = framebufferSize.get(0);
            windowHeight = framebufferSize.get(1);
        }

        // Enable v-sync
        glfwSwapInterval(1);

        glEnable(GL_MULTISAMPLE);

        glfwShowWindow(getAppContext().getWindow());

        // nvidia insight said this was fairly expensive 150micros
        // If we're only a single window (single thread?) this is probably fine.

        glfwMakeContextCurrent(getAppContext().getWindow());
    }

    public void run() {
        log.info("GlfwApp::run");

        //
        // Initialization
        //
        onBeforeInit();
        internalInit();
        onAfterInit();

        //
        // Render
        //
        while ( !glfwWindowShouldClose(getAppContext().getWindow()) ) {
            onRender();

            glfwPollEvents();

            currentSeconds = glfwGetTime();

            if (getAppContext().getWindowParams().getFullScreen() && GLFW_PRESS == glfwGetKey(getAppContext().getWindow(), GLFW_KEY_ESCAPE)) {
                glfwSetWindowShouldClose(getAppContext().getWindow(), true);
            }
        }

        try {
            onExit();
            sizeCallback.free();
            cursorCallback.free();
            glfwDestroyWindow(getAppContext().getWindow());
        } catch (Throwable t ){
            t.printStackTrace();
        } finally {
            glfwTerminate();
        }
    }

}
