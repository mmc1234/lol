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

package com.github.mmc1234.lol.render.current;

import com.github.mmc1234.lol.base.*;
import com.github.mmc1234.lol.render.*;
import jdk.incubator.foreign.*;
import org.apache.commons.io.*;
import org.lwjgl.opengl.*;
import org.lwjgl.stb.*;

import java.io.*;
import java.util.*;

public class R {
    public static ShaderProgram DEFAULT_PROGRAM;

    public static final String SHADERS_PATH = "shaders/";

    public static Mesh COORD_MESH;
    public static Mesh QUAD_MESH;

    public static Texture2D WHITE_TEXTURE;

    public static VertexBuffer COORD_VB;
    public static VertexBuffer QUAD_VB;

    public static final List<VertexAttrib> defaultVertexAttribList = VertexAttrib.list(
            TypeFormat.FLOAT32, 3,
            TypeFormat.FLOAT32, 2);


    public static void init() {
        Render.assertRenderThread();
        R.DEFAULT_PROGRAM = ShaderProgram.newInstance(
                ResourceUtil.loadModuleText(R.SHADERS_PATH + "simple_cam_vertex.glsl"),
                ResourceUtil.loadModuleText(R.SHADERS_PATH + "rgba_uv_fragment.glsl"));

        R.DEFAULT_PROGRAM.init();
        R.DEFAULT_PROGRAM.createUniform("texture_sampler");
        R.DEFAULT_PROGRAM.createUniform("projection_matrix");
        R.DEFAULT_PROGRAM.createUniform("model_view_matrix");
        R.DEFAULT_PROGRAM.createUniform("rgba");
        R.DEFAULT_PROGRAM.use();

        GL20.glUniform1i(R.DEFAULT_PROGRAM.getUniformLocation("texture_sampler"), 0);

        R.COORD_MESH = Mesh.create(R.defaultVertexAttribList);
        R.COORD_MESH.setData(0, MemorySegmentUtil.createFromFloatArray(
                -1,-1,-1,  100,-1,-1,
                -1,-1,-1, -1, 100,-1,
                -1,-1,-1, -1,-1,100));
        R.COORD_MESH.setData(1, MemorySegmentUtil.createFromFloatArray(0, 0, 0, 0,
                0.5f, 0, 0.5f, 0,
                0.9f, 0, 0.9f, 0));
        R.COORD_MESH.setIndices(MemorySegmentUtil.createFromIntArray(0, 1, 2, 3, 4, 5));

        // Init Coord VertexBuffer
        R.COORD_VB = VertexBuffer.create(R.defaultVertexAttribList);
        R.COORD_VB.getVao().initAndBind();

        R.QUAD_MESH = Mesh.create(R.defaultVertexAttribList);
        R.QUAD_MESH.setData(0, MemorySegmentUtil.createFromFloatArray(
                -1, 1, -1,  -1, -1, -1,  1, -1, -1,  1, 1, -1));
        R.QUAD_MESH.setData(1, MemorySegmentUtil.createFromFloatArray(0, 0, 0, 1, 1, 0, 1,1));
        R.QUAD_MESH.setIndices(MemorySegmentUtil.createFromIntArray(0, 1, 3, 3, 1, 2));

        // Init Quad VertexBuffer
        R.QUAD_VB = VertexBuffer.create(R.defaultVertexAttribList);
        R.QUAD_VB.getVao().initAndBind();

        var posVbo = R.COORD_VB.getVbo(0);
        posVbo.init();
        posVbo.reload(R.COORD_MESH);
        var uvVbo = R.COORD_VB.getVbo(1);
        uvVbo.init();
        uvVbo.reload(R.COORD_MESH);

        posVbo = R.QUAD_VB.getVbo(0);
        posVbo.init();
        posVbo.reload(R.QUAD_MESH);
        uvVbo = R.QUAD_VB.getVbo(1);
        uvVbo.init();
        uvVbo.reload(R.QUAD_MESH);
        Vbo.bindZero();
        Vao.bindZero();

        final Image img = Sugars.noCatch(()-> R.loadImage(Sugars.noCatch(()->ResourceUtil.getModuleResourceAsStream("textures/white.png"))));
        R.WHITE_TEXTURE = new Texture2D(TextureFormat.R8G8B8A8_UINT, img.w, img.h);
        R.WHITE_TEXTURE.init();
        R.WHITE_TEXTURE.reload(img.pixels);
        Texture2D.bindZero();
        ShaderProgram.useZero();
    }

    static record Image(int w, int h, MemorySegment pixels) {
    }
    public static Image loadImage(final InputStream inputStream) throws IOException {
        final var rawData = MemorySegmentUtil.createFromByteArray(IOUtils.toByteArray(inputStream));
        final var out = MemorySegmentUtil.createFromIntArray(0,0,0);
        final long data=rawData.address().toRawLongValue();
        final int dataSize= (int) rawData.byteSize();
        final long w = out.address().toRawLongValue();
        final long h = out.address().toRawLongValue()+MemoryLayouts.JAVA_INT.byteSize();
        final long c = out.address().toRawLongValue()+(MemoryLayouts.JAVA_INT.byteSize()*2);
        final var pixelsAddress = STBImage.nstbi_load_from_memory(data, dataSize, w,h,c ,4);

        final int imageWidth = MemoryAccess.getIntAtIndex(out, 0);
        final int imageHeight = MemoryAccess.getIntAtIndex(out, 1);
        final var imagePixels = MemoryAddress.ofLong(pixelsAddress).asSegment(imageWidth*imageHeight*4, ResourceScope.globalScope());
        return new Image(imageWidth, imageHeight, imagePixels);
    }
}
