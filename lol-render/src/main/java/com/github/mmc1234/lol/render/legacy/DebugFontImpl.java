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

import com.github.mmc1234.lol.base.*;
import com.github.mmc1234.lol.render.*;
import com.google.common.base.*;
import it.unimi.dsi.fastutil.chars.*;
import jdk.incubator.foreign.*;
import org.apache.commons.io.*;
import org.lwjgl.stb.*;

import java.io.*;

public class DebugFontImpl {
    protected Char2ObjectMap<CharDescription> descriptionMap;
    private Image image;
    private ResourceScope scope = ResourceScope.newSharedScope();
    int ascent;
    int descent;
    int lineGap;

    public DebugFontImpl() {
        Preconditions.checkState(Render.isRenderThread());
        this.image = new Image(256,256,MemorySegment.allocateNative(256*256, this.scope));
        final var info = STBTTFontinfo.create();
        final var array = Sugars.noCatch(()->IOUtils.toByteArray(new FileInputStream("E:/simhei.ttf")));

        final var buffer = MemorySegmentUtil.createFromByteArray(this.scope, array);

        final var error = STBTruetype.nstbtt_InitFont(info.address(), buffer.address().toRawLongValue(), 0);
        Preconditions.checkState(error != 0);
        final float scale = STBTruetype.stbtt_ScaleForPixelHeight(info, 48);
        final var out = MemorySegmentUtil.createFromIntArray(0,0,0,0,0,0);
        STBTruetype.nstbtt_GetFontVMetrics(info.address(), out.address().toRawLongValue(),
                out.address().toRawLongValue()+MemoryLayouts.JAVA_INT.byteSize(),
                out.address().toRawLongValue()+MemoryLayouts.JAVA_INT.byteSize()*2);
        this.ascent = (int) (MemoryAccess.getIntAtIndex(out, 0) * scale);
        this.descent = (int) (MemoryAccess.getIntAtIndex(out, 1) * scale);
        this.lineGap = (int) Math.ceil(MemoryAccess.getIntAtIndex(out, 2) * scale);

        for(int i = 'a'; i<('a'+1); i++) {
            final int glyphIndex = STBTruetype.stbtt_FindGlyphIndex(info, i);

            STBTruetype.nstbtt_GetGlyphBitmapBoxSubpixel(
                    info.address(), glyphIndex, scale, scale, 0, 0,
                    out.address().toRawLongValue(),
                    out.address().toRawLongValue()+MemoryLayouts.JAVA_INT.byteSize(),
                    out.address().toRawLongValue()+MemoryLayouts.JAVA_INT.byteSize()*2,
                    out.address().toRawLongValue()+MemoryLayouts.JAVA_INT.byteSize()*3);
            STBTruetype.nstbtt_GetGlyphHMetrics(info.address(), glyphIndex,
                    out.address().toRawLongValue()+MemoryLayouts.JAVA_INT.byteSize()*4,
                    out.address().toRawLongValue()+MemoryLayouts.JAVA_INT.byteSize()*5);
            final int w = MemoryAccess.getIntAtIndex(out, 2)-MemoryAccess.getIntAtIndex(out, 0); // r-l
            final int h = Math.abs(MemoryAccess.getIntAtIndex(out, 1))
                    + Math.abs(MemoryAccess.getIntAtIndex(out, 3)); // b-t
            final int x = MemoryAccess.getIntAtIndex(out, 0);
            final int y = this.ascent +MemoryAccess.getIntAtIndex(out, 1);
            final int advanceWidth = (int) Math.ceil(MemoryAccess.getIntAtIndex(out, 4) * scale);

            final int xStart = i%16;
            final int yStart = i/16;

            final long address = this.image.pixels.address().toRawLongValue();

            STBTruetype.nstbtt_MakeGlyphBitmapSubpixel(info.address(), address,
                    256, 256, 256, scale, scale, 0, 0, glyphIndex);
        }
    }

    public boolean isEmpty(final char ch) {
        if(ch > 255) {
            return true;
        } else {
            return this.descriptionMap.containsKey(ch);
        }
    }

    public static record Image(int w, int h, MemorySegment pixels) {}

    public Image getImage() {
        return this.image;
    }

    public void close() {
        if(this.scope != null) {
            this.scope.close();
            this.scope = null;
        }
        this.image = null;
    }
}
