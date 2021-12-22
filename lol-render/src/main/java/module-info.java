module lol.render {
    requires com.google.guice;
    requires guava;
    requires com.google.gson;
    requires it.unimi.dsi.fastutil;
    requires lol.base;
    requires lol.glfw;
    requires lol.glfw.impl;
    requires jdk.incubator.foreign;
    requires org.apache.commons.io;
    requires org.lwjgl.opengl;
    requires org.joml;
    requires org.lwjgl.stb;
    opens com.github.mmc1234.lol.render.current.demo;
}