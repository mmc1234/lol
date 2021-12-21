/*
 * Copyright 2021. mmc1234
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mmc1234.lol.renderbranch.v1.legacy.demo;

import com.github.mmc1234.lol.base.Timer;
import com.github.mmc1234.lol.base.*;
import com.github.mmc1234.lol.glfw.*;
import com.github.mmc1234.lol.glfw.impl.*;
import com.github.mmc1234.lol.renderbranch.v1.*;
import com.github.mmc1234.lol.renderbranch.v1.legacy.Camera;
import com.github.mmc1234.lol.renderbranch.v1.legacy.*;
import com.google.common.base.*;
import com.google.inject.*;
import jdk.incubator.foreign.*;
import org.apache.commons.io.*;
import org.joml.*;
import org.lwjgl.opengl.*;
import org.lwjgl.stb.*;

import java.io.*;
import java.lang.*;
import java.lang.Math;
import java.util.*;
import java.util.concurrent.atomic.*;

import static com.github.mmc1234.lol.glfw.GLFW.*;

/**
 * 简单的资源使用演示，渲染了一个纹理
 * */
public class Demo1_5 {
    Window window = Window.ofLong(0);
    final AtomicBoolean shouldExit = new AtomicBoolean();
    final Timer renderTimer = Timer.newSystem(this::recordRender, 1000/60, true);
    Mesh quadMesh;
    Mesh coordMesh;
    VertexBuffer quadVertexBuffer;
    VertexBuffer coordVertexBuffer;
    List<VertexAttrib> defaultVertexAttribDescriptionList = VertexAttrib.list(
            TypeFormat.FLOAT32, 3,
            TypeFormat.FLOAT32, 2);
    ShaderProgram mainProgram;
    Texture2D myTexture;
    Camera cam = new Camera();
    double lastInputTime = -1;

    static final String SHADERS_PATH = "shaders/";

    public void init() {
        quadMesh = Mesh.create(defaultVertexAttribDescriptionList);
        quadMesh.setData(0, MemorySegmentUtil.createFromFloatArray(-1, 1, -1,  -1, -1, -1,  1, -1, -1,  1, 1, -1));
        quadMesh.setData(1, MemorySegmentUtil.createFromFloatArray(0, 0, 0, 1, 1, 1, 1, 0));
        quadMesh.setIndices(MemorySegmentUtil.createFromIntArray(0, 1, 3, 3, 1, 2));

        coordMesh = Mesh.create(defaultVertexAttribDescriptionList);
        coordMesh.setData(0, MemorySegmentUtil.createFromFloatArray(
                -1,-1,-1,  100,-1,-1,
                -1,-1,-1, -1, 100,-1,
                -1,-1,-1, -1,-1,100));
        coordMesh.setData(1, MemorySegmentUtil.createFromFloatArray(0, 0, 0, 0,
                0.5f, 0, 0.5f, 0,
                0.9f, 0, 0.9f, 0));
        coordMesh.setIndices(MemorySegmentUtil.createFromIntArray(0, 1, 2, 3, 4, 5));

        cam.setFov((float) Math.toRadians(60));
        cam.setAspect(4/3f);
        cam.setZNear(0.01f);
        cam.setZFar(1000);

        quadVertexBuffer = VertexBuffer.create(defaultVertexAttribDescriptionList);
        coordVertexBuffer = VertexBuffer.create(defaultVertexAttribDescriptionList);

        mainProgram = ShaderProgram.newInstance(
                ResourceUtil.loadModuleText(SHADERS_PATH+ "simple_cam_vertex.glsl"),
                ResourceUtil.loadModuleText(SHADERS_PATH+ "simple_uv_fragment.glsl"));

        RenderThread.launch();
        Render.recordRenderCall(this::renderStart);
    }

    private final Vector2d lastMousePos = new Vector2d(-1, -1);

    private final Vector2d mousePos = new Vector2d();

    private final Vector2d mouseDelta = new Vector2d();

    private boolean isHover, applyMouseDeltaToCamera;

