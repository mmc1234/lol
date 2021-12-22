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

package com.github.mmc1234.lol.render.current.demo;

import com.github.mmc1234.lol.base.*;
import com.github.mmc1234.lol.glfw.*;
import com.github.mmc1234.lol.glfw.impl.*;
import com.github.mmc1234.lol.render.*;
import com.google.inject.*;
import org.lwjgl.opengl.*;

import java.util.concurrent.atomic.*;

import static com.github.mmc1234.lol.glfw.GLFW.*;
import static com.github.mmc1234.lol.glfw.GLFW.glfwSwapInterval;

@Deprecated
public abstract class DemoBase {
    static {
        Guice.createInjector(new ImplGLFWModule());
    }

    protected AtomicBoolean shouldBreakLogicLoop = new AtomicBoolean();

    protected Window window;

    protected float fps = 60f;

    protected Timer renderTimer = Timer.newSystem(()->{
        Render.assertRenderThread();
        this.renderUpdate();
    }, (long) (1000/ this.fps), true);

    public void initMainWindow() {
        Render.assertRenderThread();
        if(this.window == null) {
            glfwDefaultWindowHints();
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
        }
    }

    public void tryMakeShouldStop() {
        Render.assertRenderThread();
        if(this.window.address() == 0 || glfwWindowShouldClose(this.window)) {
            RenderThread.makeShouldStop();
        }
    }

    public boolean stopIfWindowShouldClose() {
        this.tryMakeShouldStop();
        if(RenderThread.shouldStop()) {
            this.renderStop();
            return true;
        }
        return false;
    }

    public void killMainWindow() {
        Render.assertRenderThread();
        if(this.window != null) {
            glfwDestroyWindow(this.window);
            this.window = Window.ofLong(0);
        }
    }

    public abstract void logicInit();
    public abstract void logicUpdate();
    public abstract void logicStop();
    public abstract void renderInit();
    public abstract void renderUpdate();
    public abstract void renderStop();

    public void clear(final Camera camIn) {
        Render.assertRenderThread();
        camIn.getFrameBuffer().bind();
        GL11.glClearColor(
                camIn.getBackground().red(),
                camIn.getBackground().green(),
                camIn.getBackground().blue(),
                camIn.getBackground().alpha());
        GL11.glClear(camIn.getClearMask());
    }

    public DemoBase() {
        this.logicInit();
        RenderThread.launch();
        Render.recordRenderCall(()-> this.renderInit());
        boolean stop = false;
        while(!this.shouldBreakLogicLoop.get()) { // not should close
            if(!stop && RenderThread.shouldStop() && RenderThread.isThreadAlive()) {
                Render.recordRenderCall(()-> this.renderStop());
                stop = true;
            }
            this.logicUpdate();
            Sugars.noCatchRunnable(()->Thread.sleep(1));
        }
        this.logicStop();
    }
}
