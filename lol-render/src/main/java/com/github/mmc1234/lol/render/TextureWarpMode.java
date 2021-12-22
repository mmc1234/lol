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

public enum TextureWarpMode {
    REPEAT,
    /**
     * clamp to border
     */
    CLAMP,
    MIRROR,
    /**
     * clamp to edge
     */
    MIRROR_ONCE;

    public int toInt() {
        return switch (this) {
            case REPEAT -> GL11.GL_REPEAT;
            case CLAMP -> GL13.GL_CLAMP_TO_BORDER;
            case MIRROR -> GL14.GL_MIRRORED_REPEAT;
            case MIRROR_ONCE -> GL12.GL_CLAMP_TO_EDGE;
        };
    }
}
