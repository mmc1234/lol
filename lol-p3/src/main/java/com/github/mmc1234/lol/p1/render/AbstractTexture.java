package com.github.mmc1234.lol.p1.render;

import org.lwjgl.opengl.*;

public abstract class AbstractTexture {
    private int width, height;
    private TextureFilterMode minFilter, magFilter;
    private TextureFormat format, internalFormat;
    protected int nativeTexture;

    public int getNativeTexture() {
        return nativeTexture;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public TextureFilterMode getMagFilter() {
        return magFilter;
    }

    public TextureFilterMode getMinFilter() {
        return minFilter;
    }

    public TextureFormat getFormat() {
        return format;
    }

    public TextureFormat getInternalFormat() {
        return internalFormat;
    }

    public AbstractTexture(TextureFilterMode magFilter, TextureFilterMode minFilter, TextureFormat format, TextureFormat internalFormat, int width, int height) {
        this.width = width;
        this.height = height;
        this.magFilter = magFilter;
        this.minFilter = minFilter;
        this.format = format;
        this.internalFormat = internalFormat;
    }

    public abstract void init();

    public abstract void close();

    public TextureDimension getDimension() {
        return TextureDimension.TEX2D;
    }

    public static int filterModeAsInt(TextureFilterMode filterMode) {
        return switch (filterMode) {
            case POINT -> GL33.GL_NEAREST;
            case LINEAR -> GL33.GL_LINEAR;
            case NEAREST_MIPMAP_POINT -> GL33.GL_NEAREST_MIPMAP_NEAREST;
            case LINEAR_MIPMAP_POINT -> GL33.GL_LINEAR_MIPMAP_NEAREST;
            case LINEAR_MIPMAP_LINEAR -> GL33.GL_LINEAR_MIPMAP_LINEAR;
            case NEAREST_MIPMAP_LINEAR -> GL33.GL_NEAREST_MIPMAP_LINEAR;
        };
    }

}
