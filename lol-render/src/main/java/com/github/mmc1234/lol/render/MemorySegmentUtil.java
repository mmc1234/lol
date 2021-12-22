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

public class MemorySegmentUtil {
    public static MemorySegment createFromFloatArray(final float... array) {
        final MemorySegment memorySegment = MemorySegment.allocateNative(
                MemoryLayouts.JAVA_FLOAT.byteSize()*array.length, ResourceScope.globalScope());
        for(int i = 0; i<array.length; i++) {
            MemoryAccess.setFloatAtIndex(memorySegment,i, array[i]);
        }
        return memorySegment;
    }
    public static MemorySegment createFromIntArray(final int... array) {
        return MemorySegmentUtil.createFromIntArray(ResourceScope.globalScope(), array);
    }
    public static MemorySegment createFromIntArray(final ResourceScope resourceScope, final int... array) {
        final MemorySegment memorySegment = MemorySegment.allocateNative(
                MemoryLayouts.JAVA_INT.byteSize()*array.length, resourceScope);
        for(int i = 0; i<array.length; i++) {
            MemoryAccess.setIntAtIndex(memorySegment,i, array[i]);
        }
        return memorySegment;
    }
    public static MemorySegment createFromByteArray(final byte... array) {
        return MemorySegmentUtil.createFromByteArray(ResourceScope.globalScope(),array);
    }
    public static MemorySegment createFromByteArray(final ResourceScope resourceScope, final byte... array) {
        final MemorySegment memorySegment = MemorySegment.allocateNative(
                MemoryLayouts.JAVA_BYTE.byteSize()*array.length, resourceScope);
        for(int i = 0; i<array.length; i++) {
            MemoryAccess.setByteAtOffset(memorySegment,i, array[i]);
        }
        return memorySegment;
    }
}
