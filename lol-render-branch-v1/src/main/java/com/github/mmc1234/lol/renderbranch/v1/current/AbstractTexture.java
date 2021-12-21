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

package com.github.mmc1234.lol.renderbranch.v1.current;

import com.github.mmc1234.lol.renderbranch.v1.*;
import com.google.common.base.*;
import org.lwjgl.opengl.*;

public abstract class AbstractTexture {
    protected int width, height, depth;
    protected TextureFilterMode minFilter, magFilter;
    protected TextureFormat format, internalFormat;
    protected TextureWarpMode warpModeU, warpModeV, warpModeW;
    protected int id;
    protected TextureDimension dimension;
    protected String name;


    public void setMinFilter(TextureFilterMode minFilter) {
        this.minFilter = minFilter;
    }

    public void setMagFilter(TextureFilterMode magFilter) {
        this.magFilter = magFilter;
    }

    public abstract void close();

    public int getDepth() {
        return depth;
    }

    public TextureDimension getDimension() {
        return dimension;
    }

    public TextureFormat getFormat() {
        return format;
    }

    public int getHeight() {
        return height;
    }

    public int getId() {
        return id;
    }

    public TextureFormat getInternalFormat() {
        return internalFormat;
    }

    public TextureFilterMode getMagFilter() {
        return magFilter;
    }

    public TextureFilterMode getMinFilter() {
        return minFilter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TextureWarpMode getWarpModeU() {
        return warpModeU;
    }

    public TextureWarpMode getWarpModeV() {
        return warpModeV;
    }

    public TextureWarpMode getWarpModeW() {
        return warpModeW;
    }

    public int getWidth() {
        return width;
    }

    public abstract void init();

    public void bind() {
        GL33.glBindTexture(dimension.toInt(), getId());
    }

    @Override
    public String toString() {
        return "Texture(" + name + "=" + id + ")";
    }
}