    public void hovered(boolean isHover) {
        this.isHover = isHover;
    }
    public void cursorPos(double x, double y) {
        mousePos.x = x;
        mousePos.y = y;
    }
    public void renderStart() {
        Preconditions.checkState(Render.isRenderThread());
        glfwWindowHint( GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);
        window = glfwCreateWindow(400, 320, "DEMO", Monitor.ofLong(0), Window.ofLong(0));
        glfwMakeContextCurrent(window);
        glfwSwapInterval(0);
        GL.createCapabilities();
        glfwSetCursorPosCallback(window, (w,x,y)->cursorPos(x,y));
        glfwSetCursorEnterCallback(window, (w, h)->hovered(h));

        GL33.glEnable(KHRDebug.GL_DEBUG_OUTPUT);
        KHRDebug.glDebugMessageCallback((src, type, id, severity, len, msg, data) -> {
            System.out.printf("[Src=%s, Type=%s, Id=%d, Severity=%s]\n  %s\n",
                    DebugUtil.getSourceDescription(src),
                    DebugUtil.getTypeDescription(type),id,DebugUtil.getSeverityDescription(severity), GLDebugMessageCallback.getMessage(len, msg));
        }, 0);

        Image img = Sugars.noCatch(()->loadImage(Sugars.noCatch(()->ResourceUtil.getModuleResourceAsStream("textures/texture.png"))));

        myTexture = new Texture2D(TextureFormat.R8G8B8A8_UINT, img.w, img.h);
        myTexture.init();

        myTexture.reload(img.pixels);

        Texture2D.bindZero();

        mainProgram.init();
        mainProgram.createUniform("texture_sampler");
        mainProgram.createUniform("projection_matrix");
        mainProgram.createUniform("model_view_matrix");
        mainProgram.use();
        GL33.glUniform1i(mainProgram.getUniformLocation("texture_sampler"), 0);

        quadVertexBuffer.getVao().initAndBind();

        var posVbo = quadVertexBuffer.getVbo(0);
        posVbo.init();
        posVbo.reload(quadMesh);

        var uvVbo = quadVertexBuffer.getVbo(1);
        uvVbo.init();
        uvVbo.reload(quadMesh);

        coordVertexBuffer.getVao().initAndBind();

        posVbo = coordVertexBuffer.getVbo(0);
        posVbo.init();
        posVbo.reload(coordMesh);

        uvVbo = coordVertexBuffer.getVbo(1);
        uvVbo.init();
        uvVbo.reload(coordMesh);


        Vbo.bindZero();
        Vao.bindZero();
        ShaderProgram.useZero();
    }

    public void input() {
        double deltaTime = 0;
        if(lastInputTime == -1) {
            lastInputTime = glfwGetTime();
        } else {
            var time = glfwGetTime();
            deltaTime = time-lastInputTime;
            lastInputTime = time;
        }

        // mouse delta
        mouseDelta.x = 0;
        mouseDelta.y = 0;
        if (lastMousePos.x > 0 && lastMousePos.y > 0 && isHover) {
            double deltax = mousePos.x - lastMousePos.x;
            double deltay = mousePos.y - lastMousePos.y;
            boolean rotateX = deltax != 0;
            boolean rotateY = deltay != 0;
            if (rotateX) {
                mouseDelta.y = (float) deltax;
            }
            if (rotateY) {
                mouseDelta.x = (float) deltay;
            }
        }
        lastMousePos.x = mousePos.x;
        lastMousePos.y = mousePos.y;

        Vector3f offset = new Vector3f();

        if(notReleaseKey(GLFW_KEY_A)) {
            offset.x-=(float)(deltaTime*3);
        }
        if(notReleaseKey(GLFW_KEY_D)) {
            offset.x+=(float)(deltaTime*3);
        }

        if(notReleaseKey(GLFW_KEY_W)) {
            offset.z-=(float)(deltaTime*3);
        }
        if(notReleaseKey(GLFW_KEY_S)) {
            offset.z+=(float)(deltaTime*3);
        }

        if(notReleaseKey(GLFW_KEY_Q)) {
            offset.y+=(float)(deltaTime*3);
        }
        if(notReleaseKey(GLFW_KEY_E)) {
            offset.y-=(float)(deltaTime*3);
        }
        if(notReleaseKey(GLFW_KEY_ESCAPE)) {
            shouldExit.set(true);
        }

        cam.transform.movePosition(offset.x, offset.y, offset.z);

        if(GLFW.glfwGetMouseButton(window, 1) != GLFW_RELEASE) {
            cam.transform.moveRotation((float)mouseDelta.x*0.5f, (float)mouseDelta.y*0.5f, 0);
        }
    }

