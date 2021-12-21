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

public class DebugFont {
    private Texture2D fontTexture;
    private DebugFontImpl impl;

    public Texture2D getFontTexture() {
        return fontTexture;
    }

    private String path;

    public DebugFont(String path) {
        this.path = path;
    }

    public void init() {
        Preconditions.checkState(Render.isRenderThread());
        if(fontTexture == null) {
            impl = new DebugFontImpl();
            DebugFontImpl.Image img = impl.getImage();
            fontTexture = new Texture2D(TextureFormat.A8_UINT, TextureFormat.R8G8B8A8_UINT, 256, 256);
            fontTexture.setMinFilter(TextureFilterMode.LINEAR);
            fontTexture.setMagFilter(TextureFilterMode.LINEAR);
            fontTexture.init();
            fontTexture.reload(img.pixels());
            Texture2D.bindZero();
            // impl.close();
            impl = null;
        }
    }

    public void close() {
        if(fontTexture != null) {
            fontTexture.close();
        }
    }
}
