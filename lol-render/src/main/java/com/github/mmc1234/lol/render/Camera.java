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

import com.github.mmc1234.lol.base.*;
import org.joml.*;

import java.lang.Math;

public class Camera {
    public final Transform transform = new Transform();

    private final Matrix4f projectionMatrix = new Matrix4f();

    private final Matrix4f viewMatrix = new Matrix4f();

    private final Matrix4f modelMatrix = new Matrix4f();

    private final Matrix4f modelViewMatrix = new Matrix4f();

    private FrameBuffer frameBuffer = FrameBuffer.DEFAULT;

    private int clearMask;

    private Float4 background = new Float4(0.25f, 0.25f, 0.25f,1);

    float fov, aspect, zNear, zFar;

    public int getClearMask() {
        return this.clearMask;
    }

    public void setClearMask(final int clearMask) {
        this.clearMask = clearMask;
    }

    public Float4 getBackground() {
        return this.background;
    }

    public void setBackground(final Float4 background) {
        this.background = background;
    }

    public void setFrameBuffer(final FrameBuffer frameBuffer) {
        this.frameBuffer = FrameBuffer.notNull(frameBuffer);
    }

    public FrameBuffer getFrameBuffer() {
        return this.frameBuffer;
    }

    public Matrix4f getProjectionMatrix() {
        return this.projectionMatrix;
    }

    public Matrix4f getModelMatrix() {
        return this.modelMatrix;
    }

    public Matrix4f getViewMatrix() {
        return this.viewMatrix;
    }

    public Matrix4f getModelViewMatrix() {
        return this.modelViewMatrix;
    }

    public void setFov(final float fov) {
        this.fov = fov;
    }

    public void setAspect(final float aspect) {
        this.aspect = aspect;
    }

    public void setZFar(final float zFar) {
        this.zFar = zFar;
    }

    public void setZNear(final float zNear) {
        this.zNear = zNear;
    }

    public float getFov() {
        return this.fov;
    }

    public float getAspect() {
        return this.aspect;
    }

    public float getZNear() {
        return this.zNear;
    }

    public float getZFar() {
        return this.zFar;
    }

    public void updateProjection() {
        this.projectionMatrix.identity().setPerspective(this.fov, this.aspect, this.zNear, this.zFar);
    }
    public void updateViewMatrix() {
        this.viewMatrix.identity();
        // First do the rotation so camera rotates over its position
        this.viewMatrix.rotate((float) Math.toRadians(this.transform.rotation.x), new Vector3f(1, 0, 0))
                .rotate((float)Math.toRadians(this.transform.rotation.y), new Vector3f(0, 1, 0));
        // Then do the translation
        this.viewMatrix.translate(-this.transform.position.x, -this.transform.position.y, -this.transform.position.z);
    }

    public void buildModelViewMatrix(final Transform transformIn, final Matrix4f viewMatrix) {
        this.modelMatrix.identity().translate(transformIn.getPosition()).
                rotateX((float)Math.toRadians(-transformIn.rotation.x)).
                rotateY((float)Math.toRadians(-transformIn.rotation.y)).
                rotateZ((float)Math.toRadians(-transformIn.rotation.z)).
                scale(transformIn.getScale());
        this.modelViewMatrix.set(viewMatrix);
        this.modelViewMatrix.mul(this.modelMatrix);
    }

}
