/*
 * Copyright 2021. mmc1234
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
