package com.primordia;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Hello world!
 *
 * Java NIO
 * http://tutorials.jenkov.com/java-nio/index.html
 *
 * JavaFX/OpenGL
 * https://bitbucket.org/cuchaz/jfxgl/src/default/
 *
 * GLFW
 * https://www.glfw.org/
 *
 */
public class EmptyGlfw
{
    public static void main( String[] args )
    {
        glfwInit();
        long window = createWindow();

        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents();
        }

        glfwTerminate();

    }

    private static long createWindow() {
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        long window = glfwCreateWindow(800, 600, "App", NULL, NULL);
        glfwMakeContextCurrent(window);
        createCapabilities();
        return window;
    }

}
