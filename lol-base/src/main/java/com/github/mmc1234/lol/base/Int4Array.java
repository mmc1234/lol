package com.github.mmc1234.lol.base;

import com.google.common.base.*;
import jdk.incubator.foreign.*;

public class Int4Array {
    private MemorySegment segment;
    private int size;
    public int x(int idx) {
        return MemoryAccess.getIntAtIndex(segment, idx*4);
    }
    public int y(int idx) {
        return MemoryAccess.getIntAtIndex(segment, idx*4+1);
    }
    public int z(int idx) {
        return MemoryAccess.getIntAtIndex(segment, idx*4+2);
    }
    public int w(int idx) {
        return MemoryAccess.getIntAtIndex(segment, idx*4+3);
    }
    public void x(int idx, int v) {
        MemoryAccess.setIntAtIndex(segment, idx*4, v);
    }
    public void y(int idx, int v) {
        MemoryAccess.setIntAtIndex(segment, idx*4+1, v);
    }
    public void z(int idx, int v) {
        MemoryAccess.setIntAtIndex(segment, idx*4+2, v);
    }
    public void w(int idx, int v) {
        MemoryAccess.setIntAtIndex(segment, idx*4+3, v);
    }

    public MemorySegment getSegment() {
        return segment;
    }

    public void set(int idx, int x, int y, int z, int w) {
        x(idx, x);
        y(idx, y);
        z(idx, z);
        w(idx, w);
    }

    public int length() {
        return size;
    }
    private Int4Array(MemorySegment segment) {
        Preconditions.checkNotNull(segment);
        this.segment = segment;
        this.size = (int) (segment.byteSize()/MemoryLayouts.JAVA_INT.byteSize()/4);
    }
    public static final Int4Array newInt4Array(MemorySegment segment) {
        return new Int4Array(segment);
    }
    public static final Int4Array newInt4Array(int size) {
        return new Int4Array(MemorySegment.allocateNative(4*MemoryLayouts.JAVA_INT.byteSize()*size, ResourceScope.globalScope()));
    }
}
