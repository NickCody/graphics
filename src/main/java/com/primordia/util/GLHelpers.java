package com.primordia.util;

import org.lwjgl.BufferUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.math.Ordering;

import java.io.IOException;
import java.io.InputStream;
import java.nio.*;
import java.util.Scanner;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;


public class GLHelpers {
    protected static Logger log = LoggerFactory.getLogger(GLHelpers.class);

    //
    // Array Buffer Help
    //

    public static int createArrayBuffer3f(float[] data) throws RuntimeException {
        // Setup Array Buffer
        //
        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        Buffer buffer = BufferUtils.createFloatBuffer(data.length).put(data);
        glBufferData(GL_ARRAY_BUFFER, (FloatBuffer)buffer.flip(), GL_STATIC_DRAW);
        if (!glIsBuffer(vbo)) {
            throw new RuntimeException("vbo is not a buffer!");
        }

        int vao = glGenVertexArrays();
        glBindVertexArray(vao);
        if (!glIsVertexArray(vao)) {
            throw new RuntimeException("vao is not a vertex array!");
        }

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0L);
        glEnableVertexAttribArray(0);

        return vao;
    }

    //
    // Shader Help
    //
    public static Integer generateVertexShader(String code) {
        return createAndCompileShader(code, GL_VERTEX_SHADER);
    }

    public static Integer generateFragmentShader(String code) {
        return createAndCompileShader(code, GL_FRAGMENT_SHADER);
    }

    public static Integer createAndCompileShader(String source, int shaderKind) throws RuntimeException {
        int shader = glCreateShader(shaderKind);
        glShaderSource(shader, source);
        glCompileShader(shader);

        if (!glIsShader(shader)) {
            throw new RuntimeException(source + "is not a shader or not compiled!");
        }

        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            String shaderLog = glGetShaderInfoLog(shader);
            glDeleteShader(shader);
            throw new RuntimeException("\n" + source + "\n...is not compiled!\nError was: " + shaderLog);
        }

        log.info("Successfully created shader!");
        log.debug(source);
        return shader;
    }

    public static String loadResource(String resourceName) throws IOException {
        InputStream stream = GLHelpers.class.getClassLoader().getResourceAsStream(resourceName);

        if (stream == null) {
            throw new IOException("Could not read " + resourceName);
        }

        Scanner s = new Scanner(stream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static Integer createShaderProgram(Integer[] shaders) throws RuntimeException {
        int shader_prog = glCreateProgram();

        for(int shader : shaders) {
            glAttachShader(shader_prog, shader);
            glAttachShader(shader_prog, shader);
        }

        glLinkProgram(shader_prog);

        if (!glIsProgram(shader_prog)) {
            throw new RuntimeException("Could not link shader program!");
        }

        return shader_prog;
    }
}


