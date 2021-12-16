package com.github.mmc1234.lol.p1;

import com.github.mmc1234.lol.base.*;

import com.github.mmc1234.lol.p1.core.*;

import com.github.mmc1234.lol.p1.render.*;
import org.lwjgl.opengl.*;

import java.util.*;

public class Main extends AbstractApp {
    public static void main(String[] args) {
        new Main().run();
    }

    int myTexture, msLoc;

    IntArray pixels;
    Float2Array vertices;
    Float4Array colors;
    Float2Array uvs;

    Vao vao;
    VboGroup vboGroup;
    ShaderProgram program;

    @Override
    public void onLogicStart() {
        vao = new Vao();
        vboGroup = VboGroup.newInstance( (vboGroup, idx) -> {
                    switch (idx) {
                        case 0->vboGroup.setData(vertices.getSegment().address(), (int) vertices.getSegment().byteSize());
                        case 1->vboGroup.setData(colors.getSegment().address(), (int) colors.getSegment().byteSize());
                        case 2->vboGroup.setData(uvs.getSegment().address(), (int) vertices.getSegment().byteSize());
                        default -> throw new IllegalArgumentException();
                    }
                },
                VertexAttribDescription.of(TypeFormat.FLOAT32, 2),
                VertexAttribDescription.of(TypeFormat.FLOAT32, 4),
                VertexAttribDescription.of(TypeFormat.FLOAT32, 2));
        program = ShaderProgram.newInstance("""
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
                """,
                """
                #version 150
                                    
                in vec4 fragmentColor;
                in vec2 fragmentUV;
                                    
                out vec4 finalFragmentColor;
                                    
                uniform sampler2D texture_sampler;
                                    
                void main() {
                  finalFragmentColor = fragmentColor*texture(texture_sampler, fragmentUV);
                }
                                    
                """);
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
        myTexture = GL33.glGenTextures();

        GL33.glActiveTexture(GL33.GL_TEXTURE0 + 0);
        GL33.glBindTexture(GL33.GL_TEXTURE_2D, myTexture);
        GL33.glPixelStorei(GL33.GL_UNPACK_ALIGNMENT, 4);
        GL33.nglTexImage2D(GL33.GL_TEXTURE_2D, 0, GL33.GL_RGBA, 32, 32, 0,
                GL33.GL_RGBA, GL33.GL_UNSIGNED_BYTE, pixels.getSegment().address().toRawLongValue());

        GL33.glTexParameteri(GL33.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL33.glTexParameteri(GL33.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        program.init();
        vao.init();
        GL33.glBindVertexArray(vao.getVao());
        vboGroup.init();

        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, 0);
        GL33.glBindVertexArray(0);
    }

    @Override
    public void onRender() {
        GL33.glUseProgram(program.getProgram());
        GL33.glBindVertexArray(vao.getVao());
        GL33.glUniform1i(msLoc, 0);
        GL33.glActiveTexture(GL33.GL_TEXTURE0+0);
        GL33.glBindTexture(GL33.GL_TEXTURE_2D, myTexture);
        GL33.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);
        GL33.glBindTexture(GL33.GL_TEXTURE_2D, 0);
        GL33.glBindVertexArray(0);
        GL33.glUseProgram(0);
    }

    @Override
    public void onRenderStop() {
        vboGroup.close();
        vao.close();
        program.close();
        if(myTexture != 0) {
            GL33.glDeleteTextures(myTexture);
            myTexture = 0;
        }
    }
}
