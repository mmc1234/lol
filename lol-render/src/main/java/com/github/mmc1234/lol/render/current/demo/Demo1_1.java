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

package com.github.mmc1234.lol.render.current.demo;

import com.github.mmc1234.lol.base.*;
import com.github.mmc1234.lol.render.*;
import com.github.mmc1234.lol.render.current.*;
import org.lwjgl.opengl.*;

import static com.github.mmc1234.lol.glfw.GLFW.*;


@Deprecated
public class Demo1_1 extends DemoBase {
    Camera cam;
    MyMaterial material;

    static class MyMaterial implements Material {
        public float r,g,b,a;
        @Override
        public void apply(final ShaderProgram program) {
            GL20.glUniform4f(program.getUniformLocation("rgba"), this.r, this.g, this.b, this.a);
        }
    }
    @Override
    public void renderInit() {
        this.fps = 1;
        System.out.println("Render Start");
        this.initMainWindow();
        this.material = new MyMaterial();
        this.cam.setFov((float) Math.toRadians(60));
        this.cam.setAspect(1);
        this.cam.setZNear(0.01f);
        this.cam.setZFar(1000);
        this.cam.setBackground(new Float4(0.2f, 0.9f, 0.9f, 1));
        this.cam.setClearMask(GL11.GL_COLOR_BUFFER_BIT);
        this.cam.transform.position.z = 3;
        if(true) throw new Error();
        R.init();
    }

    @Override
    public void logicInit() {
        this.cam = new Camera();
    }

    @Override
    public void renderUpdate() {
        if(this.stopIfWindowShouldClose()) {
            return;
        }
        this.clear(this.cam);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        R.DEFAULT_PROGRAM.use();
        R.WHITE_TEXTURE.bind();
        R.QUAD_VB.getVao().bind();
        this.material.r = 0.4f;
        this.material.g = 1;
        this.material.b = 1;
        this.material.a = 1;
        this.material.apply(R.DEFAULT_PROGRAM);

        this.cam.updateProjection();
        this.cam.updateViewMatrix();
        this.cam.buildModelViewMatrix(new Transform(), this.cam.getViewMatrix());
        GL20.glUniformMatrix4fv(R.DEFAULT_PROGRAM.getUniformLocation("projection_matrix"),
                false, this.cam.getProjectionMatrix().get(new float[16]));
        GL20.glUniformMatrix4fv(R.DEFAULT_PROGRAM.getUniformLocation("model_view_matrix"),
                false, this.cam.getModelViewMatrix().get(new float[16]));

        GL11.nglDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_INT, R.QUAD_MESH.getIndices().address().toRawLongValue());

        Vao.bindZero();
        Texture2D.bindZero();
        ShaderProgram.useZero();
        glfwSwapBuffers(this.window);
    }

    @Override
    public void logicUpdate() {
        Render.recordRenderCall(()-> this.renderTimer.tryRun());
    }

    @Override
    public void renderStop() {
        this.killMainWindow();
        this.shouldBreakLogicLoop.set(true);
        Render.makeShouldStop();
        System.out.println("Render Stop");
    }
    @Override
    public void logicStop() {
        System.out.println("Logic Stop");
    }

    public static void main(final String[] args) {
        new Demo1_1();
    }
}
