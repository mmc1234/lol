package com.github.mmc1234.lol.test;

import com.github.mmc1234.lol.base.*;
import com.github.mmc1234.lol.glfw.*;
import com.github.mmc1234.lol.glfw.impl.*;
import com.github.mmc1234.lol.render.*;
import com.google.common.base.*;
import com.google.inject.*;
import org.junit.*;
import org.lwjgl.opengl.*;

import java.util.*;

import static com.github.mmc1234.lol.glfw.GLFW.*;
import static org.junit.Assert.*;

public class TextureTest {
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
    WindowHandler windowHandler = new WindowHandler();
    int vao, posVbo, colorVbo, uvVbo, program, myTexture, msLoc;

    IntArray pixels;

    Float2Array vertices;
    Float4Array colors;
    Float2Array uvs;
    void close() {
        if(program != 0) {
            GL33.glDeleteProgram(program);
        }
        if(vao != 0) {
            GL33.glDeleteVertexArrays(vao);
        }

        if(myTexture != 0) {
            GL33.glDeleteTextures(myTexture);
        }

        if(posVbo != 0) {
            GL33.glDeleteBuffers(posVbo);
        }

        if(colorVbo != 0) {
            GL33.glDeleteBuffers(colorVbo);
        }

        if(uvVbo != 0) {
            GL33.glDeleteBuffers(uvVbo);
        }
        msLoc = -1;
    }

    @Test
    public void test() {
        double startTime = glfwGetTime();
        vertices = Float2Array.newFloat2Array(3);
        colors = Float4Array.newFloat4Array(3);
        uvs = Float2Array.newFloat2Array(3);
        pixels = IntArray.newIntArray(32*32);
        vertices.set(0, 0, 0.5f);
        vertices.set(1, -0.5f, -0.5f);
        vertices.set(2, 0.5f, -0.5f);
        colors.set(0, 1,1,1,1);
        colors.set(1, 1,1,1,1);
        colors.set(2, 1,1,1,1);
        uvs.set(0, 0.5f, 0);
        uvs.set(1, 0, 1);
        uvs.set(2, 1, 1);

        Random colorRand = new Random(124);
        for(int i = 0; i<32; i++) {
            for(int j = 0; j<32; j++) {
                pixels.x(j*32+i,
                        0x00005555+colorRand.nextInt(3333));
            }
        }

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
            close();
        });
        rt.setUncaughtExceptionHandler((t, e) -> {
            e.printStackTrace();
            close();
        });
        if(Platform.get() == Platform.MACOSX) {
            rt.run();
        } else {
            rt.start();
        }

        Render.recordRenderCall(()->{
            System.out.println("Init Render");
            GL33.glUseProgram(0);
            program = GL33.glCreateProgram();

            myTexture = GL33.glGenTextures();

            GL33.glActiveTexture(GL33.GL_TEXTURE0+0);
            GL33.glBindTexture(GL33.GL_TEXTURE_2D, myTexture);
            GL33.glPixelStorei(GL33.GL_UNPACK_ALIGNMENT, 4);
            GL33.nglTexImage2D(GL33.GL_TEXTURE_2D, 0,GL33.GL_RGBA, 32, 32, 0,
                    GL33.GL_RGBA, GL33.GL_UNSIGNED_BYTE, pixels.getSegment().address().toRawLongValue());

            GL33.glTexParameteri(GL33.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            GL33.glTexParameteri(GL33.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);


            // vs
            int vs = GL33.glCreateShader(GL33.GL_VERTEX_SHADER);

            GL33.glShaderSource(vs, """
                    #version 150
                    in vec2 vertexCoord;
                    in vec4 vertexColor;
                    in vec2 vertexUV;
                    
                    out vec4 fragmentColor;
                    out vec2 fragmentUV;
                    
                    void main() {
                      gl_Position = vec4(vertexCoord, 0,1);
                      fragmentColor = vertexColor;
                      fragmentUV = vertexUV;
                    }
                    """);

            GL33.glCompileShader(vs);
            GL33.glAttachShader(program, vs);


            // fs
            int fs = GL33.glCreateShader(GL33.GL_FRAGMENT_SHADER);

            GL33.glShaderSource(fs, """
                    #version 150
                    
                    in vec4 fragmentColor;
                    in vec2 fragmentUV;
                    
                    out vec4 finalFragmentColor;
                    
                    uniform sampler2D texture_sampler;
                    
                    void main() {
                      finalFragmentColor = fragmentColor*texture(texture_sampler, fragmentUV);
                    }
                    
                    """);

            GL33.glCompileShader(fs);
            GL33.glAttachShader(program, fs);

            GL33.glLinkProgram(program);
            if(GL33.glGetProgrami(program, GL33.GL_LINK_STATUS) == GL11.GL_FALSE) {
                System.out.println("(\n"+GL33.glGetProgramInfoLog(program)+"\n)");
            }
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

            uvVbo = GL33.glGenBuffers();
            GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, uvVbo);
            GL33.glVertexAttribPointer(2, 2, GL33.GL_FLOAT, false, 0, 0);
            GL33.glEnableVertexAttribArray(2);
            GL33.nglBufferData(GL33.GL_ARRAY_BUFFER, uvs.getSegment().byteSize(), uvs.getSegment().address().toRawLongValue(), GL33.GL_STATIC_DRAW);

            // Unbind
            GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, 0);
            GL33.glBindVertexArray(0);

            GL33.glUseProgram(program);
            msLoc = GL33.glGetUniformLocation(program, "texture_sampler");

            GL33.glUseProgram(0);
        });
        while(rt.isAlive() && (Window.isEmpty(windowHandler.getWindow()) || !glfwWindowShouldClose(windowHandler.getWindow()))) {
            double currentTime = glfwGetTime();
            if(currentTime-startTime > 1/60f && Render.isEmptyRecordingQueue()) {
                startTime = currentTime;
                Render.recordRenderCall(()->{
                    GL11.glClearColor(0.25f,0.25f,0.25f,1);
                    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
                    GL33.glUseProgram(program);
                    GL33.glBindVertexArray(vao);
                    GL33.glUniform1i(msLoc, 0);
                    GL33.glBindTexture(GL33.GL_TEXTURE_2D, myTexture);
                    GL33.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);
                    GL33.glBindTexture(GL33.GL_TEXTURE_2D, 0);
                    GL33.glBindVertexArray(0);
                    GL33.glUseProgram(0);
                    glfwSwapBuffers(windowHandler.getWindow());
                });
            }
            glfwWaitEventsTimeout(0.001f);
        }
    }
}
