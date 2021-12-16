package com.github.mmc1234.lol.p1.render;

import com.google.common.base.*;

public record VertexAttribDescription(TypeFormat format, int size) {

    public VertexAttribDescription {
        Preconditions.checkNotNull(format);
        Preconditions.checkArgument(size>=0);
    }

    public static final VertexAttribDescription of(TypeFormat format, int size) {
        return new VertexAttribDescription(format, size);
    }
}