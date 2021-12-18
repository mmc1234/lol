package com.github.mmc1234.lol.renderbranch.v1_1.demo;

import com.github.mmc1234.lol.base.*;
import com.github.mmc1234.lol.glfw.*;
import com.github.mmc1234.lol.glfw.impl.*;
import com.github.mmc1234.lol.renderbranch.v1.*;
import com.github.mmc1234.lol.renderbranch.v1_1.*;
import com.google.common.base.*;
import com.google.inject.*;
import org.lwjgl.opengl.*;

import java.util.concurrent.atomic.*;

import static com.github.mmc1234.lol.glfw.GLFW.*;

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
        window = glfwCreateWindow(400, 320, "DEMO", Monitor.ofLong(0), Window.ofLong(0));
        glfwMakeContextCurrent(window);
        glfwSwapInterval(0);
        GL.createCapabilities();
    }
    public void renderStop() {
        Preconditions.checkState(Render.isRenderThread());
        if(window != Window.EMPTY) {
            glfwHideWindow(window);
            glfwDestroyWindow(window);
            window = Window.ofLong(0);
        }
        RenderThread.makeShouldStop();
    }

    public void render() {
        Preconditions.checkState(Render.isRenderThread());
        shouldExit.set(glfwWindowShouldClose(window));
        if(window.address() != 0) {
            GL33.glClearColor(0.25f,0.25f, 0.25f, 1);
            glfwSwapBuffers(window);
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

    public Demo1_2() {
        init();
        loop();
        close();
    }

    public static void main(String[] args) {
        Guice.createInjector(new ImplGLFWModule());
        new Demo1_2();
    }
}
