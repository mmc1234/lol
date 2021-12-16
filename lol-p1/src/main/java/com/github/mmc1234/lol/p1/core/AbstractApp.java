package com.github.mmc1234.lol.p1.core;

import com.github.mmc1234.lol.base.Platform;
import com.github.mmc1234.lol.glfw.*;
import com.github.mmc1234.lol.glfw.impl.*;
import com.github.mmc1234.lol.p1.core.*;
import com.github.mmc1234.lol.p1.render.*;
import com.github.mmc1234.lol.p1.window.*;
import com.google.common.base.*;
import com.google.inject.*;
import org.lwjgl.opengl.*;

import static com.github.mmc1234.lol.glfw.GLFW.*;
import static com.github.mmc1234.lol.glfw.GLFW.glfwWaitEventsTimeout;

public abstract class AbstractApp implements App {
    private double startTime;// logic only
    private WindowHandler windowHandler;

    @Override
    public final void run() {
        onLogicStart();
        Guice.createInjector(new ImplGLFWModule());
        Preconditions.checkState(GLFW.glfwInit());
        windowHandler = new WindowHandler();

        startTime = GLFW.glfwGetTime();

        var rt = new Thread(() -> {
            windowHandler.init();
            GLFW.glfwMakeContextCurrent(windowHandler.getWindow());
            GLFW.glfwSwapInterval(0);
            GL.createCapabilities();
            while (!Window.isEmpty(windowHandler.getWindow()) && !GLFW.glfwWindowShouldClose(windowHandler.getWindow())) {
                GLFW.glfwWaitEventsTimeout(0.001f);
                Render.replayQueue();
                GLFW.glfwWaitEventsTimeout(0.001f);
            }
            onRenderStop();
        });
        rt.setUncaughtExceptionHandler((t, e) -> {
            e.printStackTrace();
            onRenderStop();
        });
        if (Platform.get() == Platform.MACOSX) {
            rt.run();
        } else {
            rt.start();
        }

        Render.recordRenderCall(() -> onRenderStart());
        while (rt.isAlive() && (Window.isEmpty(windowHandler.getWindow()) || !glfwWindowShouldClose(windowHandler.getWindow()))) {
            double currentTime = glfwGetTime();
            if (currentTime - startTime > 1 / 60f && Render.isEmptyRecordingQueue()) {
                startTime = currentTime;
                onLogic();
                Render.recordRenderCall(() -> {
                    GL11.glClearColor(0.25f, 0.25f, 0.25f, 1);
                    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
                    onBeforeRender();
                    onRender();
                    onAfterRender();
                    glfwSwapBuffers(windowHandler.getWindow());
                });
            }
            glfwWaitEventsTimeout(0.001f);
        }

        GLFW.glfwDestroyWindow(windowHandler.getWindow());
        GLFW.glfwTerminate();
        onLogicStop();
    }

    @Override
    public void onLogicStart() {

    }

    @Override
    public void onRenderStart() {

    }

    @Override
    public void onLogicStop() {

    }

    @Override
    public void onRenderStop() {

    }

    @Override
    public void onLogic() {

    }

    @Override
    public void onBeforeRender() {

    }

    @Override
    public void onRender() {

    }

    @Override
    public void onAfterRender() {

    }
}
