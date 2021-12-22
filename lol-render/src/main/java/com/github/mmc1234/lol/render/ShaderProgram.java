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

package com.github.mmc1234.lol.render;

import it.unimi.dsi.fastutil.objects.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;

public final class ShaderProgram {
    private int id;
    private final String vertexSource;
    private final String fragmentSource;
    private final Object2IntMap<String> uniformMap = new Object2IntArrayMap<>();

    private ShaderProgram(final String vertexSource, final String fragmentSource) {
        this.vertexSource = vertexSource;
        this.fragmentSource = fragmentSource;
    }

    public static ShaderProgram newInstance(final String vertexSource, final String fragmentSource) {
        return new ShaderProgram(vertexSource, fragmentSource);
    }

    public static void useZero() {
        Render.assertRenderThread();
        GL20.glUseProgram(0);
    }

    public void close() {
        Render.assertRenderThread();
        if (this.id != 0) {
            GL20.glDeleteProgram(this.id);
            this.id = 0;
        }
    }

    public void createUniform(final String uniformName) {
        Render.assertRenderThread();
        final int uniformLocation = glGetUniformLocation(this.id, uniformName);
        if (uniformLocation < 0) {
            throw new IllegalStateException("Could not find uniform:" + uniformName);
        }
        this.uniformMap.put(uniformName, uniformLocation);
    }

    public int getId() {
        return this.id;
    }

    public int getUniformLocation(final String name) {
        return this.uniformMap.getOrDefault(name, -1);
    }

    public void init() {
        Render.assertRenderThread();
        this.id = GL20.glCreateProgram();

        final int vs = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        final int fs = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);

        GL20.glShaderSource(vs, this.vertexSource);
        GL20.glShaderSource(fs, this.fragmentSource);
        GL20.glCompileShader(fs);
        GL20.glCompileShader(vs);
        GL20.glAttachShader(this.id, vs);
        GL20.glAttachShader(this.id, fs);

        GL20.glLinkProgram(this.id);
        if (GL20.glGetProgrami(this.id, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            System.out.println("(\n" + GL20.glGetProgramInfoLog(this.id) + "\n)");
        }
        GL20.glValidateProgram(this.id);

        GL20.glDeleteShader(vs);
        GL20.glDeleteShader(fs);
    }

    public void tryCreateUniform(final String uniformName) {
        if (Render.isRenderThread()) {
            final int uniformLocation = glGetUniformLocation(this.id, uniformName);
            if (uniformLocation >= 0) {
                this.uniformMap.put(uniformName, uniformLocation);
            }
        }
    }

    public void use() {
        Render.assertRenderThread();
        if (this.id != 0) {
            GL20.glUseProgram(this.id);
        }
    }

}
