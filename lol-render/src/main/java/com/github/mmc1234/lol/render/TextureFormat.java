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

public enum TextureFormat {
    R8G8B8A8_UINT, A8_UINT;

    public int toInt() {
        return switch (this) {
            case R8G8B8A8_UINT -> GL11.GL_RGBA;
            case A8_UINT -> GL11.GL_ALPHA;
        };
    }

    public int toTypeInt() {
        return switch (this) {
            case R8G8B8A8_UINT, A8_UINT -> GL11.GL_UNSIGNED_BYTE;
        };
    }
}
