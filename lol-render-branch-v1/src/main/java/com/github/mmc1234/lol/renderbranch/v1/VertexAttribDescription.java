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

package com.github.mmc1234.lol.renderbranch.v1;

import com.google.common.base.*;
import com.google.common.collect.*;

import java.util.*;

public record VertexAttribDescription(TypeFormat format, int size) {

    public VertexAttribDescription {
        Preconditions.checkNotNull(format);
        Preconditions.checkArgument(size >= 0);
    }

    public static List<VertexAttribDescription> list(VertexAttribDescription... descriptions) {
        List<VertexAttribDescription> list = Lists.newArrayList();
        for (var desc : descriptions) {
            if (desc != null) {
                list.add(desc);
            }
        }
        return List.copyOf(list);
    }

    public static List<VertexAttribDescription> list(TypeFormat f1, int s1) {
        return list(VertexAttribDescription.of(f1, s1));
    }

    public static List<VertexAttribDescription> list(TypeFormat f1, int s1,
                                                     TypeFormat f2, int s2) {
        return list(
                VertexAttribDescription.of(f1, s1),
                VertexAttribDescription.of(f2, s2));
    }

    public static List<VertexAttribDescription> list(TypeFormat f1, int s1,
                                                     TypeFormat f2, int s2,
                                                     TypeFormat f3, int s3) {
        return list(
                VertexAttribDescription.of(f1, s1),
                VertexAttribDescription.of(f2, s2),
                VertexAttribDescription.of(f3, s3));
    }

    public static List<VertexAttribDescription> list(TypeFormat f1, int s1,
                                                     TypeFormat f2, int s2,
                                                     TypeFormat f3, int s3,
                                                     TypeFormat f4, int s4) {
        return list(
                VertexAttribDescription.of(f1, s1),
                VertexAttribDescription.of(f2, s2),
                VertexAttribDescription.of(f3, s3),
                VertexAttribDescription.of(f4, s4));
    }

    public static List<VertexAttribDescription> list(TypeFormat f1, int s1,
                                                     TypeFormat f2, int s2,
                                                     TypeFormat f3, int s3,
                                                     TypeFormat f4, int s4,
                                                     TypeFormat f5, int s5) {
        return list(
                VertexAttribDescription.of(f1, s1),
                VertexAttribDescription.of(f2, s2),
                VertexAttribDescription.of(f3, s3),
                VertexAttribDescription.of(f4, s4),
                VertexAttribDescription.of(f5, s5));
    }

    public static VertexAttribDescription of(TypeFormat format, int size) {
        return new VertexAttribDescription(format, size);
    }
}