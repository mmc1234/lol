package com.github.mmc1234.lol.renderbranch.v1.demo;

import com.github.mmc1234.lol.base.*;

import com.github.mmc1234.lol.renderbranch.v1.*;
import org.lwjgl.opengl.*;

import java.util.*;

public class Demo1_5 extends AbstractApp {

    public static void main(String[] args) {
        new Demo1_5().run();
    }
    private Camera mainCamera;
    int msLoc, vmLoc;

    Random rand = new Random(1234);

    IntArray pixels;
    Float2Array vertices;
    Float4Array colors;
    Float2Array uvs;

    Vao vao;
    VboGroup vboGroup;
    ShaderProgram program;
    StaticTexture2D blueNoise = new StaticTexture2D(TextureFilterMode.LINEAR, TextureFilterMode.POINT,
            TextureFormat.R8G8B8A8_UINT, TextureFormat.R8G8B8A8_UINT, 256, 256);

    public Demo1_5() {
        mainCamera = new Camera();
        mainCamera.setClearMask(GL33.GL_COLOR_BUFFER_BIT);
        mainCamera.setFrameBuffer(FrameBuffer.newDefault());
    }

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
                
                uniform mat4 viewMatrix;
                                    
                void main() {
                  gl_Position = viewMatrix*vec4(vertexCoord, 0,1);
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
        pixels = IntArray.newIntArray(blueNoise.getWidth() * blueNoise.getWidth());
        vertices.set(0, 0, 0.5f);
        vertices.set(1, -0.5f, -0.5f);
        vertices.set(2, 0.5f, -0.5f);
        colors.set(0, 1, 1, 1, 1);
        colors.set(1, 1, 1, 1, 1);
        colors.set(2, 1, 1, 1, 1);
        uvs.set(0, 0.5f, 0);
        uvs.set(1, 0, 1);
        uvs.set(2, 1, 1);
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                pixels.x(j * 256 + i,
                        0x00005555 + rand.nextInt(3333));
            }
        }
        blueNoise.setPixelsHint(pixels.getSegment());
        blueNoise.setMipmapLevelHint(2);
        pixels = null;
    }

    public Camera getMainCamera() {
        return mainCamera;
    }

    @Override
    public void onRenderStart() {
        blueNoise.init();
        program.init();
        vao.init();
        GL33.glBindVertexArray(vao.getVao());
        vboGroup.init();

        msLoc = GL33.glGetUniformLocation(program.getProgram(), "mySampler");
        vmLoc = GL33.glGetUniformLocation(program.getProgram(), "viewMatrix");

        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, 0);
        GL33.glBindVertexArray(0);
    }


    @Override
    public void onWindow() {
    }

    int lastFrame = -1;

    @Override
    public void onBeforeRender() {
        GL33.glBindFramebuffer(GL33.GL_DRAW_FRAMEBUFFER, getMainCamera().getFrameBuffer().getNativeFrameBuffer());
        getMainCamera().clear();
    }

    @Override
    public void onRender() {
        if(lastFrame%60==0) {
            getMainCamera().setBackground(new Float4(rand.nextFloat(0.5f, 0.9f),
                    rand.nextFloat(0.5f, 0.9f),
                    rand.nextFloat(0.5f, 0.9f), 1));
        }

        GL33.glUseProgram(program.getProgram());
        GL33.glBindVertexArray(vao.getVao());
        GL33.glUniform1i(msLoc, 0);
        getMainCamera().movePosition(0.001f, 0, 0);
        GL33.glUniformMatrix4fv(vmLoc, false, getMainCamera().getViewMatrix().get(new float[16]));
        GL33.glActiveTexture(GL33.GL_TEXTURE0+0);
        GL33.glBindTexture(GL33.GL_TEXTURE_2D, blueNoise.getNativeTexture());
        GL33.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);
        GL33.glBindTexture(GL33.GL_TEXTURE_2D, 0);
        GL33.glBindVertexArray(0);
        GL33.glUseProgram(0);
        lastFrame++;
    }

    @Override
    public void onRenderStop() {
        vboGroup.close();
        vao.close();
        program.close();
        blueNoise.close();
    }
}
