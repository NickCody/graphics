package com.primordia.util;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

// Resources
//
// https://www.programcreek.com/java-api-examples/?api=org.lwjgl.glfw.GLFWImage
// http://www.java-gaming.org/topics/lwjgl-3-glfwsetwindowicon-does-not-work-because-i-didn-t-use-directbuff-solved/37601/view.html
//
public class ImageImporter {
    public static InputStream getResourceAsStream(String image) {
        InputStream stream = ImageImporter.class.getClassLoader().getResourceAsStream(image);
        if (stream == null) {
            throw new NullPointerException("Null stream");
        }

        return stream;
    }

    public static BufferedImage getBufferedImage(InputStream stream) throws IOException {
        return ImageIO.read(stream);
    }

    public static ByteBuffer loadImageToByteBuffer(final BufferedImage image) {
        final byte[] buffer = new byte[image.getWidth() * image.getHeight() * 4];
        int counter = 0;
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                final int c = image.getRGB(j, i);
                buffer[counter + 0] = (byte) (c << 8 >> 24);
                buffer[counter + 1] = (byte) (c << 16 >> 24);
                buffer[counter + 2] = (byte) (c << 24 >> 24);
                buffer[counter + 3] = (byte) (c >> 24);
                counter += 4;
            }
        }
        ByteBuffer bb = BufferUtils.createByteBuffer(buffer.length);
        bb.put(buffer);
        bb.flip();
        return bb;
    }
}