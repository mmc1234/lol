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

public enum TextureFilterMode {
    POINT, LINEAR,
    NEAREST_MIPMAP_POINT, NEAREST_MIPMAP_LINEAR,
    LINEAR_MIPMAP_POINT, LINEAR_MIPMAP_LINEAR;

    public int toInt() {
        return toInt(this);
    }

    public static int toInt(TextureFilterMode filterMode) {
        return switch (filterMode) {
            case POINT -> GL11.GL_NEAREST;
            case LINEAR -> GL11.GL_LINEAR;
            case NEAREST_MIPMAP_POINT -> GL11.GL_NEAREST_MIPMAP_NEAREST;
            case LINEAR_MIPMAP_POINT -> GL11.GL_LINEAR_MIPMAP_NEAREST;
            case LINEAR_MIPMAP_LINEAR -> GL11.GL_LINEAR_MIPMAP_LINEAR;
            case NEAREST_MIPMAP_LINEAR -> GL11.GL_NEAREST_MIPMAP_LINEAR;
        };
    }
}