    public boolean isReleaseKey(int key) {
        return GLFW.glfwGetKey(window, key) == GLFW_RELEASE;
    }
    public boolean notReleaseKey(int key) {
        return GLFW.glfwGetKey(window, key) != GLFW_RELEASE;
    }


    public void render() {
        input();
        Preconditions.checkState(Render.isRenderThread());
        if(window.address() != 0) {
            if(glfwWindowShouldClose(window)) {
                shouldExit.set(true);
            }
            GL33.glClearColor(0.25f,0.25f, 0.25f, 1);
            GL33.glClear(GL33.GL_COLOR_BUFFER_BIT);
            mainProgram.use();
            cam.updateProjection();
            cam.updateViewMatrix();
            cam.buildModelViewMatrix(new Transform(), cam.getViewMatrix());
            GL33.glUniformMatrix4fv(mainProgram.getUniformLocation("projection_matrix"),
                    false, cam.getProjectionMatrix().get(new float[16]));
            GL33.glUniformMatrix4fv(mainProgram.getUniformLocation("model_view_matrix"),
                    false, cam.getModelViewMatrix().get(new float[16]));
            quadVertexBuffer.getVao().bind();
            GL33.glActiveTexture(GL33.GL_TEXTURE0);
            myTexture.bind();
            GL33.nglDrawElements(GL33.GL_TRIANGLES, 6, GL33.GL_UNSIGNED_INT, quadMesh.getIndices().address().toRawLongValue());
            GL33.glLineWidth(3);
            coordVertexBuffer.getVao().bind();
            GL33.nglDrawElements(GL33.GL_LINES, 6, GL33.GL_UNSIGNED_INT, coordMesh.getIndices().address().toRawLongValue());
            GL33.glLineWidth(1);
            Vao.bindZero();
            Texture2D.bindZero();
            ShaderProgram.useZero();
            glfwSwapBuffers(window);
        }
    }

    public void renderStop() {
        quadMesh.close();
        quadVertexBuffer.close();
        mainProgram.close();
        myTexture.close();
        Preconditions.checkState(Render.isRenderThread());
        if(window != Window.EMPTY) {
            glfwHideWindow(window);
            glfwDestroyWindow(window);
            window = Window.ofLong(0);
            RenderThread.makeShouldStop();
        }
    }
    public void recordRender() {
        Render.recordRenderCall(this::render);
    }
    public void close() {
        Render.recordRenderCall(()-> renderStop());
    }
    public void loop() {
        while(!shouldExit.get()) {
            if(!RenderThread.isThreadAlive()) {
                shouldExit.set(true);
                return;
            }
            renderTimer.tryRun();
            Sugars.noCatchRunnable(()->Thread.sleep(1));
        }
    }

    public Demo1_5() {
        init();
        loop();
        close();
        System.out.println("Stop Main");
    }

    static record Image(int w, int h, MemorySegment pixels) {
    }
    public Image loadImage(InputStream inputStream) throws IOException {
        var rawData = MemorySegmentUtil.createFromByteArray(IOUtils.toByteArray(inputStream));
        var out = MemorySegmentUtil.createFromIntArray(0,0,0);
        long data=rawData.address().toRawLongValue();
        int dataSize= (int) rawData.byteSize();
        long w = out.address().toRawLongValue();
        long h = out.address().toRawLongValue()+MemoryLayouts.JAVA_INT.byteSize();
        long c = out.address().toRawLongValue()+(MemoryLayouts.JAVA_INT.byteSize()*2);
        var pixelsAddress = STBImage.nstbi_load_from_memory(data, dataSize, w,h,c ,4);

        int imageWidth = MemoryAccess.getIntAtIndex(out, 0);
        int imageHeight = MemoryAccess.getIntAtIndex(out, 1);
        var imagePixels = MemoryAddress.ofLong(pixelsAddress).asSegment(imageWidth*imageHeight*4, ResourceScope.globalScope());
        return new Image(imageWidth, imageHeight, imagePixels);
    }

    public static void main(String[] args) {
        Guice.createInjector(new ImplGLFWModule());
        new Demo1_5();
    }
}
