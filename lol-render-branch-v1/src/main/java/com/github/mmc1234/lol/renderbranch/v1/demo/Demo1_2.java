package com.github.mmc1234.lol.renderbranch.v1.demo;

import com.github.mmc1234.lol.base.*;
import com.github.mmc1234.lol.renderbranch.v1.*;
import org.lwjgl.opengl.*;

public class Demo1_2 extends AbstractApp {
    int vao, posVbo, colorVbo, program;

    Float2Array vertices;
    Float4Array colors;

    public static void main(String[] args) {
        new Demo1_2().run();
    }

    @Override
    public void onLogicStart() {
        vertices = Float2Array.newFloat2Array(3);
        colors = Float4Array.newFloat4Array(3);
        vertices.set(0, 0, 0.5f);
        vertices.set(1, -0.5f, -0.5f);
        vertices.set(2, 0.5f, -0.5f);
        colors.set(0, 1, 0, 0, 1);
        colors.set(1, 0, 1, 0, 1);
        colors.set(2, 0, 0, 1, 1);
    }

    @Override
    public void onRenderStart() {
        GL33.glUseProgram(0);
        program = GL33.glCreateProgram();
        // vs
        int vs = GL33.glCreateShader(GL33.GL_VERTEX_SHADER);

        GL33.glShaderSource(vs, """
                #version 150
                in vec2 vertexCoord;
                in vec4 vertexColor;
                                    
                out vec4 fragmentColor;
                                    
                void main() {
                  gl_Position = vec4(vertexCoord, 0,1);
                  fragmentColor = vertexColor;
                }
                """);

        GL33.glCompileShader(vs);
        GL33.glAttachShader(program, vs);


        // fs
        int fs = GL33.glCreateShader(GL33.GL_FRAGMENT_SHADER);

        GL33.glShaderSource(fs, """
                #version 150
                                    
                in vec4 fragmentColor;
                                    
                out vec4 finalFragmentColor;
                                    
                void main() {
                  finalFragmentColor = fragmentColor;
                }
                                    
                """);

        GL33.glCompileShader(fs);
        GL33.glAttachShader(program, fs);

        GL33.glLinkProgram(program);
        GL33.glValidateProgram(program);
        GL33.glDeleteShader(vs);
        GL33.glDeleteShader(fs);

        vao = GL33.glGenVertexArrays();
        GL33.glBindVertexArray(vao);

        posVbo = GL33.glGenBuffers();
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, posVbo);
        GL33.glVertexAttribPointer(0, 2, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(0);
        GL33.nglBufferData(GL33.GL_ARRAY_BUFFER, vertices.getSegment().byteSize(), vertices.getSegment().address().toRawLongValue(), GL33.GL_STATIC_DRAW);

        colorVbo = GL33.glGenBuffers();
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, colorVbo);
        GL33.glVertexAttribPointer(1, 4, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(1);
        GL33.nglBufferData(GL33.GL_ARRAY_BUFFER, colors.getSegment().byteSize(), colors.getSegment().address().toRawLongValue(), GL33.GL_STATIC_DRAW);

        // Unbind
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, 0);
        GL33.glBindVertexArray(0);
    }

    @Override
    public void onRender() {
        GL33.glUseProgram(program);
        GL33.glBindVertexArray(vao);
        GL33.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);
        GL33.glBindVertexArray(0);
        GL33.glUseProgram(0);
    }

    @Override
    public void onRenderStop() {
        if(program != 0) {
            GL33.glDeleteProgram(program);
            program = 0;
        }
        if(vao != 0) {
            GL33.glDeleteVertexArrays(vao);
        }
        if(posVbo != 0) {
            GL33.glDeleteBuffers(posVbo);
            posVbo = 0;
        }
        if(colorVbo != 0) {
            GL33.glDeleteBuffers(colorVbo);
            colorVbo = 0;
        }

    }
}
