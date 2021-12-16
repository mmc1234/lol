package com.github.mmc1234.lol.glfw;

public record Cursor(long address) {
    public static final Cursor ofLong(long address) {
        if(address == 0) return EMPTY;
        return new Cursor(address);
    }
    public static final Cursor EMPTY = new Cursor(0);
}
