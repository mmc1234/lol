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

import com.google.common.base.*;
import com.google.common.collect.*;

import java.util.*;

public class Render {
    private static volatile boolean isReplayingQueue;
    private static final Queue<RenderCall> recordingQueue = Queues.newConcurrentLinkedQueue();
    private static volatile Thread renderThread;
    private static boolean isStop;

    public static void initRenderThread() {
        Preconditions.checkState(Render.renderThread == null, "Could not initialize render thread");
        Render.renderThread = Thread.currentThread();
        Render.isStop = false;
    }

    public static void assertRenderThread() {
        if(!Render.isRenderThread()) throw Render.constructThreadException();
    }

    private static IllegalStateException constructThreadException() {
        return new IllegalStateException("Render called from wrong thread");
    }

    public static void recordRenderCall(final RenderCall renderCall) {
        Render.recordingQueue.offer(renderCall);
    }

    public static boolean isRenderThread() {
        return Thread.currentThread() == Render.renderThread;
    }

    public static boolean isEmptyRecordingQueue() {
        return Render.recordingQueue.isEmpty();
    }

    public static void replayQueue() {
        Render.assertRenderThread();
        Render.isReplayingQueue = true;

        while (!Render.recordingQueue.isEmpty() && !Render.isStop) {
            final RenderCall renderCall = Render.recordingQueue.poll();
            renderCall.execute();
        }

        Render.isReplayingQueue = false;
    }

    public static void makeShouldStop() {
        Render.assertRenderThread();
        isStop = true;
    }
}
