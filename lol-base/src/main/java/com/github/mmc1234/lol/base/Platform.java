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

    Platform(String name) {
        this.name = name;
    }

    public static Platform get() {
        return current;
    }

    public static void start(Thread thread) {
        if (thread != null && thread.isAlive()) return;
        if (Platform.MACOSX.equals(Platform.get())) {
            thread.run();
        } else {
            thread.start();
        }
    }

    public boolean isCurrent() {
        return get() == this;
    }

    static {
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows")) {
            current = WINDOWS;
        } else if (!osName.startsWith("Linux") && !osName.startsWith("FreeBSD") && !osName.startsWith("SunOS") && !osName.startsWith("Unix")) {
            if (!osName.startsWith("Mac OS X") && !osName.startsWith("Darwin")) {
                throw new LinkageError("Unknown platform: " + osName);
            }
            current = MACOSX;
        } else {
            current = LINUX;
        }
    }
}
