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

package com.github.mmc1234.lol.renderbranch.v1.legacy;

import com.github.mmc1234.lol.renderbranch.v1.*;
import com.google.common.base.*;
import jdk.incubator.foreign.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.opengl.GL30.*;

public class Vbo {
    int id;
    int index;
    VertexAttrib description;
    int draw = GL33.GL_STATIC_DRAW;
    public Vbo(int index, VertexAttrib description) {
        this.index = index;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void init() {
        if(id == 0 && Render.isRenderThread()) {
            id = glGenBuffers();
            Preconditions.checkState(id !=0);
            bind();
            glVertexAttribPointer(index, description.size(), description.format().toInt(), description.format().isNorm(), 0, 0);
            glEnableVertexAttribArray(index);
        }
    }

    public void bind() {
        if(id != 0 && Render.isRenderThread()) {
            glBindBuffer(GL_ARRAY_BUFFER, id);
        }
    }

    public static void bindZero() {
        if(Render.isRenderThread()) {
            glBindBuffer(GL_ARRAY_BUFFER, 0);
        }
    }

    public void close() {
        if(id != 0 && Render.isRenderThread()) {
            glDeleteBuffers(id);
            id = 0;
        }
    }

    public void reload(Mesh mesh) {
        reload(mesh.getMemorySegment(index));
    }
    public void reload(int vertexCount) {
    }
    public void reload(MemorySegment memorySegment) {
        if(id != 0 && Render.isRenderThread()) {
            GL33.nglBufferData(GL33.GL_ARRAY_BUFFER, memorySegment.byteSize(), memorySegment.address().toRawLongValue(), getDraw());
        }
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }
}