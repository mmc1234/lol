module lol.test {
    requires org.lwjgl.opengl;
    requires junit;
    requires com.google.guice;
    requires jdk.incubator.foreign;
    requires lol.base;
    requires lol.glfw;
    requires it.unimi.dsi.fastutil;
    requires lol.glfw.impl;
    requires org.lwjgl.stb;
    requires org.apache.commons.io;
    requires org.joml;
    requires guava;
    requires lol.render;
    requires lol.cmd;
    exports com.github.mmc1234.lol.test;
}