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

import com.google.common.base.*;
import org.lwjgl.opengl.*;

public class FrameBuffer {
    public static final FrameBuffer DEFAULT = new FrameBuffer(true);
    private int id;
    private final boolean isDefaultBuffer;

    public static final FrameBuffer notNull(final FrameBuffer frameBuffer) {
        return frameBuffer == null ? FrameBuffer.DEFAULT : frameBuffer;
    }

    private FrameBuffer(final boolean isDefaultBuffer) {
        this.isDefaultBuffer = isDefaultBuffer;
    }

    public static FrameBuffer getDefault() {
        return FrameBuffer.DEFAULT;
    }

    public static final FrameBuffer newInstance() {
        return new FrameBuffer(false);
    }

    public void close() {
        if (!this.isDefaultBuffer && Render.isRenderThread()) {
            GL30.glDeleteFramebuffers(this.id);
            this.id = 0;
        }
    }

    public static void chunk() {
        Preconditions.checkState(GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER) == GL30.GL_FRAMEBUFFER_COMPLETE);
    }

    public static void attachTexture2D(final Texture2D texture) {
        GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, texture.getDimension().toInt(), texture.getId(), 0);
    }

    public int getId() {
        return this.id;
    }

    public void bind() {
        if(Render.isRenderThread()) {
            GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, this.id);
        }
    }

    public static void bindZero() {
        FrameBuffer.DEFAULT.bind();
    }

    public void init() {
        if (!this.isDefaultBuffer && Render.isRenderThread()) {
            id = GL30.glGenFramebuffers();
            this.bind();
            // TODO
        }
    }
}
