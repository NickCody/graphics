package com.primordia.util;

import org.lwjgl.BufferUtils;

import java.nio.Buffer;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;


public class GLHelpers {

    //
    // Array Buffer Help
    //

    public static int createVertexArray3f(float[] data) {
        // Setup Array Buffer
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

    public static Integer createAndCompileShader(String code, int shaderKind) {
        int vs = glCreateShader(shaderKind);
        glShaderSource(vs, code);
        glCompileShader(vs);

        if (!glIsShader(vs)) {
            throw new RuntimeException(code + "is not a shader or not compiled!");
        }

        if (glGetShaderi(vs, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new RuntimeException(code + "is not compiled!");
        }

        return vs;
    }
}


