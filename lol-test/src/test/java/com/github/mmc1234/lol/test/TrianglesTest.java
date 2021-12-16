package com.github.mmc1234.lol.test;

import com.github.mmc1234.lol.base.*;
import com.github.mmc1234.lol.glfw.*;
import com.github.mmc1234.lol.glfw.impl.*;
import com.github.mmc1234.lol.render.*;
import com.google.inject.*;
import jdk.incubator.foreign.*;
import org.junit.*;
import org.lwjgl.opengl.*;

import static com.github.mmc1234.lol.glfw.GLFW.*;
import static org.junit.Assert.*;

public class TrianglesTest {
    @Before
    public void setUp() {
        Guice.createInjector(new ImplGLFWModule());
        assertTrue(glfwInit());
    }
    @After
    public void shutDown() {
        glfwDestroyWindow(windowHandler.getWindow());
        glfwTerminate();
    }

    void close() {
        if(program != 0) {
            GL33.glDeleteProgram(program);
        }
        if(vao != 0) {
            GL33.glDeleteVertexArrays(vao);
        }

        if(posVbo != 0) {
            GL33.glDeleteBuffers(posVbo);
        }

        if(colorVbo != 0) {
            GL33.glDeleteBuffers(colorVbo);
        }
    }

    WindowHandler windowHandler = new WindowHandler();
    int vao, posVbo, colorVbo, program;

    Float2Array vertices;
    Float4Array colors;

    @Test
    public void test() {
        double startTime = glfwGetTime();
        vertices = Float2Array.newFloat2Array(3);
        colors = Float4Array.newFloat4Array(3);
        vertices.set(0, 0, 0.5f);
        vertices.set(1, -0.5f, -0.5f);
        vertices.set(2, 0.5f, -0.5f);
        colors.set(0, 1,0,0,1);
        colors.set(1, 0,1,0,1);
        colors.set(2, 0,0,1,1);

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

        rt.setUncaughtExceptionHandler((t,e)->{
            e.printStackTrace();
            close();
        });

        Render.recordRenderCall(()->{

            GL33.glUseProgram(0);
            program = GL33.glCreateProgram();
            // vs
            int vs = GL33.glCreateShader(GL33.GL_VERTEX_SHADER);

            GL33.glShaderSource(vs, """
                    #version 150
                    in vec2 vertexCoord;
                    in vec4 vertexColor;
                    
                    out vec4 fragmentColor;
                    
                    void main() {
                      gl_Position = vec4(vertexCoord, 0,1);
                      fragmentColor = vertexColor;
                    }
                    """);

            GL33.glCompileShader(vs);
            GL33.glAttachShader(program, vs);


            // fs
            int fs = GL33.glCreateShader(GL33.GL_FRAGMENT_SHADER);

            GL33.glShaderSource(fs, """
                    #version 150
                    
                    in vec4 fragmentColor;
                    
                    out vec4 finalFragmentColor;
                    
                    void main() {
                      finalFragmentColor = fragmentColor;
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

            colorVbo = GL33.glGenBuffers();
            GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, colorVbo);
            GL33.glVertexAttribPointer(1, 4, GL33.GL_FLOAT, false, 0, 0);
            GL33.glEnableVertexAttribArray(1);
            GL33.nglBufferData(GL33.GL_ARRAY_BUFFER, colors.getSegment().byteSize(), colors.getSegment().address().toRawLongValue(), GL33.GL_STATIC_DRAW);

            // Unbind
            GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, 0);
            GL33.glBindVertexArray(0);


        });

        while(Window.isEmpty(windowHandler.getWindow()) || !glfwWindowShouldClose(windowHandler.getWindow())) {
            double currentTime = glfwGetTime();
            if(currentTime-startTime > 1/60f && Render.isEmptyRecordingQueue()) {
                Render.recordRenderCall(()->{
                    GL11.glClearColor(0.25f,0.25f,0.25f,1);
                    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
                    GL33.glUseProgram(program);
                    GL33.glBindVertexArray(vao);
                    GL33.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);
                    GL33.glBindVertexArray(0);
                    GL33.glUseProgram(0);
                    glfwSwapBuffers(windowHandler.getWindow());
                });
            }
            glfwWaitEventsTimeout(0.001f);
        }
        close();
    }
}
