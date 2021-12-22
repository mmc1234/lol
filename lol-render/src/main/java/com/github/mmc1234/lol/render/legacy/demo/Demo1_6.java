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

package com.github.mmc1234.lol.render.legacy.demo;

import com.github.mmc1234.lol.base.Timer;
import com.github.mmc1234.lol.base.*;
import com.github.mmc1234.lol.glfw.*;
import com.github.mmc1234.lol.glfw.impl.*;
import com.github.mmc1234.lol.render.*;
import com.github.mmc1234.lol.render.legacy.*;
import com.github.mmc1234.lol.render.Camera;
import com.google.common.base.*;
import com.google.inject.*;
import jdk.incubator.foreign.*;
import org.apache.commons.io.*;
import org.joml.*;
import org.lwjgl.opengl.*;
import org.lwjgl.stb.*;

import java.io.*;
import java.lang.Math;
import java.util.*;
import java.util.concurrent.atomic.*;

import static com.github.mmc1234.lol.glfw.GLFW.*;

/**
 * 简单的资源使用演示，渲染了DebugFont
 * */
public class Demo1_6 {
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
    DebugFont debugFont;

    static final String SHADERS_PATH = "shaders/";

    public void init() {
        this.quadMesh = Mesh.create(this.defaultVertexAttribDescriptionList);
        this.quadMesh.setData(0, MemorySegmentUtil.createFromFloatArray(-1, 1, -1,  -1, -1, -1,  1, -1, -1,  1, 1, -1));
        this.quadMesh.setData(1, MemorySegmentUtil.createFromFloatArray(0, 0, 0, 1, 1, 1, 1, 0));
        this.quadMesh.setIndices(MemorySegmentUtil.createFromIntArray(0, 1, 3, 3, 1, 2));

        this.coordMesh = Mesh.create(this.defaultVertexAttribDescriptionList);
        this.coordMesh.setData(0, MemorySegmentUtil.createFromFloatArray(
                -1,-1,-1,  100,-1,-1,
                -1,-1,-1, -1, 100,-1,
                -1,-1,-1, -1,-1,100));
        this.coordMesh.setData(1, MemorySegmentUtil.createFromFloatArray(0, 0, 0, 0,
                0.5f, 0, 0.5f, 0,
                0.9f, 0, 0.9f, 0));
        this.coordMesh.setIndices(MemorySegmentUtil.createFromIntArray(0, 1, 2, 3, 4, 5));

        this.cam.setFov((float) Math.toRadians(60));
        this.cam.setAspect(4/3f);
        this.cam.setZNear(0.01f);
        this.cam.setZFar(1000);

        this.quadVertexBuffer = VertexBuffer.create(this.defaultVertexAttribDescriptionList);
        this.coordVertexBuffer = VertexBuffer.create(this.defaultVertexAttribDescriptionList);

        this.mainProgram = ShaderProgram.newInstance(
                ResourceUtil.loadModuleText(Demo1_6.SHADERS_PATH + "simple_cam_vertex.glsl"),
                ResourceUtil.loadModuleText(Demo1_6.SHADERS_PATH + "simple_uv_fragment.glsl"));
        RenderThread.launch();
        Render.recordRenderCall(this::renderStart);
    }

    private final Vector2d lastMousePos = new Vector2d(-1, -1);

    private final Vector2d mousePos = new Vector2d();

    private final Vector2d mouseDelta = new Vector2d();

    private boolean isHover, applyMouseDeltaToCamera;

