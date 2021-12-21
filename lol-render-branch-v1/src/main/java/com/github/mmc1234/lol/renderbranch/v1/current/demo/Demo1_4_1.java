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

package com.github.mmc1234.lol.renderbranch.v1.current.demo;

import com.github.mmc1234.lol.base.Timer;
import com.github.mmc1234.lol.base.*;
import com.github.mmc1234.lol.glfw.*;
import com.github.mmc1234.lol.glfw.impl.*;
import com.github.mmc1234.lol.renderbranch.v1.*;
import com.github.mmc1234.lol.renderbranch.v1.current.*;
import com.google.common.base.*;
import com.google.inject.*;
import jdk.incubator.foreign.*;
import org.apache.commons.io.*;
import org.lwjgl.opengl.*;
import org.lwjgl.stb.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.*;

import static com.github.mmc1234.lol.glfw.GLFW.*;
import static org.lwjgl.stb.STBImage.stbi_load;

/**
 * 简单的资源使用演示，渲染了一个纹理
 * */
public class Demo1_4_1 {
    Window window = Window.ofLong(0);
    final AtomicBoolean shouldExit = new AtomicBoolean();
    final Timer renderTimer = Timer.newSystem(this::recordRender, 1000/60, true);
    Mesh quadMesh;
    VertexBuffer quadVertexBuffer;
    List<VertexAttribDescription> defaultVertexAttribDescriptionList = VertexAttribDescription.list(
            TypeFormat.FLOAT32, 3,
            TypeFormat.FLOAT32, 2);
    ShaderProgram mainProgram;
    Texture2D myTexture;

    static final String SHADERS_PATH = "shaders/";

    public void init() {
        quadMesh = Mesh.create(defaultVertexAttribDescriptionList);
        quadMesh.setData(0, MemorySegmentUtil.createFromFloatArray(-1, 1, -1,  -1, -1, -1,  1, -1, -1,  1, 1, -1));
        quadMesh.setData(1, MemorySegmentUtil.createFromFloatArray(0, 0, 0, 1, 1, 1, 1, 0));
        quadMesh.setIndices(MemorySegmentUtil.createFromIntArray(0, 1, 3, 3, 1, 2));
        quadVertexBuffer = VertexBuffer.create(defaultVertexAttribDescriptionList);

        mainProgram = ShaderProgram.newInstance(
                ResourceUtil.loadModuleText(SHADERS_PATH+ "simple_uv_vertex.glsl"),
                ResourceUtil.loadModuleText(SHADERS_PATH+ "simple_uv_fragment.glsl"));

        RenderThread.launch();
        Render.recordRenderCall(this::renderStart);
    }

    public void renderStart() {
        Preconditions.checkState(Render.isRenderThread());
        glfwWindowHint( GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);
        window = glfwCreateWindow(400, 320, "DEMO", Monitor.ofLong(0), Window.ofLong(0));
        glfwMakeContextCurrent(window);
        glfwSwapInterval(0);
        GL.createCapabilities();

        GL33.glEnable(KHRDebug.GL_DEBUG_OUTPUT);
        KHRDebug.glDebugMessageCallback((src, type, id, severity, len, msg, data) -> {
            System.out.printf("[Src=%s, Type=%s, Id=%d, Severity=%s]\n  %s\n",
                    DebugUtil.getSourceDescription(src),
                    DebugUtil.getTypeDescription(type),id,DebugUtil.getSeverityDescription(severity), GLDebugMessageCallback.getMessage(len, msg));
        }, 0);

        Image img = Sugars.noCatch(()->loadImage(Sugars.noCatch(()->ResourceUtil.getModuleResourceAsStream("textures/texture.png"))));

        var pixels = MemorySegmentUtil.createFromIntArray(
                0b01111111, 0b01111111, 0b01111110, 0b01111100,
                0b01111111, 0b01110111, 0b01101111, 0b01111100,
                0b01110111, 0b01111111, 0b01101111, 0b01111110,
                0b01111111, 0b00111011, 0b01111111, 0b01111111);

        myTexture = new Texture2D(TextureFormat.R8G8B8A8_UINT, img.w, img.h);
        myTexture.init();

        myTexture.reload(img.pixels);
        //myTexture.reload(pixels);
        Texture2D.bindZero();

        mainProgram.init();
        mainProgram.createUniform("texture_sampler");
        mainProgram.use();
        GL33.glUniform1i(mainProgram.getUniformLocation("texture_sampler"), 0);

        quadVertexBuffer.getVao().initAndBind();

        var posVbo = quadVertexBuffer.getVbo(0);
        posVbo.init();
        posVbo.reload(quadMesh);

        var uvVbo = quadVertexBuffer.getVbo(1);
        uvVbo.init();
        uvVbo.reload(quadMesh);



        Vbo.bindZero();
        Vao.bindZero();
        ShaderProgram.useZero();
    }

    public void render() {
        Preconditions.checkState(Render.isRenderThread());
        shouldExit.set(glfwWindowShouldClose(window));
        if(window.address() != 0) {
            GL33.glClearColor(0.25f,0.25f, 0.25f, 1);
            GL33.glClear(GL33.GL_COLOR_BUFFER_BIT);
            mainProgram.use();
            quadVertexBuffer.getVao().bind();
            GL33.glActiveTexture(GL33.GL_TEXTURE0);
            myTexture.bind();
            GL33.nglDrawElements(GL33.GL_TRIANGLES, 6, GL33.GL_UNSIGNED_INT, quadMesh.getIndices().address().toRawLongValue());
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
        }
        RenderThread.makeShouldStop();
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

    public Demo1_4_1() {
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
        new Demo1_4_1();
    }
}
