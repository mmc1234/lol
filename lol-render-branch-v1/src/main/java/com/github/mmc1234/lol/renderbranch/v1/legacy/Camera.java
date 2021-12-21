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
import com.github.mmc1234.lol.renderbranch.v1.current.*;
import com.google.common.base.*;
import org.joml.*;
import org.lwjgl.opengl.*;

import java.lang.Math;

public class Camera {
    public static final Float4 DEFAULT_COLOR = new Float4(0.25f,0.25f,0.25f,1f);
    private Float4 background = DEFAULT_COLOR;
    private FrameBuffer frameBuffer;
    private int clearMask;
    public final Transform transform;

    private float fov;
    private float aspectRatio;
    private float zFar, zNear;


    private final Matrix4f projectionMatrix;

    private final Matrix4f modelViewMatrix;

    private final Matrix4f viewMatrix;

    public final Matrix4f getProjectionMatrix() {
        return projectionMatrix.setPerspective(fov, aspectRatio, zNear, zFar);
    }

    public Matrix4f getViewMatrix() {
        Vector3f cameraPos = transform.position;
        Vector3f rotation = transform.rotation;

        viewMatrix.identity();
        // First do the rotation so camera rotates over its position
        viewMatrix.rotate((float)Math.toRadians(rotation.x), new Vector3f(1, 0, 0)) .rotate((float)Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
        // Then do the translation
        viewMatrix.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        return viewMatrix;
    }

    public Matrix4f getModelViewMatrix(Matrix4f viewMatrix) {
        modelViewMatrix.identity().translate(new Vector3f(0)).
                rotateX((float)Math.toRadians(-0)).
                rotateY((float)Math.toRadians(-0)).
                rotateZ((float)Math.toRadians(-0)).
                scale(1);
        Matrix4f viewCurr = new Matrix4f(viewMatrix);
        return viewCurr.mul(modelViewMatrix);
    }


    public Camera() {
        transform = new Transform();
        projectionMatrix = new Matrix4f();
        modelViewMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
        setZNear(0.01f);
        setZFar(1000);
        setFov((float) Math.toRadians(60f));
        setAspectRatio(1);
    }


    public void setFov(float fov) {
        this.fov = fov;
    }

    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
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

    public float getAspectRatio() {
        return aspectRatio;
    }

    public float getZFar() {
        return zFar;
    }

    public float getZNear() {
        return zNear;
    }

    public int getClearMask() {
        return clearMask;
    }

    public void clear() {
        Preconditions.checkState(Render.isRenderThread());
        GL33.glClearColor(background.red(), background.green(), background.blue(), background.alpha());
        GL33.glClear(clearMask);
    }

    public void setClearMask(int clearMask) {
        this.clearMask = clearMask;
    }

    public void setFrameBuffer(FrameBuffer frameBuffer) {
        this.frameBuffer = frameBuffer;
    }

    public FrameBuffer getFrameBuffer() {
        return frameBuffer;
    }

    public void setBackground(Float4 background) {
        this.background = background == null ? DEFAULT_COLOR : background;
    }

    public Float4 getBackground() {
        return background;
    }
}
