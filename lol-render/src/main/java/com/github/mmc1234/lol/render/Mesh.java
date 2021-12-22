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

import jdk.incubator.foreign.*;

import java.util.*;

public class Mesh {
    List<VertexAttrib> descriptionList;
    MemorySegment indices;

    public Mesh(List<VertexAttrib> list) {
        descriptionList = list;
        memorySegments = new MemorySegment[descriptionList.size()];
    }

    private MemorySegment[] memorySegments;

    public static final Mesh create(List<VertexAttrib> list) {
        return new Mesh(list);
    }

    public MemorySegment getIndices() {
        return indices;
    }

    public void setIndices(MemorySegment indices) {
        this.indices = indices;
    }

    public void setData(int index, MemorySegment memorySegment) {
        memorySegments[index] = memorySegment;
    }

    public MemorySegment getMemorySegment(int index) {
        return memorySegments[index];
    }

    public int size() {
        return memorySegments.length;
    }

    @Override
    public String toString() {
        return "Mesh{" +
                "descriptionList=" + descriptionList +
                '}';
    }

    public void close() {
        if (memorySegments.length > 0) {
            for (int i = 0; i < size(); i++) {
                memorySegments[i] = null;
            }
            memorySegments = new MemorySegment[0];
        }
        if (indices != null) {
            indices = null;
        }
    }
}
