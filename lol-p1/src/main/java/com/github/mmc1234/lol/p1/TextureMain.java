package com.github.mmc1234.lol.p1;

import com.github.mmc1234.lol.base.*;

import com.github.mmc1234.lol.p1.core.*;

import org.lwjgl.opengl.*;

import java.util.*;

public class TextureMain extends AbstractApp {
    public static void main(String[] args) {
        new TextureMain().run();
    }

    int vao, posVbo, colorVbo, uvVbo, program, myTexture, msLoc;

    IntArray pixels;
    Float2Array vertices;
    Float4Array colors;
    Float2Array uvs;

    @Override
    public void onLogicStart() {
        vertices = Float2Array.newFloat2Array(3);
        colors = Float4Array.newFloat4Array(3);
        uvs = Float2Array.newFloat2Array(3);
        pixels = IntArray.newIntArray(32 * 32);
        vertices.set(0, 0, 0.5f);
        vertices.set(1, -0.5f, -0.5f);
        vertices.set(2, 0.5f, -0.5f);
        colors.set(0, 1, 1, 1, 1);
        colors.set(1, 1, 1, 1, 1);
        colors.set(2, 1, 1, 1, 1);
        uvs.set(0, 0.5f, 0);
        uvs.set(1, 0, 1);
        uvs.set(2, 1, 1);
        Random colorRand = new Random(124);
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 32; j++) {
                pixels.x(j * 32 + i,
                        0x00005555 + colorRand.nextInt(3333));
            }
        }
    }

    @Override
    public void onRenderStart() {
        program = GL33.glCreateProgram();

        myTexture = GL33.glGenTextures();

        GL33.glActiveTexture(GL33.GL_TEXTURE0 + 0);
        GL33.glBindTexture(GL33.GL_TEXTURE_2D, myTexture);
        GL33.glPixelStorei(GL33.GL_UNPACK_ALIGNMENT, 4);
        GL33.nglTexImage2D(GL33.GL_TEXTURE_2D, 0, GL33.GL_RGBA, 32, 32, 0,
                GL33.GL_RGBA, GL33.GL_UNSIGNED_BYTE, pixels.getSegment().address().toRawLongValue());

        GL33.glTexParameteri(GL33.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL33.glTexParameteri(GL33.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);


        // vs
        int vs = GL33.glCreateShader(GL33.GL_VERTEX_SHADER);

        GL33.glShaderSource(vs, """
                #version 150
                in vec2 vertexCoord;
                in vec4 vertexColor;
                in vec2 vertexUV;
                                    
                out vec4 fragmentColor;
                out vec2 fragmentUV;
                                    
                void main() {
                  gl_Position = vec4(vertexCoord, 0,1);
                  fragmentColor = vertexColor;
                  fragmentUV = vertexUV;
                }
                """);

        GL33.glCompileShader(vs);
        GL33.glAttachShader(program, vs);


        // fs
        int fs = GL33.glCreateShader(GL33.GL_FRAGMENT_SHADER);

        GL33.glShaderSource(fs, """
                #version 150
                                    
                in vec4 fragmentColor;
                in vec2 fragmentUV;
                                    
                out vec4 finalFragmentColor;
                                    
                uniform sampler2D texture_sampler;
                                    
                void main() {
                  finalFragmentColor = fragmentColor*texture(texture_sampler, fragmentUV);
                }
                                    
                """);

        GL33.glCompileShader(fs);
        GL33.glAttachShader(program, fs);

        GL33.glLinkProgram(program);
        if (GL33.glGetProgrami(program, GL33.GL_LINK_STATUS) == GL11.GL_FALSE) {
            System.out.println("(\n" + GL33.glGetProgramInfoLog(program) + "\n)");
        }
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

        uvVbo = GL33.glGenBuffers();
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, uvVbo);
        GL33.glVertexAttribPointer(2, 2, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(2);
        GL33.nglBufferData(GL33.GL_ARRAY_BUFFER, uvs.getSegment().byteSize(), uvs.getSegment().address().toRawLongValue(), GL33.GL_STATIC_DRAW);

        // Unbind
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, 0);
        GL33.glBindVertexArray(0);

        GL33.glUseProgram(program);
        msLoc = GL33.glGetUniformLocation(program, "texture_sampler");

        GL33.glUseProgram(0);
    }

    @Override
    public void onRender() {
        GL33.glUseProgram(program);
        GL33.glBindVertexArray(vao);
        GL33.glUniform1i(msLoc, 0);
        GL33.glBindTexture(GL33.GL_TEXTURE_2D, myTexture);
        GL33.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);
        GL33.glBindTexture(GL33.GL_TEXTURE_2D, 0);
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
        if(uvVbo != 0) {
            GL33.glDeleteBuffers(uvVbo);
            uvVbo = 0;
        }
        if(myTexture != 0) {
            GL33.glDeleteTextures(myTexture);
            myTexture = 0;
        }
    }
}
