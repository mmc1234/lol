package com.github.mmc1234.lol.renderbranch.v1_1;

import com.github.mmc1234.lol.base.*;
import com.github.mmc1234.lol.renderbranch.v1.*;
import com.google.common.base.*;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

import static com.github.mmc1234.lol.glfw.GLFW.*;

public class RenderThread extends Thread {
    protected final AtomicBoolean stopFlag = new AtomicBoolean();
    protected static volatile RenderThread INSTANCE;

    private RenderThread() {
        super();
    }

    public static boolean isThreadAlive() {
        return INSTANCE.isAlive();
    }

    @Override
    public void run() {
        Preconditions.checkState(glfwInit());
        Render.initRenderThread();
        Preconditions.checkState(Render.isRenderThread());
        while(!stopFlag.get()) {
            glfwPollEvents();
            Render.replayQueue();
            glfwWaitEventsTimeout(0.0001f);
        }
        glfwTerminate();
    }

    public static void makeShouldStop() {
        INSTANCE.stopFlag.set(true);
    }

    public static synchronized void launch() {
        if(INSTANCE == null) {
            INSTANCE = new RenderThread();
            INSTANCE.setName("render thread");
            if (Platform.MACOSX.equals(Platform.get())) {
                INSTANCE.run();
            } else {
                INSTANCE.start();
            }
        }
    }
}
