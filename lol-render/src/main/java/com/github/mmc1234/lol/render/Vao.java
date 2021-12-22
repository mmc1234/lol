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

import org.lwjgl.opengl.*;

public class Vao {
    private int id;

    public void init() {
        if(this.id == 0 && Render.isRenderThread()) {
            this.id = GL30.glGenVertexArrays();
        }
    }

    public void initAndBind() {
        if(this.id == 0 && Render.isRenderThread()) {
            this.id = GL30.glGenVertexArrays();
            GL30.glBindVertexArray(this.id);
        }
    }

    public void close() {
        if(this.id != 0 && Render.isRenderThread()) {
            GL30.glDeleteVertexArrays(this.id);
            this.id = 0;
        }
    }

    public void bind() {
        if (this.id != 0 && Render.isRenderThread()) {
            GL30.glBindVertexArray(this.id);
        }
    }

    public static final void bindZero() {
        if (Render.isRenderThread()) {
            GL30.glBindVertexArray(0);
        }
    }

    public int getId() {
        return this.id;
    }
}