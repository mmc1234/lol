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

package com.github.mmc1234.lol.renderbranch.v1.current;

import com.github.mmc1234.lol.base.*;
import com.github.mmc1234.lol.renderbranch.v1.*;
import com.google.common.base.*;

import java.util.concurrent.atomic.*;

import static com.github.mmc1234.lol.glfw.GLFW.*;

public class RenderThread extends Thread {
    protected final AtomicBoolean stopFlag = new AtomicBoolean();
    protected static volatile RenderThread INSTANCE;

    private RenderThread() {
        super();
        setDaemon(true);
    }

    public static boolean isThreadAlive() {
        return INSTANCE.isAlive();
    }


    protected final AtomicBoolean successStop = new AtomicBoolean();

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
        successStop.set(true);
        System.out.println("Stop render");
    }

    public static boolean isExceptionClose() {
        return isDead() && INSTANCE.successStop.get();
    }

    public static boolean isDead() {
        return !INSTANCE.isAlive();
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
