package com.github.mmc1234.lol.renderbranch.v1_1.demo;

import com.github.mmc1234.lol.base.*;
import com.github.mmc1234.lol.glfw.*;
import com.github.mmc1234.lol.glfw.impl.*;
import com.github.mmc1234.lol.renderbranch.v1.*;
import com.github.mmc1234.lol.renderbranch.v1_1.*;
import com.google.common.base.*;
import com.google.inject.*;
import org.lwjgl.opengl.*;

import java.util.concurrent.atomic.*;

import static com.github.mmc1234.lol.glfw.GLFW.*;

public class Demo1_4 {
    Window window = Window.ofLong(0);
    final AtomicBoolean shouldExit = new AtomicBoolean();
    final Timer renderTimer = Timer.newSystem(this::recordRender, 1000/60, true);

    int vao, posVbo, program;
    Float2Array vertices;

    public void init() {
        RenderThread.launch();
        Render.recordRenderCall(this::renderStart);
    }

    public void renderStart() {
        Preconditions.checkState(Render.isRenderThread());
        window = glfwCreateWindow(400, 320, "DEMO", Monitor.ofLong(0), Window.ofLong(0));
        glfwMakeContextCurrent(window);
        glfwSwapInterval(0);
        GL.createCapabilities();

        vertices = Float2Array.newFloat2Array(3);
        vertices.set(0, 0, 0.5f);
        vertices.set(1, -0.5f, -0.5f);
        vertices.set(2, 0.5f, -0.5f);

        GL33.glUseProgram(0);
        program = GL33.glCreateProgram();
        int vs = GL33.glCreateShader(GL33.GL_VERTEX_SHADER);
        GL33.glShaderSource(vs, """
                #version 150
                in vec2 vertexCoord;
                void main() {
                  gl_Position = vec4(vertexCoord, 0,1);
                }
                """);

        GL33.glCompileShader(vs);
        GL33.glAttachShader(program, vs);

        // fs
        int fs = GL33.glCreateShader(GL33.GL_FRAGMENT_SHADER);
        GL33.glShaderSource(fs, """
                #version 150
                                    
                out vec4 finalFragmentColor;
                                    
                void main() {
                  finalFragmentColor = vec4(0, 1, 1, 1);
                }
                                    
                """);

        GL33.glCompileShader(fs);
        GL33.glAttachShader(program, fs);

        GL33.glLinkProgram(program);
        GL33.glValidateProgram(program);
        GL33.glDeleteShader(vs);
        GL33.glDeleteShader(fs);

        vao = GL33.glGenVertexArrays();
        GL33.glBindVertexArray(vao);

        posVbo = GL33.glGenBuffers();
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, posVbo);
        GL33.glVertexAttribPointer(0, 2, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(0);
        GL33.nglBufferData(GL33.GL_ARRAY_BUFFER, vertices.getSegment().byteSize(), vertices.getSegment().address().toRawLongValue(), GL33.GL_STATIC_DRAW);

        // Unbind
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, 0);
        GL33.glBindVertexArray(0);
    }
    public void renderStop() {
        if(true) {
            throw new AssertionError();
        }
        Preconditions.checkState(Render.isRenderThread());
        if(window != Window.EMPTY) {
            glfwHideWindow(window);
            glfwDestroyWindow(window); // 错误源头在这里
            window = Window.ofLong(0);
        }
        RenderThread.makeShouldStop();
    }

    public void render() {
        Preconditions.checkState(Render.isRenderThread());
        if(glfwWindowShouldClose(window)) {
            shouldExit.set(true);
        }
        if(window.address() != 0) {
            GL33.glClearColor(0.25f,0.25f, 0.25f, 1);

            GL33.glUseProgram(program);
            GL33.glBindVertexArray(vao);
            GL33.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);
            GL33.glBindVertexArray(0);
            GL33.glUseProgram(0);

            glfwSwapBuffers(window);
        }


    }

    public void recordRender() {
        Render.recordRenderCall(this::render);
    }
    public void close() {
        Render.recordRenderCall(()-> renderStop());
    }
    public void loop() {
        while(!shouldExit.get()) {
            if(!RenderThread.isThreadAlive()) {
                shouldExit.set(true);
                return;
            }
            renderTimer.tryRun();
            Sugars.noCatchRunnable(()->Thread.sleep(1));
        }
    }

    public Demo1_4() {
        init();
        loop();
        close();
    }

    public static void main(String[] args) {
        Guice.createInjector(new ImplGLFWModule());
        new Demo1_4();
    }
}
