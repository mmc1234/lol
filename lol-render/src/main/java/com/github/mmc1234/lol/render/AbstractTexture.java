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

public abstract class AbstractTexture {
    protected int width, height, depth;
    protected TextureFilterMode minFilter, magFilter;
    protected TextureFormat format, internalFormat;
    protected TextureWarpMode warpModeU, warpModeV, warpModeW;
    protected int id;
    protected TextureDimension dimension;
    protected String name;

    public void setMinFilter(final TextureFilterMode minFilter) {
        this.minFilter = minFilter;
    }

    public void setMagFilter(final TextureFilterMode magFilter) {
        this.magFilter = magFilter;
    }

    public abstract void close();

    public int getDepth() {
        return this.depth;
    }

    public TextureDimension getDimension() {
        return this.dimension;
    }

    public TextureFormat getFormat() {
        return this.format;
    }

    public int getHeight() {
        return this.height;
    }

    public int getId() {
        return this.id;
    }

    public TextureFormat getInternalFormat() {
        return this.internalFormat;
    }

    public TextureFilterMode getMagFilter() {
        return this.magFilter;
    }

    public TextureFilterMode getMinFilter() {
        return this.minFilter;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public TextureWarpMode getWarpModeU() {
        return this.warpModeU;
    }

    public TextureWarpMode getWarpModeV() {
        return this.warpModeV;
    }

    public TextureWarpMode getWarpModeW() {
        return this.warpModeW;
    }

    public int getWidth() {
        return this.width;
    }

    public abstract void init();

    public void bind() {
        Render.assertRenderThread();
        GL11.glBindTexture(this.dimension.toInt(), this.getId());
    }

    @Override
    public String toString() {
        return "Texture(" + this.name + "=" + this.id + ")";
    }
}
