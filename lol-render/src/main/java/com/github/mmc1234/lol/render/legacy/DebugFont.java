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

package com.github.mmc1234.lol.render.legacy;

import com.github.mmc1234.lol.render.*;
import com.google.common.base.*;

public class DebugFont {
    private Texture2D fontTexture;
    private DebugFontImpl impl;

    public Texture2D getFontTexture() {
        return this.fontTexture;
    }

    private final String path;

    public DebugFont(final String path) {
        this.path = path;
    }

    public void init() {
        Preconditions.checkState(Render.isRenderThread());
        if(this.fontTexture == null) {
            this.impl = new DebugFontImpl();
            final DebugFontImpl.Image img = this.impl.getImage();
            this.fontTexture = new Texture2D(TextureFormat.A8_UINT, TextureFormat.R8G8B8A8_UINT, 256, 256);
            this.fontTexture.setMinFilter(TextureFilterMode.LINEAR);
            this.fontTexture.setMagFilter(TextureFilterMode.LINEAR);
            this.fontTexture.init();
            this.fontTexture.reload(img.pixels());
            Texture2D.bindZero();
            // impl.close();
            this.impl = null;
        }
    }

    public void close() {
        if(this.fontTexture != null) {
            this.fontTexture.close();
        }
    }
}
