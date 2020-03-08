package com.primordia.util;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class WindowIconLoader {
    static public void setIcons(Long window, String[] icons) {
        GLFWImage.Buffer images = GLFWImage.malloc(icons.length);

        for(int i=0; i < icons.length; i++) {
            try {
                BufferedImage img = ImageImporter.getBufferedImage(ImageImporter.getResourceAsStream(icons[i]));
                GLFWImage image = GLFWImage.malloc();
                image.set(img.getWidth(), img.getHeight(), ImageImporter.loadImageToByteBuffer(img));
                images.put(i, image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        GLFW.glfwSetWindowIcon(window, images);

        freeImages(images);
    }

    static public void freeImages(GLFWImage.Buffer images) {
        // TODO: Crashes?
//        for(int i=0; i < images.limit(); i++) {
//            GLFWImage image = images.get(i);
//            image.free();
//        }
        images.free();
    }
}
