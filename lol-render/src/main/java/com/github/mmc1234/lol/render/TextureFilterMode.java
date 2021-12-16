package com.github.mmc1234.lol.render;

/**
 * OpenGL的枚举有6个，但是其实只要根据是否启用mipmap切换就好了
 * */
public enum TextureFilterMode {
    POINT, BI_LINEAR, TRI_LINEAR;
}
