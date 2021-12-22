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
        return this.width() >= 0 ? this.x() : this.x() + this.width();
    }

    default int minY() {
        return this.height() >= 0 ? this.y() : this.y() + this.height();
    }

    default int maxX() {
        return this.width() < 0 ? this.x() : this.x() + this.width();
    }

    default int maxY() {
        return this.height() < 0 ? this.y() : this.y() + this.height();
    }

    default IntRectangle and(final IntRectangle b) {
        final int x1 = Math.max(this.minX(), b.minX());
        final int y1 = Math.max(this.minY(), b.minY());
        final int x2 = Math.min(this.maxX(), b.maxX());
        final int y2 = Math.min(this.maxY(), b.maxY());
        return new ImmutableIntRectangle(x1, y1, x2 - x1, y2 - y1);
    }

    static IntRectangle of(final int x, final int y, final int w, final int h) {
        return new ImmutableIntRectangle(x, y, w, h);
    }
}
