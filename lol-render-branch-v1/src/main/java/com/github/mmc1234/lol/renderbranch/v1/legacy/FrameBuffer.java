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
import org.lwjgl.opengl.*;

public class FrameBuffer {
    public static final FrameBuffer DEFAULT = new FrameBuffer(true);
    private int id;
    private boolean isDefaultBuffer;

    public static final FrameBuffer notNull(FrameBuffer frameBuffer) {
        return frameBuffer == null ? DEFAULT : frameBuffer;
    }

    private FrameBuffer(boolean isDefaultBuffer) {
        this.isDefaultBuffer = isDefaultBuffer;
    }

    public static FrameBuffer getDefault() {
        return DEFAULT;
    }

    public static final FrameBuffer newInstance() {
        return new FrameBuffer(false);
    }

    public void close() {
        if (!isDefaultBuffer && Render.isRenderThread()) {
            GL33.glDeleteFramebuffers(id);
            id = 0;
        }
    }

    public static void chunk() {
        Preconditions.checkState(GL33.glCheckFramebufferStatus(GL33.GL_FRAMEBUFFER) == GL33.GL_FRAMEBUFFER_COMPLETE);
    }

    public static void attachTexture2D(Texture2D texture) {
        GL33.glFramebufferTexture2D(GL33.GL_FRAMEBUFFER, GL33.GL_COLOR_ATTACHMENT0, texture.getDimension().toInt(), texture.getId(), 0);
    }

    public int getId() {
        return id;
    }

    public void bind() {
        if(Render.isRenderThread()) {
            GL33.glBindFramebuffer(GL33.GL_DRAW_FRAMEBUFFER, id);
        }
    }

    public static void bindZero() {
        DEFAULT.bind();
    }

    public void init() {
        if (!isDefaultBuffer && Render.isRenderThread()) {
            this.id = GL33.glGenFramebuffers();
            bind();
            // TODO
        }
    }
}
