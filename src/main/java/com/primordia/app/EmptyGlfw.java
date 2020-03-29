package com.primordia.app;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Hello world!
 *
 * Bare-bones GLFW App
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
        if (!glfwInit()) {
            System.out.println("glfwInit() failed!");
            System.exit(1);
        }

        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        long window = glfwCreateWindow(800, 600, "App", NULL, NULL);

        if (window == 0) {
            System.out.println("Could not create GLFW Window!");
            System.exit(1);
        }

        glfwMakeContextCurrent(window);
        createCapabilities();


        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents();
        }

        glfwTerminate();

    }
}
