package com.github.mmc1234.lol.base;

public record Float4(float x, float y, float z, float w) {
    public float red() {
        return x;
    }

    public float green() {
        return y;
    }

    public float blue() {
        return z;
    }

    public float alpha() {
        return w;
    }


}
