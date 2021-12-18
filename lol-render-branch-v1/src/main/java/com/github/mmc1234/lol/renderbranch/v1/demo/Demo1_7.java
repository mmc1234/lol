package com.github.mmc1234.lol.renderbranch.v1.demo;

import com.github.mmc1234.lol.base.Timer;
import com.github.mmc1234.lol.base.*;
import com.github.mmc1234.lol.glfw.*;
import com.github.mmc1234.lol.renderbranch.v1.*;
import jdk.incubator.foreign.*;
import org.joml.*;
import org.lwjgl.opengl.*;
import org.lwjgl.stb.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.Random;


import static org.lwjgl.stb.STBImage.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Demo1_7 extends AbstractApp {

    public static void main(String[] args) {
        new Demo1_7().run();
    }
    private Camera mainCamera;
    int msLoc, projectionLoc, mvmLoc;

    Random rand = new Random(System.currentTimeMillis());

    StaticTexture2D white = new StaticTexture2D(TextureFilterMode.LINEAR, TextureFilterMode.POINT,
                                              TextureFormat.R8G8B8A8_UINT, TextureFormat.R8G8B8A8_UINT, 1, 1);

    IntArray pixels;
    Float3Array vertices;
    Float4Array colors;
    Float2Array uvs;

    Vao vao;
    VboGroup vboGroup;
    ShaderProgram program;
    StaticTexture2D blueNoise = new StaticTexture2D(TextureFilterMode.LINEAR, TextureFilterMode.POINT,
            TextureFormat.R8G8B8A8_UINT, TextureFormat.R8G8B8A8_UINT, 256, 256);

    StaticTexture2D skyboxTex = new StaticTexture2D(TextureFilterMode.LINEAR, TextureFilterMode.POINT,
            TextureFormat.R8G8B8A8_UINT, TextureFormat.R8G8B8A8_UINT, 128, 128);

    CoordRenderData coordRenderData;

    public Demo1_7() {

    }
    @Override
    public void onLogicStart() {
        mainCamera = new Camera();
        mainCamera.setClearMask(GL33.GL_COLOR_BUFFER_BIT);
        mainCamera.setFrameBuffer(FrameBuffer.newDefault());

        ByteBuffer buf;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            buf = STBImage.stbi_load(Demo1_7.class.getClassLoader().getResource("skybox.png").getFile(), w, h, channels, 4);
            if (buf == null) {
                throw new RuntimeException("Image file [] not loaded: " + stbi_failure_reason());
            }
        }

        vao = new Vao();
        coordRenderData = new CoordRenderData();
        vboGroup = VboGroup.newInstance( (vboGroup, idx) -> {
                    switch (idx) {
                        case 0->vboGroup.setData(vertices.getSegment().address(), (int) vertices.getSegment().byteSize());
                        case 1->vboGroup.setData(colors.getSegment().address(), (int) colors.getSegment().byteSize());
                        case 2->vboGroup.setData(uvs.getSegment().address(), (int) vertices.getSegment().byteSize());
                        default -> throw new IllegalArgumentException();
                    }
                },
                VertexAttribDescription.of(TypeFormat.FLOAT32, 3),
                VertexAttribDescription.of(TypeFormat.FLOAT32, 4),
                VertexAttribDescription.of(TypeFormat.FLOAT32, 2));
        program = ShaderProgram.newInstance("""
                #version 150
                in vec3 vertexCoord;
                in vec4 vertexColor;
                in vec2 vertexUV;
                                    
                out vec4 fragmentColor;
                out vec2 fragmentUV;
                
                uniform mat4 projectionMatrix;
                uniform mat4 modelViewMatrix;
                                    
                void main() {
                  gl_Position = projectionMatrix*modelViewMatrix*vec4(vertexCoord,1);
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
        vertices = Float3Array.newFloat3Array(6);
        colors = Float4Array.newFloat4Array(6);
        uvs = Float2Array.newFloat2Array(6);
        pixels = IntArray.newIntArray(blueNoise.getWidth() * blueNoise.getWidth());

        vertices.set(0, -1, 1, -1);
        vertices.set(1, -1, -1, -1);
        vertices.set(2, 1, 1, -1);
        vertices.set(3, 1, 1, -1);
        vertices.set(4, -1, -1, -1);
        vertices.set(5, 1, -1, -1);
        colors.set(0, 1, 1, 1, 1);
        colors.set(1, 1, 1, 1, 1);
        colors.set(2, 1, 1, 1, 1);
        colors.set(3, 1, 1, 1, 1);
        colors.set(4, 1, 1, 1, 1);
        colors.set(5, 1, 1, 1, 1);
        uvs.set(0, 0, 0);
        uvs.set(1, 0, 1);
        uvs.set(2, 1, 0);
        uvs.set(3, 1, 0);
        uvs.set(4, 0, 1);
        uvs.set(5, 1, 1);
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                //int r = Math.min(rand.nextInt(0b01111111), rand.nextInt(0b01111111)); // red
                //int b = Math.min(rand.nextInt(0b01111111), rand.nextInt(0b01111111));
                int red = rand.nextInt(0b01111110);
                int alpha = 0b01111111;
                MemoryAccess.setByteAtOffset(pixels.getSegment(), (j * 256 + i)*4+0, (byte) red);
                MemoryAccess.setByteAtOffset(pixels.getSegment(), (j * 256 + i)*4+1, (byte) red);
                MemoryAccess.setByteAtOffset(pixels.getSegment(), (j * 256 + i)*4+2, (byte) red);
                MemoryAccess.setByteAtOffset(pixels.getSegment(), (j * 256 + i)*4+3, (byte) alpha);
            }
        }
        blueNoise.setPixelsHint(pixels.getSegment());
        blueNoise.setMipmapLevelHint(2);
        IntArray whitePixels = IntArray.newIntArray(1);
        whitePixels.x(0, 0b01111111_01111111_01111111_01111111);
        white.setPixelsHint(whitePixels.getSegment());
        pixels = null;
    }

    public Camera getMainCamera() {
        return mainCamera;
    }

    @Override
    public void onRenderStart() {
        white.init();
        blueNoise.init();
        program.init();
        vao.init();
        GL33.glBindVertexArray(vao.getVao());
        vboGroup.init();
        coordRenderData.init();

        msLoc = GL33.glGetUniformLocation(program.getProgram(), "mySampler");
        projectionLoc = GL33.glGetUniformLocation(program.getProgram(), "projectionMatrix");
        mvmLoc = GL33.glGetUniformLocation(program.getProgram(), "modelViewMatrix");

        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, 0);
        GL33.glBindVertexArray(0);
        getMainCamera().transform.getPosition().set(0, 0, 0.01f);
    }

    public void onWindow(double delta) {
        Vector3f offset = new Vector3f();
        float speed = 1;

        // move lr
        if(GLFW.glfwGetKey(getWindow(), GLFW.GLFW_KEY_A) != GLFW.GLFW_RELEASE) {
            offset.x-=speed*delta;
        }
        if(GLFW.glfwGetKey(getWindow(), GLFW.GLFW_KEY_D) != GLFW.GLFW_RELEASE) {
            offset.x+=speed*delta;
        }

        // move tb
        if(GLFW.glfwGetKey(getWindow(), GLFW.GLFW_KEY_Q) != GLFW.GLFW_RELEASE) {
            offset.y+=speed*delta;
        }
        if(GLFW.glfwGetKey(getWindow(), GLFW.GLFW_KEY_E) != GLFW.GLFW_RELEASE) {
            offset.y-=speed*delta;
        }

        // move fb
        if(GLFW.glfwGetKey(getWindow(), GLFW.GLFW_KEY_W) != GLFW.GLFW_RELEASE) {
            offset.z-=speed*delta;
        }
        if(GLFW.glfwGetKey(getWindow(), GLFW.GLFW_KEY_S) != GLFW.GLFW_RELEASE) {
            offset.z+=speed*delta;
        }

        getMainCamera().transform.movePosition(offset.x, offset.y, offset.z);

        var displVec = windowHandler.getDisplVec();
        var previousPos = windowHandler.previousPos;
        var currentPos = windowHandler.currentPos;
        displVec.x = 0;
        displVec.y = 0;
        if (previousPos.x > 0 && previousPos.y > 0 && windowHandler.isEnter()) {
            double deltax = currentPos.x - previousPos.x;
            double deltay = currentPos.y - previousPos.y;
            boolean rotateX = deltax != 0;
            boolean rotateY = deltay != 0;
            if (rotateX) {
                displVec.y = (float) deltax;
            }
            if (rotateY) {
                displVec.x = (float) deltay;
            }
        }
        previousPos.x = currentPos.x;
        previousPos.y = currentPos.y;

        if (GLFW.glfwGetMouseButton(getWindow(), GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS) {
            Vector2f rotVec = windowHandler.getDisplVec();
            getMainCamera().transform.moveRotation(rotVec.x * 0.25f, rotVec.y * 0.25f, 0);
        }
    }

    Timer windowTimer = Timer.newSystem(()-> onWindow(0.01), 10, false);
    
    @Override
    public void onWindow() {
        while(windowTimer.shouldRun()) {
            windowTimer.tryRun();
        }
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
            System.out.println(getMainCamera().transform.position);
        }

        GL33.glUseProgram(program.getProgram());
        GL33.glBindVertexArray(vao.getVao());
        GL33.glUniform1i(msLoc, 0);
        var mat = new Matrix4f().identity();

        var viewMatrix = getMainCamera().getViewMatrix();
        var projectionMatrix = getMainCamera().getProjectionMatrix();
        var modelViewMatrix = getMainCamera().getModelViewMatrix(viewMatrix);

        GL33.glLineWidth(1);
        GL33.glUniformMatrix4fv(projectionLoc, false, projectionMatrix.get(new float[16]));
        GL33.glUniformMatrix4fv(mvmLoc, false, modelViewMatrix.get(new float[16]));
        GL33.glActiveTexture(GL33.GL_TEXTURE0+0);
        GL33.glBindTexture(GL33.GL_TEXTURE_2D, blueNoise.getNativeTexture());
        GL33.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);

        GL33.glLineWidth(4);
        GL33.glActiveTexture(GL33.GL_TEXTURE0+0);
        GL33.glBindTexture(GL33.GL_TEXTURE_2D, white.getNativeTexture());
        GL33.glBindVertexArray(coordRenderData.getVao().getVao());
        GL33.glDrawArrays(GL11.GL_LINES, 0, 6);

        GL33.glBindTexture(GL33.GL_TEXTURE_2D, 0);
        GL33.glBindVertexArray(0);
        GL33.glUseProgram(0);
        lastFrame++;
    }

    @Override
    public void onRenderStop() {
        coordRenderData.close();
        vboGroup.close();
        vao.close();
        program.close();
        blueNoise.close();
        white.close();
    }
}
