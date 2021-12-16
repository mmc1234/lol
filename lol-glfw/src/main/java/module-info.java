module lol.glfw {
    requires jdk.incubator.foreign;
    requires lol.base;
    requires com.google.guice;
    exports com.github.mmc1234.lol.glfw;
    exports com.github.mmc1234.lol.glfw.backend;
    opens com.github.mmc1234.lol.glfw;
}