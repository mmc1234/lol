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

package com.github.mmc1234.lol.renderbranch.v1.current.demo;

import com.github.mmc1234.lol.glfw.*;
import com.github.mmc1234.lol.glfw.impl.*;
import com.google.common.base.*;
import com.google.inject.*;

import static com.github.mmc1234.lol.glfw.GLFW.*;

/**
 * 关闭垂直同步的简单窗口
 * */
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
