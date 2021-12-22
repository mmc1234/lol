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

import java.util.*;

public class VertexBuffer {
    private final List<VertexAttrib> descriptionList;
    Vao vao = new Vao();
    Vbo[] vboArray;
    public VertexBuffer(final List<VertexAttrib> descriptionList) {
        this.descriptionList = descriptionList;
        this.vboArray = new Vbo[descriptionList.size()];
        for(int i = 0;i<descriptionList.size(); i++) {
            this.vboArray[i] = new Vbo(i, Objects.requireNonNull(descriptionList.get(i)));
        }
    }
    public static final VertexBuffer create(final List<VertexAttrib> descriptionList) {
        return new VertexBuffer(descriptionList);
    }

    public Vao getVao() {
        return this.vao;
    }

    public Vbo getVbo(final int index) {
        return this.vboArray[index];
    }

    public void close() {
        if(Render.isRenderThread()) {
            for(final var vbo : this.vboArray) {
                vbo.close();
            }
            this.vao.close();
        }
    }
}
