package com.github.mmc1234.lol.test;

import com.github.mmc1234.lol.base.*;
import com.github.mmc1234.lol.glfw.*;
import com.github.mmc1234.lol.glfw.impl.*;
import com.github.mmc1234.lol.render.*;
import com.google.common.collect.*;
import com.google.inject.*;
import jdk.incubator.foreign.*;
import org.junit.*;
import org.lwjgl.opengl.*;

import static com.github.mmc1234.lol.glfw.GLFW.*;
import static org.junit.Assert.*;

public class MiniTest {
    @Before
    public void setUp() {
        Guice.createInjector(new ImplGLFWModule());
        assertTrue(glfwInit());
    }
    @After
    public void shutDown() {
        glfwTerminate();
    }
    WindowHandler windowHandler = new WindowHandler();
    @Test
    public void test() {
        double startTime = glfwGetTime();
        // Start render thread
        var rt = new Thread(()->{
            windowHandler.init();
            glfwMakeContextCurrent(windowHandler.getWindow());
            glfwSwapInterval(0);
            GL.createCapabilities();
            while(!Window.isEmpty(windowHandler.getWindow()) && !glfwWindowShouldClose(windowHandler.getWindow())) {
                glfwWaitEventsTimeout(0.001f);
                Render.replayQueue();
                glfwWaitEventsTimeout(0.001f);
            }
        });
        if(Platform.get() == Platform.MACOSX) {
            rt.run();
        } else {
            rt.start();
        }

        while(Window.isEmpty(windowHandler.getWindow()) || !glfwWindowShouldClose(windowHandler.getWindow())) {
            double currentTime = glfwGetTime();
            if(currentTime-startTime > 1/60f && Render.isEmptyRecordingQueue()) {
                Render.recordRenderCall(()->{
                    GL11.glClearColor(0.25f,0.25f,0.25f,1);
                    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
                    glfwSwapBuffers(windowHandler.getWindow());
                });
            }
            glfwWaitEventsTimeout(0.001f);
        }
    }
}
