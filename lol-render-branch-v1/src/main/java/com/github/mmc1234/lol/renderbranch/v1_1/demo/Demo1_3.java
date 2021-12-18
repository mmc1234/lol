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

/**
 * 如果不成对调用init和terminate，那么destroyWindow不会出现问题
 * */
public class Demo1_3 {
    Window window = Window.ofLong(0);
    final AtomicBoolean shouldExit = new AtomicBoolean();
    final Timer renderTimer = Timer.newSystem(this::recordRender, 1000/60, true);

    public void init() {
        glfwInit();
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
        //if(window != Window.EMPTY) {
            glfwHideWindow(window);
            glfwDestroyWindow(window); // 错误源头在这里
            window = Window.ofLong(0);
        //}
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
        // bad call
        glfwTerminate();
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

    public Demo1_3() {
        init();
        loop();
        close();
    }

    public static void main(String[] args) {
        Guice.createInjector(new ImplGLFWModule());
        new Demo1_3();
    }
}
