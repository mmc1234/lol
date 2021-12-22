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

package com.github.mmc1234.lol.render;

import com.github.mmc1234.lol.base.*;
import com.google.common.base.*;

import java.util.concurrent.atomic.*;

import static com.github.mmc1234.lol.glfw.GLFW.*;

public final class RenderThread extends Thread {
    protected static volatile RenderThread INSTANCE;
    protected final AtomicBoolean stopFlag = new AtomicBoolean();
    protected final AtomicBoolean successStop = new AtomicBoolean();

    private RenderThread(final String name) {
        this.setName(name);
        this.setDaemon(true);
    }

    public static boolean isDead() {
        return !RenderThread.INSTANCE.isAlive();
    }

    public static boolean isExceptionClose() {
        return RenderThread.isDead() && RenderThread.INSTANCE.successStop.get();
    }

    public static boolean isThreadAlive() {
        return RenderThread.INSTANCE != null && RenderThread.INSTANCE.isAlive();
    }

    public static synchronized void launch() {
        if (RenderThread.INSTANCE == null) {
            RenderThread.INSTANCE = new RenderThread("render thread");
            if (Platform.MACOSX.equals(Platform.get())) {
                RenderThread.INSTANCE.run();
            } else {
                RenderThread.INSTANCE.start();
            }
        }
    }

    public static boolean notShouldStop() {
        return !RenderThread.INSTANCE.stopFlag.get();
    }


    public static boolean shouldStop() {
        return RenderThread.INSTANCE.stopFlag.get();
    }

    public static void makeShouldStop() {
        RenderThread.INSTANCE.stopFlag.set(true);
    }

    @Override
    public void run() {
        // Init
        Preconditions.checkState(glfwInit());
        Render.initRenderThread();
        Preconditions.checkState(Render.isRenderThread());

        // Loop
        loop:while (RenderThread.notShouldStop()) {
            glfwPollEvents();

            Render.replayQueue();
            glfwWaitEventsTimeout(0.001f);
        }

        // Terminate
        glfwTerminate();
        this.successStop.set(true);
        // System.out.println("Stop render");
    }
}
