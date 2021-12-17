package com.github.mmc1234.lol.renderbranch.v1;

import com.google.common.base.*;
import com.google.common.collect.*;

import java.util.*;

public class Render {
    private static volatile boolean isReplayingQueue;
    private static final Queue<RenderCall> recordingQueue = Queues.newConcurrentLinkedQueue();
    private static Thread renderThread;

    public static void initRenderThread() {
        Preconditions.checkState(renderThread == null, "Could not initialize render thread");
        renderThread = Thread.currentThread();
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
            RenderCall renderCall = (RenderCall) recordingQueue.poll();
            renderCall.execute();
        }

        isReplayingQueue = false;
    }
}
