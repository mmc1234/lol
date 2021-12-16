package com.github.mmc1234.lol.glfw.impl;

import com.github.mmc1234.lol.base.*;
import com.github.mmc1234.lol.glfw.*;
import com.github.mmc1234.lol.glfw.GLFW;
import com.github.mmc1234.lol.glfw.backend.*;
import jdk.incubator.foreign.*;
import org.lwjgl.glfw.*;

import java.nio.*;
import java.util.*;

public class ImplGLFWBackend implements GlfwBackend {
    @Override
    public int glfwGetWindowAttrib(Window window, int attrib) {
        return org.lwjgl.glfw.GLFW.glfwGetWindowAttrib(window.address(), attrib);
    }

    @Override
    public void glfwSetWindowAttrib(Window window, int attrib, int value) {
        org.lwjgl.glfw.GLFW.glfwSetWindowAttrib(window.address(),attrib,value);
    }

    @Override
    public void glfwSetWindowMonitor(Window window, Monitor monitor, int x, int y, int width, int height, int refreshRate) {
        org.lwjgl.glfw.GLFW.glfwSetWindowMonitor(window.address(), monitor.address(),x,y,width,height,refreshRate);
    }

    @Override
    public void glfwFocusWindow(Window window) {
        org.lwjgl.glfw.GLFW.glfwFocusWindow(window.address());
    }

    @Override
    public Monitor glfwGetWindowMonitor(Window window) {
        return Monitor.ofLong(org.lwjgl.glfw.GLFW.glfwGetWindowMonitor(window.address()));
    }

    @Override
    public void glfwHideWindow(Window window) {
        org.lwjgl.glfw.GLFW.glfwHideWindow(window.address());
    }

    @Override
    public void glfwIconifyWindow(Window window) {
        org.lwjgl.glfw.GLFW.glfwIconifyWindow(window.address());
    }

    @Override
    public boolean glfwInit() {
        return org.lwjgl.glfw.GLFW.glfwInit();
    }

    @Override
    public void glfwMaximizeWindow(Window window) {
        org.lwjgl.glfw.GLFW.glfwMaximizeWindow(window.address());
    }

    @Override
    public void glfwRequestWindowAttention(Window window) {
        org.lwjgl.glfw.GLFW.glfwRequestWindowAttention(window.address());
    }

    @Override
    public void glfwRestoreWindow(Window window) {
        org.lwjgl.glfw.GLFW.glfwRestoreWindow(window.address());
    }

    @Override
    public void glfwShowWindow(Window window) {
        org.lwjgl.glfw.GLFW.glfwShowWindow(window.address());
    }

    @Override
    public void glfwTerminate() {
        org.lwjgl.glfw.GLFW.glfwTerminate();
    }

    @Override
    public void glfwInitHint(int hint, int value) {
        org.lwjgl.glfw.GLFW.glfwInitHint(hint, value);
    }

    @Override
    public void glfwGetVersion(long majorOut, long minorOut, long revOut) {
        org.lwjgl.glfw.GLFW.nglfwGetVersion(majorOut, minorOut, revOut);
    }

    @Override
    public String glfwGetVersionString() {
        return org.lwjgl.glfw.GLFW.glfwGetVersionString();
    }

    @Override
    public int glfwGetError(ObjRef<String> description) {
        return 0;
    }

    @Override
    public void glfwSetErrorCallback(ErrorCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetErrorCallback((c,d)-> cb.error(c, GLFWErrorCallback.getDescription(d)));
    }

    @Override
    public Monitor[] glfwGetMonitors() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Monitor glfwGetPrimaryMonitor() {
        return Monitor.ofLong(org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor());
    }

    @Override
    public void glfwGetMonitorPos(Monitor monitor, long xOut, long yOut) {
        org.lwjgl.glfw.GLFW.nglfwGetMonitorPos(monitor.address(), xOut, yOut);
    }

    @Override
    public void glfwGetMonitorWorkarea(Monitor monitor, long xOut, long yOut, long wOut, long hOut) {
        org.lwjgl.glfw.GLFW.nglfwGetMonitorWorkarea(monitor.address(), xOut,yOut,wOut,hOut);
    }

