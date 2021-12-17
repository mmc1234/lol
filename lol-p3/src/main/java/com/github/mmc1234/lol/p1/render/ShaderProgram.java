package com.github.mmc1234.lol.p1.render;

import org.lwjgl.opengl.*;

public class ShaderProgram {
    private int program;
    private String vertexSource;
    private String fragmentSource;

    public int getProgram() {
        return program;
    }

    private ShaderProgram(String vertexSource, String fragmentSource) {
        this.vertexSource = vertexSource;
        this.fragmentSource = fragmentSource;
    }
    public static final ShaderProgram newInstance(String vertexSource, String fragmentSource) {
        return new ShaderProgram(vertexSource, fragmentSource);
    }
    public void init() {
        if(program == 0) {
            program = GL33.glCreateProgram();

            int vs = GL33.glCreateShader(GL33.GL_VERTEX_SHADER);
            int fs = GL33.glCreateShader(GL33.GL_FRAGMENT_SHADER);
            GL33.glShaderSource(vs, vertexSource);
            GL33.glShaderSource(fs, fragmentSource);
            GL33.glCompileShader(fs);
            GL33.glCompileShader(vs);
            GL33.glAttachShader(program, vs);
            GL33.glAttachShader(program, fs);

            GL33.glLinkProgram(program);
            if (GL33.glGetProgrami(program, GL33.GL_LINK_STATUS) == GL11.GL_FALSE) {
                System.out.println("(\n" + GL33.glGetProgramInfoLog(program) + "\n)");
            }
            GL33.glValidateProgram(program);

            GL33.glDeleteShader(vs);
            GL33.glDeleteShader(fs);
        }
    }

    public void close() {
        if(program != 0) {
            GL33.glDeleteProgram(program);
            program = 0;
        }
    }

}
