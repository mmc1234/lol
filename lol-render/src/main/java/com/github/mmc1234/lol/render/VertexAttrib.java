/*
 * Copyright 2021. mmc1234
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mmc1234.lol.render;

import com.google.common.base.*;
import com.google.common.collect.*;

import java.util.*;

public record VertexAttrib(TypeFormat format, int size) {

    public VertexAttrib {
        Preconditions.checkNotNull(format);
        Preconditions.checkArgument(size >= 0);
    }

    public static List<VertexAttrib> list(final VertexAttrib... descriptions) {
        final List<VertexAttrib> list = Lists.newArrayList();
        for (final var desc : descriptions) {
            if (desc != null) {
                list.add(desc);
            }
        }
        return List.copyOf(list);
    }

    public static List<VertexAttrib> list(final TypeFormat f1, final int s1) {
        return VertexAttrib.list(of(f1, s1));
    }

    public static List<VertexAttrib> list(final TypeFormat f1, final int s1,
                                          final TypeFormat f2, final int s2) {
        return VertexAttrib.list(
                of(f1, s1),
                of(f2, s2));
    }

    public static List<VertexAttrib> list(final TypeFormat f1, final int s1,
                                          final TypeFormat f2, final int s2,
                                          final TypeFormat f3, final int s3) {
        return VertexAttrib.list(
                of(f1, s1),
                of(f2, s2),
                of(f3, s3));
    }

    public static List<VertexAttrib> list(final TypeFormat f1, final int s1,
                                          final TypeFormat f2, final int s2,
                                          final TypeFormat f3, final int s3,
                                          final TypeFormat f4, final int s4) {
        return VertexAttrib.list(
                of(f1, s1),
                of(f2, s2),
                of(f3, s3),
                of(f4, s4));
    }

    public static List<VertexAttrib> list(final TypeFormat f1, final int s1,
                                          final TypeFormat f2, final int s2,
                                          final TypeFormat f3, final int s3,
                                          final TypeFormat f4, final int s4,
                                          final TypeFormat f5, final int s5) {
        return VertexAttrib.list(
                of(f1, s1),
                of(f2, s2),
                of(f3, s3),
                of(f4, s4),
                of(f5, s5));
    }

    public static VertexAttrib of(final TypeFormat format, final int size) {
        return new VertexAttrib(format, size);
    }
}