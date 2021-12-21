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
import jdk.incubator.foreign.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

public class Texture2D extends AbstractTexture {

    public Texture2D(TextureFormat format, TextureFormat internalFormat, int w, int h) {
        dimension = TextureDimension.TEX2D;
        warpModeU = TextureWarpMode.CLAMP;
        warpModeV = TextureWarpMode.CLAMP;
        magFilter = TextureFilterMode.POINT;
        minFilter = TextureFilterMode.POINT;
        this.format = format;
        this.internalFormat = internalFormat;
        setSize(w,h);
    }

    public Texture2D(TextureFormat format, int w, int h) {
        this(format, TextureFormat.R8G8B8A8_UINT, w,h);
    }


    @Override
    public void close() {
        if(id != 0) {
            // TODO
            Preconditions.checkState(Render.isRenderThread());
        }
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }
    public void reload() {
        GL33.glTexImage2D(dimension.toInt(), 0, getInternalFormat().toInt(),
                getWidth(), getHeight(), 0, getFormat().toInt(), getFormat().toTypeInt(), 0);
    }
    public void reload(MemorySegment pixels) {
        Preconditions.checkState(Render.isRenderThread());

        if(id != 0) {
            Preconditions.checkState(dimension.toInt() == GL33.GL_TEXTURE_2D);

            GL33.glTexImage2D(dimension.toInt(), 0, getInternalFormat().toInt(),
                    getWidth(), getHeight(), 0, getFormat().toInt(), getFormat().toTypeInt(), pixels.address().toRawLongValue());
        }
    }

    public static void bindZero() {
        GL33.glBindTexture(TextureDimension.TEX2D.toInt(), 0);
    }

    @Override
    public void init() {
        Preconditions.checkState(Render.isRenderThread());
        if(id == 0) {
            // create and bind
            id = GL33.glGenTextures();
            GL33.glActiveTexture(GL33.GL_TEXTURE0+0);
            GL33.glBindTexture(dimension.toInt(), getId());

            // config
            GL33.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
            GL33.glTexParameteri(dimension.toInt(), GL33.GL_TEXTURE_MIN_FILTER, minFilter.toInt());
            GL33.glTexParameteri(dimension.toInt(), GL33.GL_TEXTURE_MAG_FILTER, magFilter.toInt());
            // GL33.glTexParameteri(GL33. GL_TEXTURE_2D, GL33.GL_GENERATE_MIPMAP, GL33.GL_TRUE);
            // GL33.glTexParameteri(GL33. GL_TEXTURE_2D, GL33.GL_TEXTURE_MAX_LEVEL, mipmapLevel);
            //GL33.glBindTexture(dimension.toInt(), 0);
        }
    }
}
