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

package com.github.mmc1234.lol.renderbranch.v1;

import com.google.common.base.*;
import com.google.common.collect.*;

import java.util.*;

public class Render {
    private static volatile boolean isReplayingQueue;
    private static final Queue<RenderCall> recordingQueue = Queues.newConcurrentLinkedQueue();
    private static volatile Thread renderThread;

    public static void initRenderThread() {
        Preconditions.checkState(renderThread == null, "Could not initialize render thread");
        renderThread = Thread.currentThread();
    }

    public static void assertRenderThread() {
        if(!isRenderThread()) throw constructThreadException();
    }

    private static IllegalStateException constructThreadException() {
        return new IllegalStateException("Render called from wrong thread");
    }

    public static void recordRenderCall(RenderCall renderCall) {
        recordingQueue.offer(renderCall);
    }

    public static boolean isRenderThread() {
        return Thread.currentThread() == renderThread;
    }

    public static boolean isEmptyRecordingQueue() {
        return recordingQueue.isEmpty();
    }

    public static void replayQueue() {
        isReplayingQueue = true;

        while (!recordingQueue.isEmpty()) {
            RenderCall renderCall = recordingQueue.poll();
            renderCall.execute();
        }

        isReplayingQueue = false;
    }
}
