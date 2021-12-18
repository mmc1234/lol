package com.github.mmc1234.lol.renderbranch.v1.demo;

import com.github.mmc1234.lol.base.*;
import com.github.mmc1234.lol.renderbranch.v1.*;
import org.lwjgl.opengl.*;

public class CoordRenderData {
    Float3Array vertices;
    Float4Array colors;
    Float2Array uvs;

    Vao vao;
    VboGroup vboGroup;

    public Vao getVao() {
        return vao;
    }

    public void init() {
        vao = new Vao();
        vboGroup = VboGroup.newInstance( (vboGroup, idx) -> {
                    switch (idx) {
                        case 0->vboGroup.setData(vertices.getSegment().address(), (int) vertices.getSegment().byteSize());
                        case 1->vboGroup.setData(colors.getSegment().address(), (int) colors.getSegment().byteSize());
                        case 2->vboGroup.setData(uvs.getSegment().address(), (int) vertices.getSegment().byteSize());
                        default -> throw new IllegalArgumentException();
                    }
                },
                VertexAttribDescription.of(TypeFormat.FLOAT32, 3),
                VertexAttribDescription.of(TypeFormat.FLOAT32, 4),
                VertexAttribDescription.of(TypeFormat.FLOAT32, 2));
        vertices = Float3Array.newFloat3Array(6);
        colors = Float4Array.newFloat4Array(6);
        uvs = Float2Array.newFloat2Array(6);
        vertices.set(0, -1, -1, -1);
        vertices.set(1, 10000, -1, -1);
        vertices.set(2, -1, -1, -1);
        vertices.set(3, -1, 10000, -1);
        vertices.set(4, -1, -1, -1);
        vertices.set(5, -1, -1, 10000);
        colors.set(0, 1, 0, 0, 1);
        colors.set(1, 1, 0, 0, 1);
        colors.set(2, 0, 1, 0, 1);
        colors.set(3, 0, 1, 0, 1);
        colors.set(4, 0, 0, 1, 1);
        colors.set(5, 0, 0, 1, 1);
        uvs.set(0, 0f, 0);
        uvs.set(1, 0, 0);
        uvs.set(2, 0, 0);
        uvs.set(3, 0, 0);
        uvs.set(4, 0, 0);
        uvs.set(5, 0, 0);

        vao.init();
        GL33.glBindVertexArray(vao.getVao());
        vboGroup.init();
    }

    public void close() {
        vboGroup.close();
        vao.close();
    }
}
