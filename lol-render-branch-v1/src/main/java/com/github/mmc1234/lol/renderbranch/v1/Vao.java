package com.github.mmc1234.lol.renderbranch.v1;

import org.lwjgl.opengl.*;

public class Vao {
    private int vao;

    public void init() {
        if(vao == 0) {
            vao = GL33.glGenVertexArrays();
        }
    }

    public void close() {
        if(vao != 0) {
            GL33.glDeleteVertexArrays(vao);
            vao = 0;
        }
    }

    public int getVao() {
        return vao;
    }
}
