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
import org.lwjgl.opengl.*;

public class Vao {
    private int id;

    public void init() {
        if(id == 0 && Render.isRenderThread()) {
            id = GL33.glGenVertexArrays();
        }
    }

    public void initAndBind() {
        if(id == 0 && Render.isRenderThread()) {
            id = GL33.glGenVertexArrays();
            GL33.glBindVertexArray(id);
        }
    }

    public void close() {
        if(id != 0 && Render.isRenderThread()) {
            GL33.glDeleteVertexArrays(id);
            id = 0;
        }
    }

    public void bind() {
        if (id != 0 && Render.isRenderThread()) {
            GL33.glBindVertexArray(id);
        }
    }

    public static final void bindZero() {
        if (Render.isRenderThread()) {
            GL33.glBindVertexArray(0);
        }
    }

    public int getId() {
        return id;
    }
}