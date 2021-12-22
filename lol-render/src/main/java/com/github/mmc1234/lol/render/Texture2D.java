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

import jdk.incubator.foreign.*;
import org.lwjgl.opengl.*;

public class Texture2D extends AbstractTexture {

    public Texture2D(TextureFormat format, TextureFormat internalFormat, int w, int h) {
        dimension = TextureDimension.TEX2D;
        warpModeU = TextureWarpMode.CLAMP;
        warpModeV = TextureWarpMode.CLAMP;
        magFilter = TextureFilterMode.POINT;
        minFilter = TextureFilterMode.POINT;
        this.format = format;
        this.internalFormat = internalFormat;
        setSize(w, h);
    }

    public Texture2D(TextureFormat format, int w, int h) {
        this(format, TextureFormat.R8G8B8A8_UINT, w, h);
    }


    @Override
    public void close() {
        Render.assertRenderThread();
        if (id != 0) {
            GL11.glDeleteTextures(id);
            id = 0;
        }
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void reload() {
        Render.assertRenderThread();
        GL11.glTexImage2D(dimension.toInt(), 0, getInternalFormat().toInt(),
                getWidth(), getHeight(), 0, getFormat().toInt(), getFormat().toTypeInt(), 0);
    }

    public void reload(MemorySegment pixels) {
        Render.assertRenderThread();
        if (id != 0) {
            GL11.glTexImage2D(dimension.toInt(), 0, getInternalFormat().toInt(),
                    getWidth(), getHeight(), 0, getFormat().toInt(), getFormat().toTypeInt(), pixels.address().toRawLongValue());
        }
    }

    public static void bindZero() {
        Render.assertRenderThread();
        GL11.glBindTexture(TextureDimension.TEX2D.toInt(), 0);
    }

    @Override
    public void init() {
        Render.assertRenderThread();
        if (id == 0) {
            // Create and bind
            id = GL11.glGenTextures();
            GL13.glActiveTexture(GL13.GL_TEXTURE0 + 0);
            GL11.glBindTexture(dimension.toInt(), getId());

            // config
            GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
            GL11.glTexParameteri(dimension.toInt(), GL11.GL_TEXTURE_MIN_FILTER, minFilter.toInt());
            GL11.glTexParameteri(dimension.toInt(), GL11.GL_TEXTURE_MAG_FILTER, magFilter.toInt());
            GL11.glTexParameteri(dimension.toInt(), GL11.GL_TEXTURE_WRAP_S, warpModeU.toInt());
            GL11.glTexParameteri(dimension.toInt(), GL11.GL_TEXTURE_WRAP_T, warpModeV.toInt());

            // GL33.glTexParameteri(GL33. GL_TEXTURE_2D, GL33.GL_GENERATE_MIPMAP, GL33.GL_TRUE);
            // GL33.glTexParameteri(GL33. GL_TEXTURE_2D, GL33.GL_TEXTURE_MAX_LEVEL, mipmapLevel);
            //GL33.glBindTexture(dimension.toInt(), 0);
        }
    }
}
