package com.github.mmc1234.lol.base;

import com.google.common.base.*;
import jdk.incubator.foreign.*;

public class Float2Array {
    private MemorySegment segment;
    private int size;
    public float x(int idx) {
        return MemoryAccess.getFloatAtIndex(segment, idx*2);
    }
    public float y(int idx) {
        return MemoryAccess.getFloatAtIndex(segment, idx*2+1);
    }

    public int length() {
        return size;
    }

    public void x(int idx, float v) {
        MemoryAccess.setFloatAtIndex(segment, idx*2, v);
    }
    public void y(int idx, float v) {
        MemoryAccess.setFloatAtIndex(segment, idx*2+1, v);
    }
    private Float2Array(MemorySegment segment) {
        Preconditions.checkNotNull(segment);
        this.segment = segment;
        this.size = (int) (segment.byteSize()/MemoryLayouts.JAVA_FLOAT.byteSize()/2);
    }
    public static final Float2Array newFloat2Array(MemorySegment segment) {
        return new Float2Array(segment);
    }
    public static final Float2Array newFloat2Array(int size) {
        return new Float2Array(MemorySegment.allocateNative(2*MemoryLayouts.JAVA_FLOAT.byteSize()*size, ResourceScope.globalScope()));
    }
}
