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

    private RenderThread(String name) {
        setName(name);
        setDaemon(true);
    }

    public static boolean isDead() {
        return !INSTANCE.isAlive();
    }

    public static boolean isExceptionClose() {
        return isDead() && INSTANCE.successStop.get();
    }

    public static boolean isThreadAlive() {
        return INSTANCE != null && INSTANCE.isAlive();
    }

    public static synchronized void launch() {
        if (INSTANCE == null) {
            INSTANCE = new RenderThread("render thread");
            if (Platform.MACOSX.equals(Platform.get())) {
                INSTANCE.run();
            } else {
                INSTANCE.start();
            }
        }
    }

    public static boolean notShouldStop() {
        return !INSTANCE.stopFlag.get();
    }


    public static boolean shouldStop() {
        return INSTANCE.stopFlag.get();
    }

    public static void makeShouldStop() {
        INSTANCE.stopFlag.set(true);
    }

    @Override
    public void run() {
        // Init
        Preconditions.checkState(glfwInit());
        Render.initRenderThread();
        Preconditions.checkState(Render.isRenderThread());

        // Loop
        loop:
        while (notShouldStop()) {
            glfwPollEvents();

            Render.replayQueue();
            glfwWaitEventsTimeout(0.001f);
        }

        // Terminate
        glfwTerminate();
        successStop.set(true);
        // System.out.println("Stop render");
    }
}
