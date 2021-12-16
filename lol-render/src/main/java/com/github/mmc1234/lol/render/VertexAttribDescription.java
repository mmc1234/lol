package com.github.mmc1234.lol.render;

public class VertexAttribDescription {

    public static void main(String[] args) {
        var layout = new VertexAttribDescription[] {
                VertexAttribDescription.of(VertexAttrib.POSITION, TypeFormat.FLOAT32, 3),
                VertexAttribDescription.of(VertexAttrib.NORMAL, TypeFormat.UNORM10_10_10_2, 1),
                VertexAttribDescription.of(VertexAttrib.COLOR, TypeFormat.SNORM8, 4),
                VertexAttribDescription.of(VertexAttrib.UV, TypeFormat.SNORM16, 2),
        };
    }

    public static final VertexAttribDescription of(VertexAttrib vertexAttrib, TypeFormat format, int size) {
        return null;
    }
}