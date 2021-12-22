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

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL30.GL_HALF_FLOAT;

public enum TypeFormat {
    FLOAT16, FLOAT32,
    INT8, UINT8, INT16, UINT16, INT32, UINT32,
    SNORM8, UNORM8, SNORM16, UNORM16, SNORM32, UNORM32,

    UINT3_3_2,
    UINT2_3_3_REV,

    UINT5_6_5,
    UINT5_6_5_REV,

    UINT4_4_4_4,
    UINT4_4_4_4_REV,

    UINT5_5_5_1,
    UINT1_5_5_5_REV,

    UINT8_8_8_8,
    UINT8_8_8_8_REV,

    UINT10_10_10_2,
    UINT2_10_10_10_REV,


    UNORM3_3_2,
    UNORM2_3_3_REV,

    UNORM5_6_5,
    UNORM5_6_5_REV,

    UNORM4_4_4_4,
    UNORM4_4_4_4_REV,

    UNORM5_5_5_1,
    UNORM1_5_5_5_REV,

    UNORM8_8_8_8,
    UNORM8_8_8_8_REV,

    UNORM10_10_10_2,
    UNORM2_10_10_10_REV;

    public int toInt() {
        return switch (this) {
            case INT8, SNORM8 -> GL_BYTE;
            case INT16, SNORM16 -> GL_SHORT;
            case INT32, SNORM32 -> GL_INT;
            case UINT8, UNORM8 -> GL_UNSIGNED_BYTE;
            case UINT1_5_5_5_REV, UNORM1_5_5_5_REV -> GL_UNSIGNED_SHORT_1_5_5_5_REV;
            case UINT2_3_3_REV, UNORM2_3_3_REV -> GL_UNSIGNED_BYTE_2_3_3_REV;
            case UINT16, UNORM16 -> GL_UNSIGNED_SHORT;
            case UINT32, UNORM32 -> GL_UNSIGNED_INT;
            case FLOAT16 -> GL_HALF_FLOAT;
            case FLOAT32 -> GL_FLOAT;
            case UINT3_3_2, UNORM3_3_2 -> GL_UNSIGNED_BYTE_3_3_2;
            case UINT2_10_10_10_REV, UNORM2_10_10_10_REV -> GL_UNSIGNED_INT_2_10_10_10_REV;
            case UINT5_6_5, UNORM5_6_5 -> GL_UNSIGNED_SHORT_5_6_5;
            case UINT4_4_4_4, UNORM4_4_4_4 -> GL_UNSIGNED_SHORT_4_4_4_4;
            case UINT5_5_5_1, UNORM5_5_5_1 -> GL_UNSIGNED_SHORT_5_5_5_1;
            case UINT8_8_8_8, UNORM8_8_8_8 -> GL_UNSIGNED_INT_8_8_8_8;
            case UINT5_6_5_REV, UNORM5_6_5_REV -> GL_UNSIGNED_SHORT_5_6_5_REV;
            case UINT4_4_4_4_REV, UNORM4_4_4_4_REV -> GL_UNSIGNED_SHORT_4_4_4_4_REV;
            case UINT10_10_10_2, UNORM10_10_10_2 -> GL_UNSIGNED_INT_10_10_10_2;
            case UINT8_8_8_8_REV, UNORM8_8_8_8_REV -> GL_UNSIGNED_INT_8_8_8_8_REV;
            default -> throw new IllegalStateException();
        };
    }

    public static boolean isNorm(TypeFormat format) {
        return switch (format) {
            case SNORM8, SNORM16, SNORM32, UNORM1_5_5_5_REV, UNORM2_3_3_REV, UNORM2_10_10_10_REV,
                    UNORM3_3_2, UNORM4_4_4_4, UNORM4_4_4_4_REV, UNORM5_5_5_1, UNORM5_6_5, UNORM5_6_5_REV, UNORM8,
                    UNORM8_8_8_8, UNORM8_8_8_8_REV, UNORM10_10_10_2, UNORM16, UNORM32 -> true;
            default -> false;
        };
    }

    public static int formatToInt(TypeFormat fmt) {
        return fmt.toInt();
    }

    public boolean isNorm() {
        return switch (this) {
            case SNORM8, SNORM16, SNORM32, UNORM1_5_5_5_REV, UNORM2_3_3_REV, UNORM2_10_10_10_REV,
                    UNORM3_3_2, UNORM4_4_4_4, UNORM4_4_4_4_REV, UNORM5_5_5_1, UNORM5_6_5, UNORM5_6_5_REV, UNORM8,
                    UNORM8_8_8_8, UNORM8_8_8_8_REV, UNORM10_10_10_2, UNORM16, UNORM32 -> true;
            default -> false;
        };
    }
}
