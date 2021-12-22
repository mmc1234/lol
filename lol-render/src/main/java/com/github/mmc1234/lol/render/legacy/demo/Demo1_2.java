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

import com.github.mmc1234.lol.base.*;
import com.github.mmc1234.lol.glfw.*;
import com.github.mmc1234.lol.glfw.impl.*;
import com.github.mmc1234.lol.render.*;
import com.google.common.base.*;
import com.google.inject.*;
import org.lwjgl.opengl.*;

import java.util.concurrent.atomic.*;

import static com.github.mmc1234.lol.glfw.GLFW.*;

/**
 * 将代码移动到独立方法
 * */
public class Demo1_2 {
    Window window = Window.ofLong(0);
    final AtomicBoolean shouldExit = new AtomicBoolean();
    final Timer renderTimer = Timer.newSystem(this::recordRender, 1000/60, true);

    public void init() {
        RenderThread.launch();
        Render.recordRenderCall(this::renderStart);
    }

    public void renderStart() {
        Preconditions.checkState(Render.isRenderThread());
        this.window = glfwCreateWindow(400, 320, "DEMO", Monitor.ofLong(0), Window.ofLong(0));
        glfwMakeContextCurrent(this.window);
        glfwSwapInterval(0);
        GL.createCapabilities();
    }
    public void render() {
        Preconditions.checkState(Render.isRenderThread());
        this.shouldExit.set(glfwWindowShouldClose(this.window));
        if(this.window.address() != 0) {
            GL11.glClearColor(0.25f, 0.25f, 0.25f, 1);
            glfwSwapBuffers(this.window);
        }
    }
    public void renderStop() {
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

    public Demo1_2() {
        this.init();
        this.loop();
        this.close();
    }

    public static void main(final String[] args) {
        Guice.createInjector(new ImplGLFWModule());
        new Demo1_2();
    }
}
