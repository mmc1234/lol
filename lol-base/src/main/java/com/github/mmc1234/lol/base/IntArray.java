package com.github.mmc1234.lol.base;

import com.google.common.base.*;
import jdk.incubator.foreign.*;

public class IntArray {
    private MemorySegment segment;
    private int size;
    public float x(int idx) {
        return MemoryAccess.getFloatAtIndex(segment, idx);
    }
    public int length() {
        return size;
    }
    public void x(int idx, float v) {
        MemoryAccess.setFloatAtIndex(segment, idx, v);
    }

    public MemorySegment getSegment() {
        return segment;
    }

    private IntArray(MemorySegment segment) {
        Preconditions.checkNotNull(segment);
        this.segment = segment;
        this.size = (int) (segment.byteSize()/MemoryLayouts.JAVA_FLOAT.byteSize());
    }
    public static final IntArray newIntArray(MemorySegment segment) {
        return new IntArray(segment);
    }
    public static final IntArray newIntArray(int size) {
        return new IntArray(MemorySegment.allocateNative(MemoryLayouts.JAVA_FLOAT.byteSize()*size, ResourceScope.globalScope()));
    }
}
