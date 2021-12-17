package com.github.mmc1234.lol.p1.render;

/**
 * OpenGL的枚举有6个，但是其实只要根据是否启用mipmap切换就好了
 * */
public enum TextureFilterMode {
    POINT, LINEAR,
    NEAREST_MIPMAP_POINT,NEAREST_MIPMAP_LINEAR,
    LINEAR_MIPMAP_POINT, LINEAR_MIPMAP_LINEAR;
}
