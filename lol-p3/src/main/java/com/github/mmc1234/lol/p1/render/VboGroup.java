package com.github.mmc1234.lol.p1.render;

import com.google.common.base.*;
import com.google.common.collect.*;
import jdk.incubator.foreign.*;
import org.lwjgl.opengl.*;

import java.util.*;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.GL_HALF_FLOAT;

public class VboGroup {
    private List<VertexAttribDescription> descriptionList = Lists.newArrayList();
    private int[] buffers;
    private VboInitializer initializer;
    private VboGroup(VboInitializer initializer, VertexAttribDescription ...descriptions) {
        this.initializer = initializer;
        for(var desc : descriptions) {
            if(desc != null) {
                descriptionList.add(desc);
            }
        }
        buffers = new int[descriptionList.size()];
    }

    public static final VboGroup newInstance(VboInitializer initializer, VertexAttribDescription ...descriptions) {
        return new VboGroup(initializer, descriptions);
    }

    private static int asType(TypeFormat fmt) {
        return switch (fmt) {
            case INT8, SNORM8 -> GL_BYTE;
            case INT16, SNORM16 -> GL_SHORT;
            case INT32, SNORM32 -> GL_INT;
            case UINT8, UNORM8 -> GL_UNSIGNED_BYTE;
            case UINT1_5_5_5_REV, UNORM1_5_5_5_REV -> GL_UNSIGNED_SHORT_1_5_5_5_REV;
            case UINT2_3_3_REV, UNORM2_3_3_REV -> GL_UNSIGNED_BYTE_2_3_3_REV;
            case UINT16, UNORM16 -> GL_UNSIGNED_SHORT;
            case UINT32, UNORM32-> GL_UNSIGNED_INT;
            case FLOAT16 -> GL_HALF_FLOAT;
            case FLOAT32 -> GL_FLOAT;
            case UINT3_3_2,UNORM3_3_2 -> GL_UNSIGNED_BYTE_3_3_2;
            case UINT2_10_10_10_REV, UNORM2_10_10_10_REV -> GL_UNSIGNED_INT_2_10_10_10_REV;
            case UINT5_6_5, UNORM5_6_5 -> GL_UNSIGNED_SHORT_5_6_5;
            case UINT4_4_4_4, UNORM4_4_4_4 -> GL_UNSIGNED_SHORT_4_4_4_4;
            case UINT5_5_5_1, UNORM5_5_5_1 -> GL_UNSIGNED_SHORT_5_5_5_1;
            case UINT8_8_8_8,UNORM8_8_8_8 -> GL_UNSIGNED_INT_8_8_8_8;
            case UINT5_6_5_REV, UNORM5_6_5_REV -> GL_UNSIGNED_SHORT_5_6_5_REV;
            case UINT4_4_4_4_REV, UNORM4_4_4_4_REV -> GL_UNSIGNED_SHORT_4_4_4_4_REV;
            case UINT10_10_10_2, UNORM10_10_10_2-> GL_UNSIGNED_INT_10_10_10_2;
            case UINT8_8_8_8_REV, UNORM8_8_8_8_REV -> GL_UNSIGNED_INT_8_8_8_8_REV;
            default -> throw new IllegalArgumentException("Format="+fmt);
        };
    }


    public void init() {
        if(buffers.length == 0 || buffers[0] != 0) throw new IllegalStateException();
        for(int i = 0; i<buffers.length; i++) {
            var desc = descriptionList.get(i);
            var size = desc.size();
            var type = asType(desc.format());
            var norm = desc.format().isNorm();
            buffers[i] = glGenBuffers();
            Preconditions.checkState(buffers[i] !=0);
            glBindBuffer(GL_ARRAY_BUFFER, buffers[i]);
            glVertexAttribPointer(i, size, type, norm, 0, 0);
            glEnableVertexAttribArray(i);
            if(initializer!=null) {
                initializer.initVbo(this, i);
            }
        }
    }

    public void setData(MemoryAddress address, long size) {
        GL33.nglBufferData(GL33.GL_ARRAY_BUFFER, size, address.toRawLongValue(), GL33.GL_STATIC_DRAW);
    }

    public void close() {
        if(buffers.length == 0 || buffers[0] != 0) return;
        for(int i = 0; i<buffers.length; i++) {
            glDeleteBuffers(buffers[i]);
            buffers[i] = 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        return o == this;
    }

    @Override
    public int hashCode() {
        return descriptionList.hashCode();
    }

    @Override
    public String toString() {
        return "VboGroup{" +
                "descriptionList=" + descriptionList +
                ", buffers=" + Arrays.toString(buffers) +
                '}';
    }
}
