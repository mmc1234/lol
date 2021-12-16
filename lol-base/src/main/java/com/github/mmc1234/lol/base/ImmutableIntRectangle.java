package com.github.mmc1234.lol.base;

public final record ImmutableIntRectangle(int x, int y, int width, int height) implements IntRectangle {
    public static final ImmutableIntRectangle EMPTY = new ImmutableIntRectangle(0,0,0,0);
}
