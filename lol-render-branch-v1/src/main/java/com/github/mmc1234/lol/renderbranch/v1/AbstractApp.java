package com.github.mmc1234.lol.renderbranch.v1;

import com.github.mmc1234.lol.base.Platform;
import com.github.mmc1234.lol.glfw.*;
import com.github.mmc1234.lol.glfw.impl.*;
import com.google.common.base.*;
import com.google.inject.*;

import java.util.concurrent.atomic.*;

import static com.github.mmc1234.lol.glfw.GLFW.*;

public abstract class AbstractApp implements App {
    private double startTime;// logic only
    protected WindowHandler windowHandler;
    public Window getWindow() {
        return windowHandler.getWindow();
    }

    class RenderThread extends Thread {
        public RenderThread() {
            super(() -> {
                Render.initRenderThread();
                windowHandler.init();
                while (!Window.isEmpty(windowHandler.getWindow()) && !GLFW.glfwWindowShouldClose(windowHandler.getWindow())) {
                    onWindow();
                    GLFW.glfwWaitEventsTimeout(0.001f);
                    Render.replayQueue();
                    GLFW.glfwWaitEventsTimeout(0.001f);
                }
                onRenderStop();
            });
            setUncaughtExceptionHandler((t, e) -> {
                e.printStackTrace();
                onRenderStop();
            });
        }

        private void launchRenderThread() {
            if (Platform.get() == Platform.MACOSX) {
                run();
            } else {
                start();
            }
        }
    }
    AtomicBoolean isRendering = new AtomicBoolean();

    private void newFrame() {
        Render.recordRenderCall(() -> {
            isRendering.set(true);
            onBeforeRender();
            onRender();
            onAfterRender();
            glfwSwapBuffers(windowHandler.getWindow());
            isRendering.set(false);
        });
    }

    protected RenderThread renderThread;

    @Override
    public final void run() {
        Guice.createInjector(new ImplGLFWModule());
        Preconditions.checkState(GLFW.glfwInit());
        onLogicStart();
        windowHandler = new WindowHandler();
        startTime = GLFW.glfwGetTime();
        renderThread = new RenderThread();
        renderThread.launchRenderThread();
        Render.recordRenderCall(() -> onRenderStart());
        logicLoop();
        GLFW.glfwTerminate();
        onLogicStop();
    }
    private void logicLoop() {
        while (renderThread.isAlive() && (Window.isEmpty(windowHandler.getWindow()) || !glfwWindowShouldClose(windowHandler.getWindow()))) {
            double currentTime = glfwGetTime();
            Render.recordRenderCall(this::onWindow);
            if ((currentTime - startTime) > (1 / 60f) && !isRendering.get()) {
                startTime = currentTime;
                onLogic();
                newFrame();
            }
            glfwWaitEventsTimeout(0.001f);
        }
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

    @Override
    public void onWindow() {

    }
}
