package com.github.mmc1234.lol.base;

import com.google.common.base.*;
import jdk.incubator.foreign.*;

public class Float4Array {
    private MemorySegment segment;
    private int size;
    public float x(int idx) {
        return MemoryAccess.getFloatAtIndex(segment, idx*4);
    }
    public float y(int idx) {
        return MemoryAccess.getFloatAtIndex(segment, idx*4+1);
    }
    public float z(int idx) {
        return MemoryAccess.getFloatAtIndex(segment, idx*4+2);
    }
    public float w(int idx) {
        return MemoryAccess.getFloatAtIndex(segment, idx*4+3);
    }
    public void x(int idx, float v) {
        MemoryAccess.setFloatAtIndex(segment, idx*4, v);
    }
    public void y(int idx, float v) {
        MemoryAccess.setFloatAtIndex(segment, idx*4+1, v);
    }
    public void z(int idx, float v) {
        MemoryAccess.setFloatAtIndex(segment, idx*4+2, v);
    }
    public void w(int idx, float v) {
        MemoryAccess.setFloatAtIndex(segment, idx*4+3, v);
    }

    public MemorySegment getSegment() {
        return segment;
    }

    public void set(int idx, float x, float y, float z, float w) {
        x(idx, x);
        y(idx, y);
        z(idx, z);
        w(idx, w);
    }

    public int length() {
        return size;
    }
    private Float4Array(MemorySegment segment) {
        Preconditions.checkNotNull(segment);
        this.segment = segment;
        this.size = (int) (segment.byteSize()/MemoryLayouts.JAVA_FLOAT.byteSize()/4);
    }
    public static final Float4Array newFloat4Array(MemorySegment segment) {
        return new Float4Array(segment);
    }
    public static final Float4Array newFloat4Array(int size) {
        return new Float4Array(MemorySegment.allocateNative(4*MemoryLayouts.JAVA_FLOAT.byteSize()*size, ResourceScope.globalScope()));
    }
}
