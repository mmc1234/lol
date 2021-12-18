package com.github.mmc1234.lol.renderbranch.v1;

import com.github.mmc1234.lol.glfw.*;
import jdk.incubator.foreign.*;
import org.joml.*;
import org.lwjgl.opengl.*;

public class WindowHandler implements CursorPosCallback, CursorEnterCallback, MouseButtonCallback, WindowFocusCallback, FramebufferSizeCallback, WindowSizeCallback {
    boolean enter, focus;
    double mouseX, mouseY;
    int width, height;
    int frameBufferWidth, frameBufferHeight;
    Window window = Window.EMPTY;
    private MemorySegment tmp;

    public final Vector2d previousPos;

    public final Vector2d currentPos;

    public final Vector2f displVec;

    public Vector2f getDisplVec() {
        return displVec;
    }

    public void destroy() {
        GLFW.glfwDestroyWindow(window);
        window = Window.EMPTY;
    }

    public WindowHandler() {
        previousPos = new Vector2d(-1, -1);
        currentPos = new Vector2d(0, 0);
        displVec = new Vector2f();
        tmp = MemorySegment.allocateNative(4 * 4, ResourceScope.globalScope());
    }

    @Override
    public void framebufferSize(Window window, int w, int h) {
        frameBufferWidth = w;
        frameBufferHeight = h;
    }

    private long getRawLongValueAtIndex(int index) {
        return tmp.address().toRawLongValue() + 4 * index;
    }

    private int getIntAtIndex(int index) {
        return MemoryAccess.getIntAtIndex(tmp, index);
    }

    public void init() {
        if (Window.EMPTY.equals(window)) {
            window = GLFW.glfwCreateWindow(640, 480, "Title", Monitor.EMPTY, Window.EMPTY);
            GLFW.glfwSetCursorPosCallback(window, this);
            GLFW.glfwSetCursorEnterCallback(window, this);
            GLFW.glfwSetMouseButtonCallback(window, this);
            GLFW.glfwSetWindowFocusCallback(window, this);
            GLFW.glfwSetWindowSizeCallback(window, this);
            GLFW.glfwSetFramebufferSizeCallback(window, this);

            setFocus(GLFW.GLFW_TRUE == GLFW.glfwGetWindowAttrib(window, GLFW.GLFW_FOCUSED));
            setEnter(GLFW.GLFW_TRUE == GLFW.glfwGetWindowAttrib(window, GLFW.GLFW_HOVERED));
            GLFW.glfwGetWindowPos(window, getRawLongValueAtIndex(0), getRawLongValueAtIndex(1));
            setMousePos(MemoryAccess.getIntAtIndex(tmp, 0), MemoryAccess.getIntAtIndex(tmp, 1));

            GLFW.glfwGetWindowSize(window, getRawLongValueAtIndex(0), getRawLongValueAtIndex(1));
            setSize(MemoryAccess.getIntAtIndex(tmp, 0), MemoryAccess.getIntAtIndex(tmp, 1));

            GLFW.glfwGetFramebufferSize(window, getRawLongValueAtIndex(0), getRawLongValueAtIndex(1));
            setFrameBufferSize(MemoryAccess.getIntAtIndex(tmp, 0), MemoryAccess.getIntAtIndex(tmp, 1));

            GLFW.glfwMakeContextCurrent(getWindow());
            GLFW.glfwSwapInterval(0);
            GL.createCapabilities();
        }
    }

    private void setEnter(boolean enter) {
        this.enter = enter;
    }

    private void setFocus(boolean focus) {
        this.focus = focus;
    }

    public boolean isEnter() {
        return enter;
    }

    public boolean isFocus() {
        return focus;
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public Window getWindow() {
        return window;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getFrameBufferHeight() {
        return frameBufferHeight;
    }

    public int getFrameBufferWidth() {
        return frameBufferWidth;
    }

    private void setSize(int w, int h) {
        this.width = w;
        this.height = h;
    }

    private void setFrameBufferSize(int w, int h) {
        this.frameBufferWidth = w;
        this.frameBufferHeight = h;
    }

    private void setMousePos(double x, double y) {
        this.mouseX = x;
        this.mouseY = y;
        currentPos.x = x;
        currentPos.y = y;
    }

    @Override
    public void cursorEnter(Window window, boolean entered) {
        setEnter(entered);
    }

    @Override
    public void cursorPos(Window window, double x, double y) {
        setMousePos(x, y);
    }

    @Override
    public void focus(Window window, boolean focused) {
        setFocus(focused);
    }

    @Override
    public void mouseButton(Window window, int button, boolean isPress, int mods) {
        // System.out.println(toString()+".  button="+button+", isPress="+isPress+", mods="+mods);
    }

    @Override
    public void size(Window window, int w, int h) {
        width = w;
        height = h;
    }

    @Override
    public String toString() {
        return "enter=" + enter +
                ", focus=" + focus +
                ", mousePos=(" + mouseX + "," + mouseY + ")" +
                ", size=(" + width + "," + height + ")" +
                ", frameBufferSize=(" + frameBufferWidth + "," + frameBufferHeight + ")";
    }
}
