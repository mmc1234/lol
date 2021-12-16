package com.github.mmc1234.lol.render;

public interface Camera {
    Camera createRenderSnapshot();
    Camera createAudioSnapshot();
    boolean isSnapshot();
}
