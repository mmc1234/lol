package com.github.mmc1234.lol.renderbranch.v1;

public interface App extends Runnable {
    void onLogicStart();

    void onRenderStart();

    void onLogicStop();

    void onRenderStop();

    void onLogic();

    void onBeforeRender();

    void onRender();

    void onAfterRender();

    void onWindow();
}
