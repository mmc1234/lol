/*
 * Copyright 2021. mmc1234
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mmc1234.lol.renderbranch.v1;

import it.unimi.dsi.fastutil.objects.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;

public final class ShaderProgram {
    private int id;
    private String vertexSource;
    private String fragmentSource;
    private Object2IntMap<String> uniformMap = new Object2IntArrayMap<>();

    public void createUniform(String uniformName) {
        int uniformLocation = glGetUniformLocation(id, uniformName);
        if (uniformLocation < 0) {
            throw new IllegalStateException("Could not find uniform:" + uniformName);
        }
        uniformMap.put(uniformName, uniformLocation);
    }
    public void tryCreateUniform(String uniformName) {
        int uniformLocation = glGetUniformLocation(id, uniformName);
        if (uniformLocation >= 0) {
            uniformMap.put(uniformName, uniformLocation);
        }
    }

    public int getUniformLocation(String name) {
        return uniformMap.getOrDefault(name, -1);
    }

    public int getId() {
        return id;
    }

    private ShaderProgram(String vertexSource, String fragmentSource) {
        this.vertexSource = vertexSource;
        this.fragmentSource = fragmentSource;
    }
    public static final ShaderProgram newInstance(String vertexSource, String fragmentSource) {
        return new ShaderProgram(vertexSource, fragmentSource);
    }
    public void init() {
        if(id == 0 && Render.isRenderThread()) {
            id = GL33.glCreateProgram();

            int vs = GL33.glCreateShader(GL33.GL_VERTEX_SHADER);
            int fs = GL33.glCreateShader(GL33.GL_FRAGMENT_SHADER);

            GL33.glShaderSource(vs, vertexSource);
            GL33.glShaderSource(fs, fragmentSource);
            GL33.glCompileShader(fs);
            GL33.glCompileShader(vs);
            GL33.glAttachShader(id, vs);
            GL33.glAttachShader(id, fs);

            GL33.glLinkProgram(id);
            if (GL33.glGetProgrami(id, GL33.GL_LINK_STATUS) == GL11.GL_FALSE) {
                System.out.println("(\n" + GL33.glGetProgramInfoLog(id) + "\n)");
            }
            GL33.glValidateProgram(id);

            GL33.glDeleteShader(vs);
            GL33.glDeleteShader(fs);
        }
    }

    public void use() {
        if(id != 0 && Render.isRenderThread()) {
            GL33.glUseProgram(id);
        }
    }

    public static void useZero() {
        if(Render.isRenderThread()) {
            GL33.glUseProgram(0);
        }
    }

    public void close() {
        if(id != 0 && Render.isRenderThread()) {
            GL33.glDeleteProgram(id);
            id = 0;
        }
    }

}
