package com.github.mmc1234.lol.base;

import com.google.common.base.*;
import jdk.incubator.foreign.*;

public class Float3Array {
    private MemorySegment segment;
    private int size;
    public float x(int idx) {
        return MemoryAccess.getFloatAtIndex(segment, idx*3);
    }
    public float y(int idx) {
        return MemoryAccess.getFloatAtIndex(segment, idx*3+1);
    }
    public float z(int idx) {
        return MemoryAccess.getFloatAtIndex(segment, idx*3+2);
    }
    public void x(int idx, float v) {
        MemoryAccess.setFloatAtIndex(segment, idx*3, v);
    }
    public void y(int idx, float v) {
        MemoryAccess.setFloatAtIndex(segment, idx*3+1, v);
    }
    public void z(int idx, float v) {
        MemoryAccess.setFloatAtIndex(segment, idx*3+2, v);
    }

    public int length() {
        return size;
    }
    private Float3Array(MemorySegment segment) {
        Preconditions.checkNotNull(segment);
        this.segment = segment;
        this.size = (int) (segment.byteSize()/MemoryLayouts.JAVA_FLOAT.byteSize()/3);
    }
    public static final Float3Array newFloat3Array(MemorySegment segment) {
        return new Float3Array(segment);
    }
    public static final Float3Array newFloat3Array(int size) {
        return new Float3Array(MemorySegment.allocateNative(3*MemoryLayouts.JAVA_FLOAT.byteSize()*size, ResourceScope.globalScope()));
    }
}
