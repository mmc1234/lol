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

import org.lwjgl.opengl.*;

public enum TextureDimension {
    TEX2D, TEX3D, CUBE, TEX2D_ARRAY;
    public int toInt() {
        return switch (this) {
            case TEX2D -> GL33.GL_TEXTURE_2D;
            case CUBE -> GL33.GL_TEXTURE_CUBE_MAP;
            case TEX3D -> GL33.GL_TEXTURE_3D;
            case TEX2D_ARRAY -> GL33.GL_TEXTURE_2D_ARRAY;
        };
    }
}
