package com.github.mmc1234.lol.base;

public interface IntRectangle {
    int x();
    int y();
    int width();
    int height();
    default int minX() {
        return width() >= 0 ? x() : x() + width();
    }

    default int minY() {
        return height() >= 0 ? y() : y() + height();
    }

    default int maxX() {
        return width() < 0 ? x() : x() + width();
    }

    default int maxY() {
        return height() < 0 ? y() : y() + height();
    }

    default IntRectangle and(IntRectangle b) {
        int x1 = Math.max(minX(), b.minX());
        int y1 = Math.max(minY(), b.minY());
        int x2 = Math.min(maxX(), b.maxX());
        int y2 = Math.min(maxY(), b.maxY());
        return new ImmutableIntRectangle(x1, y1, x2-x1, y2-y1);
    }

    static IntRectangle of(int x, int y, int w, int h) {
        return new ImmutableIntRectangle(x,y,w,h);
    }
}