    @Override
    public void glfwGetMonitorPhysicalSize(Monitor monitor, long wOut, long hOut) {
    }

    @Override
    public void glfwGetMonitorContentScale(Monitor monitor, long xScaleOut, long yScaleOut) {
        org.lwjgl.glfw.GLFW.nglfwGetMonitorContentScale(monitor.address(), xScaleOut, yScaleOut);
    }

    @Override
    public String glfwGetMonitorName(Monitor monitor) {
        return org.lwjgl.glfw.GLFW.glfwGetMonitorName(monitor.address());
    }

    @Override
    public void glfwSetMonitorUserPointer(Monitor monitor, MemoryAddress pointer) {
        org.lwjgl.glfw.GLFW.glfwSetMonitorUserPointer(monitor.address(), pointer.toRawLongValue());
    }

    @Override
    public MemoryAddress glfwGetMonitorUserPointer(Monitor monitor) {
        return MemoryAddress.ofLong(org.lwjgl.glfw.GLFW.glfwGetMonitorUserPointer(monitor.address()));
    }

    @Override
    public void glfwSetMonitorCallback(MonitorCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetMonitorCallback((m,c)-> cb.monitor(Monitor.ofLong(m), c == GLFW.GLFW_CONNECTED));
    }

    @Override
    public VideoMode[] glfwGetVideoModes(Monitor monitor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public VideoMode glfwGetVideoMode(Monitor monitor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void glfwSetGamma(Monitor monitor, float gamma) {
        org.lwjgl.glfw.GLFW.glfwSetGamma(monitor.address(), gamma);
    }

    @Override
    public GammaRamp glfwGetGammaRamp(Monitor monitor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void glfwSetGammaRamp(Monitor monitor, GammaRamp ramp) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void glfwDefaultWindowHints() {
        org.lwjgl.glfw.GLFW.glfwDefaultWindowHints();
    }

    @Override
    public void glfwWindowHint(int hint, int value) {
        org.lwjgl.glfw.GLFW.glfwWindowHint(hint, value);
    }

    @Override
    public void glfwWindowHintString(int hint, CharSequence value) {
        org.lwjgl.glfw.GLFW.glfwWindowHintString(hint, value);
    }

    @Override
    public Window glfwCreateWindow(int width, int height, CharSequence title, Monitor monitor, Window share) {
        return Window.ofLong(org.lwjgl.glfw.GLFW.glfwCreateWindow(width, height, title, monitor.address(), share.address()));
    }

    @Override
    public void glfwDestroyWindow(Window window) {
        org.lwjgl.glfw.GLFW.glfwDestroyWindow(window.address());
    }

    @Override
    public boolean glfwWindowShouldClose(Window window) {
        return org.lwjgl.glfw.GLFW.glfwWindowShouldClose(window.address());
    }

    @Override
    public void glfwSetWindowShouldClose(Window window, boolean value) {
        org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose(window.address(), value);
    }

    @Override
    public void glfwSetWindowTitle(Window window, CharSequence title) {
        org.lwjgl.glfw.GLFW.glfwSetWindowTitle(window.address(), title);
    }

    @Override
    public void glfwSetWindowIcon(Window window, MemorySegment images, int count) {
        org.lwjgl.glfw.GLFW.nglfwSetWindowIcon(window.address(), count, images.address().toRawLongValue());
    }

    @Override
    public void glfwGetWindowPos(Window window, long xOut, long yOut) {
        org.lwjgl.glfw.GLFW.nglfwGetWindowPos(window.address(), xOut, yOut);
    }

    @Override
    public void glfwSetWindowPos(Window window, int x, int y) {
        org.lwjgl.glfw.GLFW.glfwSetWindowPos(window.address(),x,y);
    }

    @Override
    public void glfwGetWindowSize(Window window, long xOut, long yOut) {
        org.lwjgl.glfw.GLFW.nglfwGetWindowSize(window.address(), xOut, yOut);
    }

    @Override
    public void glfwSetWindowSizeLimits(Window window, Int2 minSize, Int2 maxSize) {
        org.lwjgl.glfw.GLFW.glfwSetWindowSizeLimits(window.address(), minSize.x(), minSize.y(), maxSize.x(), maxSize.y());
    }

    @Override
    public void glfwSetWindowAspectRatio(Window window, int numer, int denom) {
        org.lwjgl.glfw.GLFW.glfwSetWindowAspectRatio(window.address(),numer,denom);
    }

    @Override
    public void glfwSetWindowSize(Window window, int width, int height) {
        org.lwjgl.glfw.GLFW.glfwSetWindowSize(window.address(), width,height);
    }

    @Override
    public void glfwGetFramebufferSize(Window window, long xOut, long yOut) {
        org.lwjgl.glfw.GLFW.nglfwGetFramebufferSize(window.address(), xOut, yOut);
    }

    @Override
    public IntRectangle glfwGetWindowFrameRect(Window window) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void glfwGetWindowContentScale(Window window, long xOut, long yOut) {
        org.lwjgl.glfw.GLFW.nglfwGetWindowContentScale(window.address(), xOut, yOut);
    }

    @Override
    public float glfwGetWindowOpacity(Window window) {
        return org.lwjgl.glfw.GLFW.glfwGetWindowOpacity(window.address());
    }

    @Override
    public void glfwSetWindowOpacity(Window window, float opacity) {
        org.lwjgl.glfw.GLFW.glfwSetWindowOpacity(window.address(),opacity);
    }

    @Override
    public void glfwSetWindowUserPointer(Window window, MemoryAddress pointer) {
        org.lwjgl.glfw.GLFW.glfwSetWindowUserPointer(window.address(), pointer.toRawLongValue());
    }

    @Override
    public MemoryAddress glfwGetWindowUserPointer(Window window) {
        return MemoryAddress.ofLong(org.lwjgl.glfw.GLFW.glfwGetWindowUserPointer(window.address()));
    }

    @Override
    public void glfwSetWindowPosCallback(Window window, WindowPosCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetWindowPosCallback(window.address(), (w,x,y)-> cb.pos(Window.ofLong(w),x,y));
    }

    @Override
    public void glfwSetWindowSizeCallback(Window window, WindowSizeCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback(window.address(), (w,x,y)-> cb.size(Window.ofLong(w),x,y));
    }

    @Override
    public void glfwSetWindowCloseCallback(Window window, WindowCloseCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetWindowCloseCallback(window.address(), Window::ofLong);
    }

    @Override
    public void glfwSetWindowRefreshCallback(Window window, WindowRefreshCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetWindowRefreshCallback(window.address(), Window::ofLong);
    }

    @Override
    public void glfwSetWindowFocusCallback(Window window, WindowFocusCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetWindowFocusCallback(window.address(), (w,f)-> cb.focus(Window.ofLong(w),f));
    }

    @Override
    public void glfwSetWindowIconifyCallback(Window window, WindowIconifyCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetWindowIconifyCallback(window.address(), (w,i)->cb.iconify(Window.ofLong(w),i));
    }

    @Override
    public void glfwSetWindowMaximizeCallback(Window window, WindowMaximizeCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetWindowMaximizeCallback(window.address(), (w,m)->cb.maximize(Window.ofLong(w),m));
    }

    @Override
    public void glfwSetFramebufferSizeCallback(Window window, FramebufferSizeCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback(window.address(), (w,x,y)-> cb.framebufferSize(Window.ofLong(w),x,y));
    }

    @Override
    public void glfwSetWindowContentScaleCallback(Window window, WindowContentScaleCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetWindowContentScaleCallback(window.address(), (w,x,y)-> cb.windowContentScale(Window.ofLong(w),x,y));
    }

    @Override
    public void glfwPollEvents() {
        org.lwjgl.glfw.GLFW.glfwPollEvents();
    }

    @Override
    public void glfwWaitEvents() {
        org.lwjgl.glfw.GLFW.glfwWaitEvents();
    }

    @Override
    public void glfwWaitEventsTimeout(double timeout) {
        org.lwjgl.glfw.GLFW.glfwWaitEventsTimeout(timeout);
    }

    @Override
    public void glfwPostEmptyEvent() {
        org.lwjgl.glfw.GLFW.glfwPostEmptyEvent();
    }

    @Override
    public int glfwGetInputMode(Window window, int mode) {
        return org.lwjgl.glfw.GLFW.glfwGetInputMode(window.address(),mode);
    }

    @Override
    public void glfwSetInputMode(Window window, int mode, int value) {
        org.lwjgl.glfw.GLFW.glfwSetInputMode(window.address(), mode, value);
    }

    @Override
    public boolean glfwRawMouseMotionSupported() {
        return org.lwjgl.glfw.GLFW.glfwRawMouseMotionSupported();
    }

    @Override
    public String glfwGetKeyName(int key, int scancode) {
        return org.lwjgl.glfw.GLFW.glfwGetKeyName(key,scancode);
    }

    @Override
    public int glfwGetKeyScancode(int key) {
        return org.lwjgl.glfw.GLFW.glfwGetKeyScancode(key);
    }

    @Override
    public int glfwGetKey(Window window, int key) {
        return org.lwjgl.glfw.GLFW.glfwGetKey(window.address(), key);
    }

    @Override
    public int glfwGetMouseButton(Window window, int button) {
        return org.lwjgl.glfw.GLFW.glfwGetMouseButton(window.address(),button);
    }

    @Override
    public Double2 glfwGetCursorPos(Window window) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void glfwSetCursorPos(Window window, double x, double y) {
        org.lwjgl.glfw.GLFW.glfwSetCursorPos(window.address(),x,y);
    }

    @Override
    public Cursor glfwCreateCursor(MemorySegment image, int xHot, int yHot) {
        return Cursor.ofLong(org.lwjgl.glfw.GLFW.nglfwCreateCursor(image.address().toRawLongValue(), xHot,yHot));
    }

    @Override
    public Cursor glfwCreateStandardCursor(int shape) {
        return Cursor.ofLong(org.lwjgl.glfw.GLFW.glfwCreateStandardCursor(shape));
    }

    @Override
    public void glfwDestroyCursor(Cursor cursor) {
        org.lwjgl.glfw.GLFW.glfwDestroyWindow(cursor.address());
    }

    @Override
    public void glfwSetCursor(Window window, Cursor cursor) {
        org.lwjgl.glfw.GLFW.glfwSetCursor(window.address(), cursor.address());
    }

    @Override
    public void glfwSetKeyCallback(Window window, KeyCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetKeyCallback(window.address(), (l, i, i1, i2, i3) -> cb.key(Window.ofLong(l), i,i1,i2,i3));
    }

    @Override
    public void glfwSetCharCallback(Window window, CharCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetCharCallback(window.address(), (w,cp)->cb.ch(Window.ofLong(w), cp));
    }

    @Override
    public void glfwSetCharModsCallback(Window window, CharModsCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetCharModsCallback(window.address(), (w,cp,m)->cb.charMods(Window.ofLong(w), cp, m));
    }

    @Override
    public void glfwSetMouseButtonCallback(Window window, MouseButtonCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback(window.address(), (l, i, i1, i2) -> cb.mouseButton(Window.ofLong(l), i,i1 == GLFW.GLFW_TRUE,i2));
    }

    @Override
    public void glfwSetCursorPosCallback(Window window, CursorPosCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback(window.address(), (w,x,y)->cb.cursorPos(Window.ofLong(w), x,y));
    }

    @Override
    public void glfwSetCursorEnterCallback(Window window, CursorEnterCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetCursorEnterCallback(window.address(), (w,e)->cb.cursorEnter(Window.ofLong(w),e));
    }

    @Override
    public void glfwSetScrollCallback(Window window, ScrollCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetScrollCallback(window.address(), (w,x,y)->cb.scroll(Window.ofLong(w), x,y));
    }

    @Override
    public void glfwSetDropCallback(Window window, DropCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetDropCallback(window.address(), (w, c, n) -> {
            String[] pathArray = new String[c];
            for(int i = 0;i<c;i++) {
                pathArray[i] = GLFWDropCallback.getName(n,i);
            }
            cb.drop(Window.ofLong(w), List.of(pathArray));
        });
    }

    @Override
    public boolean glfwJoystickPresent(int jid) {
        return org.lwjgl.glfw.GLFW.glfwJoystickPresent(jid);
    }

    @Override
    public FloatBuffer glfwGetJoystickAxes(int jid) {
        return org.lwjgl.glfw.GLFW.glfwGetJoystickAxes(jid);
    }

    @Override
    public ByteBuffer glfwGetJoystickButtons(int jid) {
        return org.lwjgl.glfw.GLFW.glfwGetJoystickButtons(jid);
    }

    @Override
    public ByteBuffer glfwGetJoystickHats(int jid) {
        return org.lwjgl.glfw.GLFW.glfwGetJoystickHats(jid);
    }

    @Override
    public String glfwGetJoystickName(int jid) {
        return org.lwjgl.glfw.GLFW.glfwGetJoystickName(jid);
    }

    @Override
    public String glfwGetJoystickGUID(int jid) {
        return org.lwjgl.glfw.GLFW.glfwGetJoystickGUID(jid);
    }

    @Override
    public void glfwSetJoystickUserPointer(int jid, MemoryAddress pointer) {
        org.lwjgl.glfw.GLFW.glfwSetJoystickUserPointer(jid, pointer.toRawLongValue());
    }

    @Override
    public MemoryAddress glfwGetJoystickUserPointer(int jid) {
        return MemoryAddress.ofLong(org.lwjgl.glfw.GLFW.glfwGetJoystickUserPointer(jid));
    }

    @Override
    public boolean glfwJoystickIsGamepad(int jid) {
        return org.lwjgl.glfw.GLFW.glfwJoystickIsGamepad(jid);
    }

    @Override
    public void glfwSetJoystickCallback(JoystickCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetJoystickCallback((j,c)->cb.joystick(j,c==GLFW.GLFW_CONNECTED));
    }

    @Override
    public boolean glfwUpdateGamepadMappings(MemoryAddress string) {
        return GLFW.GLFW_TRUE == org.lwjgl.glfw.GLFW.nglfwUpdateGamepadMappings(string.toRawLongValue());
    }

    @Override
    public String glfwGetGamepadName(int jid) {
        return org.lwjgl.glfw.GLFW.glfwGetGamepadName(jid);
    }

    @Override
    public boolean glfwGetGamepadState(int jid, GamepadState state) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void glfwSetClipboardString(Window window, CharSequence string) {
        org.lwjgl.glfw.GLFW.glfwSetClipboardString(window.address(),string);
    }

    @Override
    public String glfwGetClipboardString(Window window) {
        return org.lwjgl.glfw.GLFW.glfwGetClipboardString(window.address());
    }

    @Override
    public double glfwGetTime() {
        return org.lwjgl.glfw.GLFW.glfwGetTime();
    }

    @Override
    public void glfwSetTime(double time) {
        org.lwjgl.glfw.GLFW.glfwSetTime(time);
    }

    @Override
    public long glfwGetTimerValue() {
        return org.lwjgl.glfw.GLFW.glfwGetTimerValue();
    }

    @Override
    public long glfwGetTimerFrequency() {
        return org.lwjgl.glfw.GLFW.glfwGetTimerFrequency();
    }

    @Override
    public void glfwMakeContextCurrent(Window window) {
        org.lwjgl.glfw.GLFW.glfwMakeContextCurrent(window.address());
    }

    @Override
    public Window glfwGetCurrentContext() {
        return Window.ofLong(org.lwjgl.glfw.GLFW.glfwGetCurrentContext());
    }

    @Override
    public void glfwSwapBuffers(Window window) {
        org.lwjgl.glfw.GLFW.glfwSwapBuffers(window.address());
    }

    @Override
    public void glfwSwapInterval(int interval) {
        org.lwjgl.glfw.GLFW.glfwSwapInterval(interval);
    }

    @Override
    public boolean glfwExtensionSupported(CharSequence extension) {
        return org.lwjgl.glfw.GLFW.glfwExtensionSupported(extension);
    }

    @Override
    public MemoryAddress glfwGetProcAddress(CharSequence procName) {
        return MemoryAddress.ofLong(org.lwjgl.glfw.GLFW.glfwGetProcAddress(procName));
    }
}