    public void hovered(final boolean isHover) {
        this.isHover = isHover;
    }
    public void cursorPos(final double x, final double y) {
        this.mousePos.x = x;
        this.mousePos.y = y;
    }
    public void renderStart() {
        Preconditions.checkState(Render.isRenderThread());
        glfwWindowHint( GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);
        this.window = glfwCreateWindow(400, 320, "DEMO", Monitor.ofLong(0), Window.ofLong(0));
        glfwMakeContextCurrent(this.window);
        glfwSwapInterval(0);
        GL.createCapabilities();
        glfwSetCursorPosCallback(this.window, (w, x, y)-> this.cursorPos(x,y));
        glfwSetCursorEnterCallback(this.window, (w, h)-> this.hovered(h));

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(KHRDebug.GL_DEBUG_OUTPUT);

        KHRDebug.glDebugMessageCallback((src, type, id, severity, len, msg, data) -> {
            System.out.printf("[Src=%s, Type=%s, Id=%d, Severity=%s]\n  %s\n",
                    DebugUtil.getSourceDescription(src),
                    DebugUtil.getTypeDescription(type),id,DebugUtil.getSeverityDescription(severity), GLDebugMessageCallback.getMessage(len, msg));
        }, 0);

        this.debugFont = new DebugFont("simhei.ttf");
        this.debugFont.init();

        final Image img = Sugars.noCatch(()-> this.loadImage(Sugars.noCatch(()->ResourceUtil.getModuleResourceAsStream("textures/texture.png"))));
        this.myTexture = new Texture2D(TextureFormat.R8G8B8A8_UINT, img.w, img.h);
        this.myTexture.init();
        this.myTexture.reload(img.pixels);
        Texture2D.bindZero();

        this.mainProgram.init();
        this.mainProgram.createUniform("texture_sampler");
        this.mainProgram.createUniform("projection_matrix");
        this.mainProgram.createUniform("model_view_matrix");
        this.mainProgram.use();
        GL20.glUniform1i(this.mainProgram.getUniformLocation("texture_sampler"), 0);

        this.quadVertexBuffer.getVao().initAndBind();

        var posVbo = this.quadVertexBuffer.getVbo(0);
        posVbo.init();
        posVbo.reload(this.quadMesh);

        var uvVbo = this.quadVertexBuffer.getVbo(1);
        uvVbo.init();
        uvVbo.reload(this.quadMesh);

        this.coordVertexBuffer.getVao().initAndBind();

        posVbo = this.coordVertexBuffer.getVbo(0);
        posVbo.init();
        posVbo.reload(this.coordMesh);

        uvVbo = this.coordVertexBuffer.getVbo(1);
        uvVbo.init();
        uvVbo.reload(this.coordMesh);


        Vbo.bindZero();
        Vao.bindZero();
        ShaderProgram.useZero();
    }

    public void input() {
        double deltaTime = 0;
        if(this.lastInputTime == -1) {
            this.lastInputTime = glfwGetTime();
        } else {
            final var time = glfwGetTime();
            deltaTime = time- this.lastInputTime;
            this.lastInputTime = time;
        }

        // mouse delta
        this.mouseDelta.x = 0;
        this.mouseDelta.y = 0;
        if (this.lastMousePos.x > 0 && this.lastMousePos.y > 0 && this.isHover) {
            final double deltax = this.mousePos.x - this.lastMousePos.x;
            final double deltay = this.mousePos.y - this.lastMousePos.y;
            final boolean rotateX = deltax != 0;
            final boolean rotateY = deltay != 0;
            if (rotateX) {
                this.mouseDelta.y = (float) deltax;
            }
            if (rotateY) {
                this.mouseDelta.x = (float) deltay;
            }
        }
        this.lastMousePos.x = this.mousePos.x;
        this.lastMousePos.y = this.mousePos.y;

        final Vector3f offset = new Vector3f();

        if(this.notReleaseKey(GLFW_KEY_A)) {
            offset.x-=(float)(deltaTime*3);
        }
        if(this.notReleaseKey(GLFW_KEY_D)) {
            offset.x+=(float)(deltaTime*3);
        }

        if(this.notReleaseKey(GLFW_KEY_W)) {
            offset.z-=(float)(deltaTime*3);
        }
        if(this.notReleaseKey(GLFW_KEY_S)) {
            offset.z+=(float)(deltaTime*3);
        }

        if(this.notReleaseKey(GLFW_KEY_Q)) {
            offset.y+=(float)(deltaTime*3);
        }
        if(this.notReleaseKey(GLFW_KEY_E)) {
            offset.y-=(float)(deltaTime*3);
        }
        if(this.notReleaseKey(GLFW_KEY_ESCAPE)) {
            this.shouldExit.set(true);
        }

        this.cam.transform.movePosition(offset.x, offset.y, offset.z);

        if(glfwGetMouseButton(this.window, 1) != GLFW_RELEASE) {
            this.cam.transform.moveRotation((float) this.mouseDelta.x*0.5f, (float) this.mouseDelta.y*0.5f, 0);
        }
    }

