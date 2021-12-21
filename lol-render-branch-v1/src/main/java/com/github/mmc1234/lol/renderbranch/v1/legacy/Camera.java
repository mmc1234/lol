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

package com.github.mmc1234.lol.renderbranch.v1.legacy;

import com.github.mmc1234.lol.base.*;
import com.github.mmc1234.lol.renderbranch.v1.*;
import com.github.mmc1234.lol.renderbranch.v1.legacy.*;
import org.joml.*;

import java.lang.Math;

public class Camera {
    public final Transform transform = new Transform();

    private final Matrix4f projectionMatrix = new Matrix4f();

    private final Matrix4f viewMatrix = new Matrix4f();

    private final Matrix4f modelMatrix = new Matrix4f();

    private final Matrix4f modelViewMatrix = new Matrix4f();

    private FrameBuffer frameBuffer;

    private int clearMask;

    private Float4 background = new Float4(0.25f, 0.25f, 0.25f,1);

    float fov, aspect, zNear, zFar;

    public int getClearMask() {
        return clearMask;
    }

    public void setClearMask(int clearMask) {
        this.clearMask = clearMask;
    }

    public Float4 getBackground() {
        return background;
    }

    public void setBackground(Float4 background) {
        this.background = background;
    }

    public void setFrameBuffer(FrameBuffer frameBuffer) {
        this.frameBuffer = FrameBuffer.notNull(frameBuffer);
    }

    public FrameBuffer getFrameBuffer() {
        return frameBuffer;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public Matrix4f getModelViewMatrix() {
        return modelViewMatrix;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }

    public void setAspect(float aspect) {
        this.aspect = aspect;
    }

    public void setZFar(float zFar) {
        this.zFar = zFar;
    }

    public void setZNear(float zNear) {
        this.zNear = zNear;
    }

    public float getFov() {
        return fov;
    }

    public float getAspect() {
        return aspect;
    }

    public float getZNear() {
        return zNear;
    }

    public float getZFar() {
        return zFar;
    }

    public void updateProjection() {
        projectionMatrix.identity().setPerspective(fov, aspect, zNear, zFar);
    }
    public void updateViewMatrix() {
        viewMatrix.identity();
        // First do the rotation so camera rotates over its position
        viewMatrix.rotate((float) Math.toRadians(transform.rotation.x), new Vector3f(1, 0, 0))
                .rotate((float)Math.toRadians(transform.rotation.y), new Vector3f(0, 1, 0));
        // Then do the translation
        viewMatrix.translate(-transform.position.x, -transform.position.y, -transform.position.z);
    }

    public void buildModelViewMatrix(Transform transformIn, Matrix4f viewMatrix) {
        modelMatrix.identity().translate(transformIn.getPosition()).
                rotateX((float)Math.toRadians(-transformIn.rotation.x)).
                rotateY((float)Math.toRadians(-transformIn.rotation.y)).
                rotateZ((float)Math.toRadians(-transformIn.rotation.z)).
                scale(transformIn.getScale());
        modelViewMatrix.set(viewMatrix);
        modelViewMatrix.mul(modelMatrix);
    }

}
