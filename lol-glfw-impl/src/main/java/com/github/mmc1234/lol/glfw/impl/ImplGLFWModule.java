package com.github.mmc1234.lol.glfw.impl;

import com.github.mmc1234.lol.glfw.*;
import com.github.mmc1234.lol.glfw.backend.*;
import com.google.inject.*;

public class ImplGLFWModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new GLFWModule());
        bind(GlfwBackend.class).to(ImplGLFWBackend.class);
    }
}
