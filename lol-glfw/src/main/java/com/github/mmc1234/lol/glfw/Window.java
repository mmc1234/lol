package com.github.mmc1234.lol.glfw;

public record Window(long address) {
    public static final Window ofLong(long address) {
        if(address == 0) return EMPTY;
        return new Window(address);
    }
    public static final Window EMPTY = new Window(0);
    public static final boolean isEmpty(Window window) {
        return window == null || EMPTY.equals(window) || window.address == 0;
    }
}
