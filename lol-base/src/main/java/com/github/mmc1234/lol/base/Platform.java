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