    public boolean isReleaseKey(final int key) {
        return glfwGetKey(this.window, key) == GLFW_RELEASE;
    }
    public boolean notReleaseKey(final int key) {
        return glfwGetKey(this.window, key) != GLFW_RELEASE;
    }


    public void render() {
        this.input();
        Preconditions.checkState(Render.isRenderThread());
        if(this.window.address() != 0) {
            if(glfwWindowShouldClose(this.window)) {
                this.shouldExit.set(true);
            }
            GL11.glClearColor(0.25f, 0.25f, 0.25f, 1);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            this.mainProgram.use();
            this.cam.updateProjection();
            this.cam.updateViewMatrix();
            this.cam.buildModelViewMatrix(new Transform(), this.cam.getViewMatrix());
            GL20.glUniformMatrix4fv(this.mainProgram.getUniformLocation("projection_matrix"),
                    false, this.cam.getProjectionMatrix().get(new float[16]));
            GL20.glUniformMatrix4fv(this.mainProgram.getUniformLocation("model_view_matrix"),
                    false, this.cam.getModelViewMatrix().get(new float[16]));
            this.quadVertexBuffer.getVao().bind();
            GL13.glActiveTexture(GL13.GL_TEXTURE0);

            //myTexture.bind();
            this.debugFont.getFontTexture().bind();
            GL11.nglDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_INT, this.quadMesh.getIndices().address().toRawLongValue());

            this.myTexture.bind();
            GL11.glLineWidth(8);
            this.coordVertexBuffer.getVao().bind();
            GL11.nglDrawElements(GL11.GL_LINES, 6, GL11.GL_UNSIGNED_INT, this.coordMesh.getIndices().address().toRawLongValue());
            GL11.glLineWidth(1);
            Vao.bindZero();
            Texture2D.bindZero();
            ShaderProgram.useZero();
            glfwSwapBuffers(this.window);
        }
    }

    public void renderStop() {
        this.quadMesh.close();
        this.quadVertexBuffer.close();
        this.mainProgram.close();
        this.myTexture.close();
        this.debugFont.close();
        Preconditions.checkState(Render.isRenderThread());
        if(this.window != Window.EMPTY) {
            glfwHideWindow(this.window);
            glfwDestroyWindow(this.window);
            this.window = Window.ofLong(0);
            RenderThread.makeShouldStop();
        }
    }
    public void recordRender() {
        Render.recordRenderCall(this::render);
    }
    public void close() {
        Render.recordRenderCall(()-> this.renderStop());
    }
    public void loop() {
        while(!this.shouldExit.get()) {
            if(!RenderThread.isThreadAlive()) {
                this.shouldExit.set(true);
                return;
            }
            this.renderTimer.tryRun();
            Sugars.noCatchRunnable(()->Thread.sleep(1));
        }
    }

    public Demo1_6() {
        this.init();
        this.loop();
        this.close();
        System.out.println("Stop Main");
    }

    static record Image(int w, int h, MemorySegment pixels) {
    }
    public Image loadImage(final InputStream inputStream) throws IOException {
        final var rawData = MemorySegmentUtil.createFromByteArray(IOUtils.toByteArray(inputStream));
        final var out = MemorySegmentUtil.createFromIntArray(0,0,0);
        final long data=rawData.address().toRawLongValue();
        final int dataSize= (int) rawData.byteSize();
        final long w = out.address().toRawLongValue();
        final long h = out.address().toRawLongValue()+MemoryLayouts.JAVA_INT.byteSize();
        final long c = out.address().toRawLongValue()+(MemoryLayouts.JAVA_INT.byteSize()*2);
        final var pixelsAddress = STBImage.nstbi_load_from_memory(data, dataSize, w,h,c ,4);

        final int imageWidth = MemoryAccess.getIntAtIndex(out, 0);
        final int imageHeight = MemoryAccess.getIntAtIndex(out, 1);
        final var imagePixels = MemoryAddress.ofLong(pixelsAddress).asSegment(imageWidth*imageHeight*4, ResourceScope.globalScope());
        return new Image(imageWidth, imageHeight, imagePixels);
    }

    public static void main(final String[] args) {
        Guice.createInjector(new ImplGLFWModule());
        new Demo1_6();
    }
}
