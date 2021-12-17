package com.github.mmc1234.lol.p1.core;

import com.github.mmc1234.lol.glfw.*;

public interface App extends Runnable {
    void onLogicStart();

    void onRenderStart();

    void onLogicStop();

    void onRenderStop();

    void onLogic();

    void onBeforeRender();

    void onRender();

    void onAfterRender();
}
