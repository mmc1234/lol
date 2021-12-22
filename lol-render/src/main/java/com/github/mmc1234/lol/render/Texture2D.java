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

    public Texture2D(final TextureFormat format, final TextureFormat internalFormat, final int w, final int h) {
        this.dimension = TextureDimension.TEX2D;
        this.warpModeU = TextureWarpMode.CLAMP;
        this.warpModeV = TextureWarpMode.CLAMP;
        this.magFilter = TextureFilterMode.POINT;
        this.minFilter = TextureFilterMode.POINT;
        this.format = format;
        this.internalFormat = internalFormat;
        this.setSize(w,h);
    }

    public Texture2D(final TextureFormat format, final int w, final int h) {
        this(format, TextureFormat.R8G8B8A8_UINT, w,h);
    }


    @Override
    public void close() {
        Render.assertRenderThread();
        if(this.id != 0) {
            GL11.glDeleteTextures(this.id);
            this.id = 0;
        }
    }

    public void setSize(final int width, final int height) {
        this.width = width;
        this.height = height;
    }
    public void reload() {
        Render.assertRenderThread();
        GL11.glTexImage2D(this.dimension.toInt(), 0, this.getInternalFormat().toInt(),
                this.getWidth(), this.getHeight(), 0, this.getFormat().toInt(), this.getFormat().toTypeInt(), 0);
    }
    public void reload(final MemorySegment pixels) {
        Render.assertRenderThread();
        if(this.id != 0) {
            GL11.glTexImage2D(this.dimension.toInt(), 0, this.getInternalFormat().toInt(),
                    this.getWidth(), this.getHeight(), 0, this.getFormat().toInt(), this.getFormat().toTypeInt(), pixels.address().toRawLongValue());
        }
    }

    public static void bindZero() {
        Render.assertRenderThread();
        GL11.glBindTexture(TextureDimension.TEX2D.toInt(), 0);
    }

    @Override
    public void init() {
        Render.assertRenderThread();
        if(this.id == 0) {
            // Create and bind
            this.id = GL11.glGenTextures();
            GL13.glActiveTexture(GL13.GL_TEXTURE0 + 0);
            GL11.glBindTexture(this.dimension.toInt(), this.getId());

            // config
            GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
            GL11.glTexParameteri(this.dimension.toInt(), GL11.GL_TEXTURE_MIN_FILTER, this.minFilter.toInt());
            GL11.glTexParameteri(this.dimension.toInt(), GL11.GL_TEXTURE_MAG_FILTER, this.magFilter.toInt());
            GL11.glTexParameteri(this.dimension.toInt(), GL11.GL_TEXTURE_WRAP_S, this.warpModeU.toInt());
            GL11.glTexParameteri(this.dimension.toInt(), GL11.GL_TEXTURE_WRAP_T, this.warpModeV.toInt());

            // GL33.glTexParameteri(GL33. GL_TEXTURE_2D, GL33.GL_GENERATE_MIPMAP, GL33.GL_TRUE);
            // GL33.glTexParameteri(GL33. GL_TEXTURE_2D, GL33.GL_TEXTURE_MAX_LEVEL, mipmapLevel);
            //GL33.glBindTexture(dimension.toInt(), 0);
        }
    }
}
