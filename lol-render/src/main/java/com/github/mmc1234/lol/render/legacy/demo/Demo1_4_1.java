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
    List<VertexAttrib> defaultVertexAttribDescriptionList = VertexAttrib.list(
            TypeFormat.FLOAT32, 3,
            TypeFormat.FLOAT32, 2);
    ShaderProgram mainProgram;
    Texture2D myTexture;

    static final String SHADERS_PATH = "shaders/";

    public void init() {
        this.quadMesh = Mesh.create(this.defaultVertexAttribDescriptionList);
        this.quadMesh.setData(0, MemorySegmentUtil.createFromFloatArray(-1, 1, -1,  -1, -1, -1,  1, -1, -1,  1, 1, -1));
        this.quadMesh.setData(1, MemorySegmentUtil.createFromFloatArray(0, 0, 0, 1, 1, 1, 1, 0));
        this.quadMesh.setIndices(MemorySegmentUtil.createFromIntArray(0, 1, 3, 3, 1, 2));
        this.quadVertexBuffer = VertexBuffer.create(this.defaultVertexAttribDescriptionList);

        this.mainProgram = ShaderProgram.newInstance(
                ResourceUtil.loadModuleText(Demo1_4_1.SHADERS_PATH + "simple_uv_vertex.glsl"),
                ResourceUtil.loadModuleText(Demo1_4_1.SHADERS_PATH + "simple_uv_fragment.glsl"));

        RenderThread.launch();
        Render.recordRenderCall(this::renderStart);
    }

    public void renderStart() {
        Preconditions.checkState(Render.isRenderThread());
        glfwWindowHint( GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);
        this.window = glfwCreateWindow(400, 320, "DEMO", Monitor.ofLong(0), Window.ofLong(0));
        glfwMakeContextCurrent(this.window);
        glfwSwapInterval(0);
        GL.createCapabilities();

        GL11.glEnable(KHRDebug.GL_DEBUG_OUTPUT);
        KHRDebug.glDebugMessageCallback((src, type, id, severity, len, msg, data) -> {
            System.out.printf("[Src=%s, Type=%s, Id=%d, Severity=%s]\n  %s\n",
                    DebugUtil.getSourceDescription(src),
                    DebugUtil.getTypeDescription(type),id,DebugUtil.getSeverityDescription(severity), GLDebugMessageCallback.getMessage(len, msg));
        }, 0);

        final Image img = Sugars.noCatch(()-> this.loadImage(Sugars.noCatch(()->ResourceUtil.getModuleResourceAsStream("textures/texture.png"))));

        final var pixels = MemorySegmentUtil.createFromIntArray(
                0b01111111, 0b01111111, 0b01111110, 0b01111100,
                0b01111111, 0b01110111, 0b01101111, 0b01111100,
                0b01110111, 0b01111111, 0b01101111, 0b01111110,
                0b01111111, 0b00111011, 0b01111111, 0b01111111);

        this.myTexture = new Texture2D(TextureFormat.R8G8B8A8_UINT, img.w, img.h);
        this.myTexture.init();

        this.myTexture.reload(img.pixels);
        //myTexture.reload(pixels);
        Texture2D.bindZero();

        this.mainProgram.init();
        this.mainProgram.createUniform("texture_sampler");
        this.mainProgram.use();
        GL20.glUniform1i(this.mainProgram.getUniformLocation("texture_sampler"), 0);

        this.quadVertexBuffer.getVao().initAndBind();

        final var posVbo = this.quadVertexBuffer.getVbo(0);
        posVbo.init();
        posVbo.reload(this.quadMesh);

        final var uvVbo = this.quadVertexBuffer.getVbo(1);
        uvVbo.init();
        uvVbo.reload(this.quadMesh);



        Vbo.bindZero();
        Vao.bindZero();
        ShaderProgram.useZero();
    }

    public void render() {
        Preconditions.checkState(Render.isRenderThread());
        this.shouldExit.set(glfwWindowShouldClose(this.window));
        if(this.window.address() != 0) {
            GL11.glClearColor(0.25f, 0.25f, 0.25f, 1);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            this.mainProgram.use();
            this.quadVertexBuffer.getVao().bind();
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            this.myTexture.bind();
            GL11.nglDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_INT, this.quadMesh.getIndices().address().toRawLongValue());
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
        Preconditions.checkState(Render.isRenderThread());
        if(this.window != Window.EMPTY) {
            glfwHideWindow(this.window);
            glfwDestroyWindow(this.window);
            this.window = Window.ofLong(0);
        }
        RenderThread.makeShouldStop();
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

    public Demo1_4_1() {
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
        new Demo1_4_1();
    }
}
