package com.primordia.graphics.core;

import com.primordia.graphics.model.AppContext;
import org.eclipse.swt.widgets.Display;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

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
    protected Display display = new Display();
    protected List<SwtWindow> auxWindows = new ArrayList<>();
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
    public void onProcessMessages() {}
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

        if (getAppContext().getWindowParams().getFullScreen()) {
            makeFullScreen();
        }

        try (MemoryStack frame = MemoryStack.stackPush()) {
            IntBuffer framebufferSize = frame.mallocInt(2);
            nglfwGetFramebufferSize(getAppContext().getWindow(), memAddress(framebufferSize), memAddress(framebufferSize) + 4);
            windowWidth = framebufferSize.get(0);
            windowHeight = framebufferSize.get(1);
        }

        // Enable v-sync
        glfwSwapInterval(1);

        if (getAppContext().getWindowParams().getMultiSamples() > 1) {
            glEnable(GL_MULTISAMPLE);
        }

        glfwMakeContextCurrent(getAppContext().getWindow());
        glfwShowWindow(getAppContext().getWindow());
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
            currentSeconds = glfwGetTime();

            glfwMakeContextCurrent(getAppContext().getWindow());
            onRender();

//            for (SwtWindow swt : auxWindows) {
//                swt.onRender();
//            }

//            glfwMakeContextCurrent(getAppContext().getWindow());

            display.readAndDispatch();

            if (getAppContext().getWindowParams().getFullScreen() && GLFW_PRESS == glfwGetKey(getAppContext().getWindow(), GLFW_KEY_ESCAPE)) {
                glfwSetWindowShouldClose(getAppContext().getWindow(), true);
            }
        }

        try {

            onExit();
            sizeCallback.free();
            cursorCallback.free();
            glfwDestroyWindow(getAppContext().getWindow());

            for (SwtWindow swt : auxWindows) {
                swt.close();
            }

            display.dispose();

            if (getAppContext().getErrCallback() != null)
                getAppContext().getErrCallback().free();

            if (getAppContext().getDebugMessageCallback() != null)
                getAppContext().getDebugMessageCallback().free();

        } catch (Throwable t ){
            t.printStackTrace();
        } finally {
            glfwTerminate();
        }
    }

    public void makeFullScreen() {
        PointerBuffer mons = glfwGetMonitors();
        if (mons == null) {
            throw new RuntimeException("Could not get monitors");
        }

        long mon = mons.get(getAppContext().getWindowParams().getMonitor());

        GLFWVidMode vidmode = glfwGetVideoMode(mon);
        if ( vidmode == null) {
            throw new RuntimeException("glfwGetVideoMode(" + mon + ") failed.");
        }
        glfwSetWindowPos(
                getAppContext().getWindow(),
                (vidmode.width() - getAppContext().getWindowParams().getWidth()) / 2,
                (vidmode.height() - getAppContext().getWindowParams().getHeight()) / 2
        );
    }

    public Display getDisplay() {
        return this.display;
    }

    public void addAuxWindow(SwtWindow sw) {
        auxWindows.add(sw);
    }
}
