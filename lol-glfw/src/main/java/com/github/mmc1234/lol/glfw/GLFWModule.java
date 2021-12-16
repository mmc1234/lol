package com.github.mmc1234.lol.glfw;

import com.google.inject.*;

public class GLFWModule extends AbstractModule {
    @Override
    protected void configure() {
        requestStaticInjection(GLFW.class);
    }
}
