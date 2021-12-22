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

import org.joml.*;

import java.lang.Math;

public final class Transform {
    public final Vector3f position;

    public final Vector3f rotation;

    public final Vector3f scale;

    public Transform() {
        this(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(1,1,1));
    }

    public Transform(final Vector3f position, final Vector3f rotation, final Vector3f scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Vector3f getPosition() {
        return this.position;
    }

    public void setPosition(final float x, final float y, final float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public void movePosition(final float offsetX, final float offsetY, final float offsetZ) {
        if ( offsetZ != 0 ) {
            this.position.x += (float)Math.sin(Math.toRadians(this.rotation.y)) * -1.0f * offsetZ;
            this.position.z += (float)Math.cos(Math.toRadians(this.rotation.y)) * offsetZ;
        }
        if ( offsetX != 0) {
            this.position.x += (float)Math.sin(Math.toRadians(this.rotation.y - 90)) * -1.0f * offsetX;
            this.position.z += (float)Math.cos(Math.toRadians(this.rotation.y - 90)) * offsetX;
        }
        this.position.y += offsetY;
    }

    public Vector3f getRotation() {
        return this.rotation;
    }

    public void setRotation(final float x, final float y, final float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }

    public Vector3f getScale() {
        return this.scale;
    }

    public void setScale(final float x, final float y, final float z) {
        this.scale.x = x;
        this.scale.y = y;
        this.scale.z = z;
    }

    public void moveRotation(final float offsetX, final float offsetY, final float offsetZ) {
        this.rotation.x += offsetX;
        this.rotation.y += offsetY;
        this.rotation.z += offsetZ;
    }
}
