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

package com.github.mmc1234.lol.render.legacy.demo;

import com.github.mmc1234.lol.base.*;
import com.github.mmc1234.lol.glfw.*;
import com.github.mmc1234.lol.glfw.impl.*;
import com.google.inject.*;

import static com.github.mmc1234.lol.glfw.GLFW.*;

/**
 * 简单的实现了空的渲染线程
 * */
public class Demo1_1_1 {
    void renderLoop() {
        if(glfwInit()) {
            final var window = glfwCreateWindow(400, 300, "DEMO", Monitor.ofLong(0), Window.ofLong(0));
            glfwMakeContextCurrent(window);
            glfwSwapInterval(0);

            while(!glfwWindowShouldClose(window)) {
                glfwWaitEventsTimeout(0.001f);
                glfwSwapBuffers(window);
            }
            glfwTerminate();
        } else {
            throw new AssertionError();
        }
    }
    Demo1_1_1() {
        Guice.createInjector(new ImplGLFWModule());

        if(glfwInit()) {
            final var renderThread = new Thread(this::renderLoop);
            if(Platform.MACOSX.isCurrent())  renderThread.run();
            else renderThread.start();
            while(renderThread.isAlive()) {
                Sugars.noCatchRunnable(()->Thread.sleep(1));
            }
            glfwTerminate();
        } else {
            throw new AssertionError();
        }
    }
    public static void main(final String[] args) {
        new Demo1_1_1();
    }
}
