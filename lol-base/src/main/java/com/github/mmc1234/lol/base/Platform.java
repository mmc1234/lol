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

public enum Platform {
    LINUX("Linux"),
    WINDOWS("Windows"),
    MACOSX("Mac OS X");

    private static Platform current;
    private final String name;

    Platform(final String name) {
        this.name = name;
    }

    public static Platform get() {
        return Platform.current;
    }

    public static void start(final Thread thread) {
        if (thread != null && thread.isAlive()) return;
        if (MACOSX.equals(get())) {
            thread.run();
        } else {
            thread.start();
        }
    }

    public boolean isCurrent() {
        return Platform.get() == this;
    }

    static {
        final String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows")) {
            Platform.current = Platform.WINDOWS;
        } else if (!osName.startsWith("Linux") && !osName.startsWith("FreeBSD") && !osName.startsWith("SunOS") && !osName.startsWith("Unix")) {
            if (!osName.startsWith("Mac OS X") && !osName.startsWith("Darwin")) {
                throw new LinkageError("Unknown platform: " + osName);
            }
            Platform.current = Platform.MACOSX;
        } else {
            Platform.current = Platform.LINUX;
        }
    }
}
