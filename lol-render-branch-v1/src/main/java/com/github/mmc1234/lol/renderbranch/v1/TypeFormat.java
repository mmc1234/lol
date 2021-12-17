package com.github.mmc1234.lol.renderbranch.v1;

/**
 * @link https://blog.csdn.net/weixin_34216107/article/details/92071758
 * */
public enum TypeFormat {
    FLOAT16, FLOAT32,
    INT8, UINT8, INT16, UINT16, INT32, UINT32,
    SNORM8, UNORM8,  SNORM16, UNORM16, SNORM32, UNORM32,

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

    public static boolean isNorm(TypeFormat format) {
        return switch (format) {
            case SNORM8, SNORM16, SNORM32, UNORM1_5_5_5_REV, UNORM2_3_3_REV, UNORM2_10_10_10_REV,
                    UNORM3_3_2,UNORM4_4_4_4, UNORM4_4_4_4_REV, UNORM5_5_5_1, UNORM5_6_5, UNORM5_6_5_REV, UNORM8,
                    UNORM8_8_8_8,UNORM8_8_8_8_REV, UNORM10_10_10_2, UNORM16, UNORM32-> true;
            default -> false;
        };
    }

    public boolean isNorm() {
        return switch (this) {
            case SNORM8, SNORM16, SNORM32, UNORM1_5_5_5_REV, UNORM2_3_3_REV, UNORM2_10_10_10_REV,
                    UNORM3_3_2,UNORM4_4_4_4, UNORM4_4_4_4_REV, UNORM5_5_5_1, UNORM5_6_5, UNORM5_6_5_REV, UNORM8,
                    UNORM8_8_8_8,UNORM8_8_8_8_REV, UNORM10_10_10_2, UNORM16, UNORM32-> true;
            default -> false;
        };
    }
}
