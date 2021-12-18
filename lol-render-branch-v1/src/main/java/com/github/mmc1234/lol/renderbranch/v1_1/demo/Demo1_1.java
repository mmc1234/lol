package com.github.mmc1234.lol.renderbranch.v1_1.demo;

import com.github.mmc1234.lol.glfw.*;
import com.github.mmc1234.lol.glfw.impl.*;
import com.google.common.base.*;
import com.google.inject.*;

import static com.github.mmc1234.lol.glfw.GLFW.*;

public class Demo1_1 {
    public static void main(String[] args) {
        Guice.createInjector(new ImplGLFWModule());
        Preconditions.checkState(GLFW.glfwInit());

        var window = glfwCreateWindow(400, 300, "DEMO", Monitor.ofLong(0), Window.ofLong(0));
        glfwMakeContextCurrent(window);
        glfwSwapInterval(0);

        while(!glfwWindowShouldClose(window)) {
            glfwWaitEventsTimeout(0.001f);
            glfwSwapBuffers(window);
        }
    }
}
