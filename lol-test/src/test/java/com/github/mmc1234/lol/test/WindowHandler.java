package com.github.mmc1234.lol.test;

import com.github.mmc1234.lol.glfw.*;
import jdk.incubator.foreign.*;
import org.lwjgl.system.*;

import static com.github.mmc1234.lol.glfw.GLFW.*;

public class WindowHandler implements CursorPosCallback, CursorEnterCallback, MouseButtonCallback, WindowFocusCallback, FramebufferSizeCallback, WindowSizeCallback {
    boolean enter, focus;
    double mouseX, mouseY;
    int width, height;
    int frameBufferWidth, frameBufferHeight;
    Window window = Window.EMPTY;
    private MemorySegment tmp;

    public WindowHandler() {
        tmp = MemorySegment.allocateNative(4*4, ResourceScope.globalScope());
    }

    @Override
    public void framebufferSize(Window window, int w, int h) {
        frameBufferWidth = w;
        frameBufferHeight = h;
    }

    private long getRawLongValueAtIndex(int index) {
        return tmp.address().toRawLongValue()+4*index;
    }

    private int getIntAtIndex(int index) {
        return MemoryAccess.getIntAtIndex(tmp, index);
    }

    public void init() {
        if(Window.EMPTY.equals(window)) {
            window = glfwCreateWindow(800, 640, "Title", Monitor.EMPTY, Window.EMPTY);
            glfwSetCursorPosCallback(window, this);
            glfwSetCursorEnterCallback(window, this);
            glfwSetMouseButtonCallback(window, this);
            glfwSetWindowFocusCallback(window, this);
            glfwSetWindowSizeCallback(window, this);
            glfwSetFramebufferSizeCallback(window, this);

            setFocus(GLFW_TRUE == glfwGetWindowAttrib(window, GLFW_FOCUSED));
            setEnter(GLFW_TRUE == glfwGetWindowAttrib(window, GLFW_HOVERED));
            glfwGetWindowPos(window, getRawLongValueAtIndex(0), getRawLongValueAtIndex(1));
            setMousePos(MemoryAccess.getIntAtIndex(tmp,0), MemoryAccess.getIntAtIndex(tmp,1));

            glfwGetWindowSize(window, getRawLongValueAtIndex(0), getRawLongValueAtIndex(1));
            setSize(MemoryAccess.getIntAtIndex(tmp,0), MemoryAccess.getIntAtIndex(tmp,1));

            glfwGetFramebufferSize(window, getRawLongValueAtIndex(0), getRawLongValueAtIndex(1));
            setFrameBufferSize(MemoryAccess.getIntAtIndex(tmp,0), MemoryAccess.getIntAtIndex(tmp,1));
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
    }

    @Override
    public void cursorEnter(Window window, boolean entered) {
        setEnter(entered);
    }

    @Override
    public void cursorPos(Window window, double x, double y) {
        setMousePos(x,y);
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
        return  "enter=" + enter +
                ", focus=" + focus +
                ", mousePos=(" + mouseX+","+mouseY+")"+
                ", size=(" + width+","+height+")"+
                ", frameBufferSize=(" + frameBufferWidth+","+frameBufferHeight+")";
    }
}
