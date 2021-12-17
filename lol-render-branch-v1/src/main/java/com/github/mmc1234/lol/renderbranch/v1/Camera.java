package com.github.mmc1234.lol.renderbranch.v1;

import com.github.mmc1234.lol.base.*;
import com.google.common.base.*;
import org.joml.*;
import org.lwjgl.opengl.*;

import java.lang.Math;

public class Camera {
    public static final Float4 DEFAULT_COLOR = new Float4(0.25f,0.25f,0.25f,1f);
    private Float4 background = DEFAULT_COLOR;
    private FrameBuffer frameBuffer;
    private int clearMask;
    private final Vector3f position;
    private final Vector3f rotation;

    public Camera() {
        position = new Vector3f(0, 0, 0);
        rotation = new Vector3f(0, 0, 0);
    }

    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }

    public void movePosition(float offsetX, float offsetY, float offsetZ) {
        if ( offsetZ != 0 ) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y)) * -1.0f * offsetZ;
            position.z += (float)Math.cos(Math.toRadians(rotation.y)) * offsetZ;
        }
        if ( offsetX != 0) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * offsetX;
            position.z += (float)Math.cos(Math.toRadians(rotation.y - 90)) * offsetX;
        }
        position.y += offsetY;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        rotation.x = x;
        rotation.y = y;
        rotation.z = z;
    }

    public void moveRotation(float offsetX, float offsetY, float offsetZ) {
        rotation.x += offsetX;
        rotation.y += offsetY;
        rotation.z += offsetZ;
    }

    public int getClearMask() {
        return clearMask;
    }


    private Matrix4f viewMatrix = new Matrix4f().identity();
    public Matrix4f getViewMatrix() {
        Vector3f cameraPos = this.getPosition();
        Vector3f rotation = this.getRotation();
        viewMatrix.identity();
        // 首先进行旋转，使摄像机在其位置上旋转
        viewMatrix.rotate((float)Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
                .rotate((float)Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
        // 然后做位移
        viewMatrix.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        return viewMatrix;
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
