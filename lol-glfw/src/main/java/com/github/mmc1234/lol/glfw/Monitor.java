package com.github.mmc1234.lol.glfw;

public record Monitor(long address) {
    public static final Monitor ofLong(long address) {
        if(address == 0) return EMPTY;
        return new Monitor(address);
    }
    public static final Monitor EMPTY = new Monitor(0);
}
