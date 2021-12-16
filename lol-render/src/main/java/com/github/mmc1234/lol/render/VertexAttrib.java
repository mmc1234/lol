package com.github.mmc1234.lol.render;

public class VertexAttrib {
    private String name;
    private VertexAttrib(String name) {
        this.name = name;
    }
    public static final VertexAttrib POSITION = new VertexAttrib("position");
    public static final VertexAttrib NORMAL=  new VertexAttrib("normal");
    public static final VertexAttrib UV = new VertexAttrib("uv");
    public static final VertexAttrib COLOR = new VertexAttrib("color");
}
