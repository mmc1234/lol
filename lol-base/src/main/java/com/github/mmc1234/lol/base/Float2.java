package com.github.mmc1234.lol.base;

public interface Float2 {
    static ImmutableFloat2 of(float x, float y) {
        return new ImmutableFloat2(x,y);
    }
}
