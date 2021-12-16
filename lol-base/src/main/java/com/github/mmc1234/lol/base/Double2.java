package com.github.mmc1234.lol.base;

public record Double2(double x, double y) {
    public static Double2 create(double x, double y) {
        return new Double2(x,y);
    }
}
