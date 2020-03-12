package com.primordia.util;

import org.lwjgl.BufferUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.FloatBuffer;
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

    public static int createVertexArray3f(float[] data) throws RuntimeException {
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

        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0L);

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

    public static Integer createAndCompileShader(String code, int shaderKind) throws RuntimeException {
        int vs = glCreateShader(shaderKind);
        glShaderSource(vs, code);
        glCompileShader(vs);

        if (!glIsShader(vs)) {
            throw new RuntimeException(code + "is not a shader or not compiled!");
        }

        if (glGetShaderi(vs, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new RuntimeException(code + "is not compiled!");
        }

        log.info("Successfully created shader: " + code);
        return vs;
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


