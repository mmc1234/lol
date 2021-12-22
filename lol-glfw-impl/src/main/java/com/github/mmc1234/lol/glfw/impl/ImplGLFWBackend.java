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
    public int glfwGetWindowAttrib(final Window window, final int attrib) {
        return org.lwjgl.glfw.GLFW.glfwGetWindowAttrib(window.address(), attrib);
    }

    @Override
    public void glfwSetWindowAttrib(final Window window, final int attrib, final int value) {
        org.lwjgl.glfw.GLFW.glfwSetWindowAttrib(window.address(), attrib, value);
    }

    @Override
    public void glfwSetWindowMonitor(final Window window, final Monitor monitor, final int x, final int y, final int width, final int height, final int refreshRate) {
        org.lwjgl.glfw.GLFW.glfwSetWindowMonitor(window.address(), monitor.address(), x, y, width, height, refreshRate);
    }

    @Override
    public void glfwFocusWindow(final Window window) {
        org.lwjgl.glfw.GLFW.glfwFocusWindow(window.address());
    }

    @Override
    public Monitor glfwGetWindowMonitor(final Window window) {
        return Monitor.ofLong(org.lwjgl.glfw.GLFW.glfwGetWindowMonitor(window.address()));
    }

    @Override
    public void glfwHideWindow(final Window window) {
        org.lwjgl.glfw.GLFW.glfwHideWindow(window.address());
    }

    @Override
    public void glfwIconifyWindow(final Window window) {
        org.lwjgl.glfw.GLFW.glfwIconifyWindow(window.address());
    }

    @Override
    public boolean glfwInit() {
        return org.lwjgl.glfw.GLFW.glfwInit();
    }

    @Override
    public void glfwMaximizeWindow(final Window window) {
        org.lwjgl.glfw.GLFW.glfwMaximizeWindow(window.address());
    }

    @Override
    public void glfwRequestWindowAttention(final Window window) {
        org.lwjgl.glfw.GLFW.glfwRequestWindowAttention(window.address());
    }

    @Override
    public void glfwRestoreWindow(final Window window) {
        org.lwjgl.glfw.GLFW.glfwRestoreWindow(window.address());
    }

    @Override
    public void glfwShowWindow(final Window window) {
        org.lwjgl.glfw.GLFW.glfwShowWindow(window.address());
    }

    @Override
    public void glfwTerminate() {
        org.lwjgl.glfw.GLFW.glfwTerminate();
    }

    @Override
    public void glfwInitHint(final int hint, final int value) {
        org.lwjgl.glfw.GLFW.glfwInitHint(hint, value);
    }

    @Override
    public void glfwGetVersion(final long majorOut, final long minorOut, final long revOut) {
        org.lwjgl.glfw.GLFW.nglfwGetVersion(majorOut, minorOut, revOut);
    }

    @Override
    public String glfwGetVersionString() {
        return org.lwjgl.glfw.GLFW.glfwGetVersionString();
    }

    @Override
    public int glfwGetError(final ObjRef<String> description) {
        return 0;
    }

    @Override
    public void glfwSetErrorCallback(final ErrorCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetErrorCallback((c, d) -> cb.error(c, GLFWErrorCallback.getDescription(d)));
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
    public void glfwGetMonitorPos(final Monitor monitor, final long xOut, final long yOut) {
        org.lwjgl.glfw.GLFW.nglfwGetMonitorPos(monitor.address(), xOut, yOut);
    }

    @Override
    public void glfwGetMonitorWorkarea(final Monitor monitor, final long xOut, final long yOut, final long wOut, final long hOut) {
        org.lwjgl.glfw.GLFW.nglfwGetMonitorWorkarea(monitor.address(), xOut, yOut, wOut, hOut);
    }

    @Override
    public void glfwGetMonitorPhysicalSize(final Monitor monitor, final long wOut, final long hOut) {
    }

    @Override
    public void glfwGetMonitorContentScale(final Monitor monitor, final long xScaleOut, final long yScaleOut) {
        org.lwjgl.glfw.GLFW.nglfwGetMonitorContentScale(monitor.address(), xScaleOut, yScaleOut);
    }

    @Override
    public String glfwGetMonitorName(final Monitor monitor) {
        return org.lwjgl.glfw.GLFW.glfwGetMonitorName(monitor.address());
    }

    @Override
    public void glfwSetMonitorUserPointer(final Monitor monitor, final MemoryAddress pointer) {
        org.lwjgl.glfw.GLFW.glfwSetMonitorUserPointer(monitor.address(), pointer.toRawLongValue());
    }

    @Override
    public MemoryAddress glfwGetMonitorUserPointer(final Monitor monitor) {
        return MemoryAddress.ofLong(org.lwjgl.glfw.GLFW.glfwGetMonitorUserPointer(monitor.address()));
    }

    @Override
    public void glfwSetMonitorCallback(final MonitorCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetMonitorCallback((m, c) -> cb.monitor(Monitor.ofLong(m), c == GLFW.GLFW_CONNECTED));
    }

    @Override
    public VideoMode[] glfwGetVideoModes(final Monitor monitor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public VideoMode glfwGetVideoMode(final Monitor monitor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void glfwSetGamma(final Monitor monitor, final float gamma) {
        org.lwjgl.glfw.GLFW.glfwSetGamma(monitor.address(), gamma);
    }

    @Override
    public GammaRamp glfwGetGammaRamp(final Monitor monitor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void glfwSetGammaRamp(final Monitor monitor, final GammaRamp ramp) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void glfwDefaultWindowHints() {
        org.lwjgl.glfw.GLFW.glfwDefaultWindowHints();
    }

    @Override
    public void glfwWindowHint(final int hint, final int value) {
        org.lwjgl.glfw.GLFW.glfwWindowHint(hint, value);
    }

    @Override
    public void glfwWindowHintString(final int hint, final CharSequence value) {
        org.lwjgl.glfw.GLFW.glfwWindowHintString(hint, value);
    }

    @Override
    public Window glfwCreateWindow(final int width, final int height, final CharSequence title, final Monitor monitor, final Window share) {
        return Window.ofLong(org.lwjgl.glfw.GLFW.glfwCreateWindow(width, height, title, monitor.address(), share.address()));
    }

    @Override
    public void glfwDestroyWindow(final Window window) {
        org.lwjgl.glfw.GLFW.glfwDestroyWindow(window.address());
    }

    @Override
    public boolean glfwWindowShouldClose(final Window window) {
        return org.lwjgl.glfw.GLFW.glfwWindowShouldClose(window.address());
    }

    @Override
    public void glfwSetWindowShouldClose(final Window window, final boolean value) {
        org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose(window.address(), value);
    }

    @Override
    public void glfwSetWindowTitle(final Window window, final CharSequence title) {
        org.lwjgl.glfw.GLFW.glfwSetWindowTitle(window.address(), title);
    }

    @Override
    public void glfwSetWindowIcon(final Window window, final MemorySegment images, final int count) {
        org.lwjgl.glfw.GLFW.nglfwSetWindowIcon(window.address(), count, images.address().toRawLongValue());
    }

    @Override
    public void glfwGetWindowPos(final Window window, final long xOut, final long yOut) {
        org.lwjgl.glfw.GLFW.nglfwGetWindowPos(window.address(), xOut, yOut);
    }

    @Override
    public void glfwSetWindowPos(final Window window, final int x, final int y) {
        org.lwjgl.glfw.GLFW.glfwSetWindowPos(window.address(), x, y);
    }

    @Override
    public void glfwGetWindowSize(final Window window, final long xOut, final long yOut) {
        org.lwjgl.glfw.GLFW.nglfwGetWindowSize(window.address(), xOut, yOut);
    }

    @Override
    public void glfwSetWindowSizeLimits(final Window window, final Int2 minSize, final Int2 maxSize) {
        org.lwjgl.glfw.GLFW.glfwSetWindowSizeLimits(window.address(), minSize.x(), minSize.y(), maxSize.x(), maxSize.y());
    }

    @Override
    public void glfwSetWindowAspectRatio(final Window window, final int numer, final int denom) {
        org.lwjgl.glfw.GLFW.glfwSetWindowAspectRatio(window.address(), numer, denom);
    }

    @Override
    public void glfwSetWindowSize(final Window window, final int width, final int height) {
        org.lwjgl.glfw.GLFW.glfwSetWindowSize(window.address(), width, height);
    }

    @Override
    public void glfwGetFramebufferSize(final Window window, final long xOut, final long yOut) {
        org.lwjgl.glfw.GLFW.nglfwGetFramebufferSize(window.address(), xOut, yOut);
    }

    @Override
    public IntRectangle glfwGetWindowFrameRect(final Window window) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void glfwGetWindowContentScale(final Window window, final long xOut, final long yOut) {
        org.lwjgl.glfw.GLFW.nglfwGetWindowContentScale(window.address(), xOut, yOut);
    }

    @Override
    public float glfwGetWindowOpacity(final Window window) {
        return org.lwjgl.glfw.GLFW.glfwGetWindowOpacity(window.address());
    }

    @Override
    public void glfwSetWindowOpacity(final Window window, final float opacity) {
        org.lwjgl.glfw.GLFW.glfwSetWindowOpacity(window.address(), opacity);
    }

    @Override
    public void glfwSetWindowUserPointer(final Window window, final MemoryAddress pointer) {
        org.lwjgl.glfw.GLFW.glfwSetWindowUserPointer(window.address(), pointer.toRawLongValue());
    }

    @Override
    public MemoryAddress glfwGetWindowUserPointer(final Window window) {
        return MemoryAddress.ofLong(org.lwjgl.glfw.GLFW.glfwGetWindowUserPointer(window.address()));
    }

    @Override
    public void glfwSetWindowPosCallback(final Window window, final WindowPosCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetWindowPosCallback(window.address(), (w, x, y) -> cb.pos(Window.ofLong(w), x, y));
    }

    @Override
    public void glfwSetWindowSizeCallback(final Window window, final WindowSizeCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback(window.address(), (w, x, y) -> cb.size(Window.ofLong(w), x, y));
    }

    @Override
    public void glfwSetWindowCloseCallback(final Window window, final WindowCloseCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetWindowCloseCallback(window.address(), Window::ofLong);
    }

    @Override
    public void glfwSetWindowRefreshCallback(final Window window, final WindowRefreshCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetWindowRefreshCallback(window.address(), Window::ofLong);
    }

    @Override
    public void glfwSetWindowFocusCallback(final Window window, final WindowFocusCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetWindowFocusCallback(window.address(), (w, f) -> cb.focus(Window.ofLong(w), f));
    }

    @Override
    public void glfwSetWindowIconifyCallback(final Window window, final WindowIconifyCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetWindowIconifyCallback(window.address(), (w, i) -> cb.iconify(Window.ofLong(w), i));
    }

    @Override
    public void glfwSetWindowMaximizeCallback(final Window window, final WindowMaximizeCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetWindowMaximizeCallback(window.address(), (w, m) -> cb.maximize(Window.ofLong(w), m));
    }

    @Override
    public void glfwSetFramebufferSizeCallback(final Window window, final FramebufferSizeCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback(window.address(), (w, x, y) -> cb.framebufferSize(Window.ofLong(w), x, y));
    }

    @Override
    public void glfwSetWindowContentScaleCallback(final Window window, final WindowContentScaleCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetWindowContentScaleCallback(window.address(), (w, x, y) -> cb.windowContentScale(Window.ofLong(w), x, y));
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
    public void glfwWaitEventsTimeout(final double timeout) {
        org.lwjgl.glfw.GLFW.glfwWaitEventsTimeout(timeout);
    }

    @Override
    public void glfwPostEmptyEvent() {
        org.lwjgl.glfw.GLFW.glfwPostEmptyEvent();
    }

    @Override
    public int glfwGetInputMode(final Window window, final int mode) {
        return org.lwjgl.glfw.GLFW.glfwGetInputMode(window.address(), mode);
    }

    @Override
    public void glfwSetInputMode(final Window window, final int mode, final int value) {
        org.lwjgl.glfw.GLFW.glfwSetInputMode(window.address(), mode, value);
    }

    @Override
    public boolean glfwRawMouseMotionSupported() {
        return org.lwjgl.glfw.GLFW.glfwRawMouseMotionSupported();
    }

    @Override
    public String glfwGetKeyName(final int key, final int scancode) {
        return org.lwjgl.glfw.GLFW.glfwGetKeyName(key, scancode);
    }

    @Override
    public int glfwGetKeyScancode(final int key) {
        return org.lwjgl.glfw.GLFW.glfwGetKeyScancode(key);
    }

    @Override
    public int glfwGetKey(final Window window, final int key) {
        return org.lwjgl.glfw.GLFW.glfwGetKey(window.address(), key);
    }

    @Override
    public int glfwGetMouseButton(final Window window, final int button) {
        return org.lwjgl.glfw.GLFW.glfwGetMouseButton(window.address(), button);
    }

    @Override
    public Double2 glfwGetCursorPos(final Window window) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void glfwSetCursorPos(final Window window, final double x, final double y) {
        org.lwjgl.glfw.GLFW.glfwSetCursorPos(window.address(), x, y);
    }

    @Override
    public Cursor glfwCreateCursor(final MemorySegment image, final int xHot, final int yHot) {
        return Cursor.ofLong(org.lwjgl.glfw.GLFW.nglfwCreateCursor(image.address().toRawLongValue(), xHot, yHot));
    }

    @Override
    public Cursor glfwCreateStandardCursor(final int shape) {
        return Cursor.ofLong(org.lwjgl.glfw.GLFW.glfwCreateStandardCursor(shape));
    }

    @Override
    public void glfwDestroyCursor(final Cursor cursor) {
        org.lwjgl.glfw.GLFW.glfwDestroyWindow(cursor.address());
    }

    @Override
    public void glfwSetCursor(final Window window, final Cursor cursor) {
        org.lwjgl.glfw.GLFW.glfwSetCursor(window.address(), cursor.address());
    }

    @Override
    public void glfwSetKeyCallback(final Window window, final KeyCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetKeyCallback(window.address(), (l, i, i1, i2, i3) -> cb.key(Window.ofLong(l), i, i1, i2, i3));
    }

    @Override
    public void glfwSetCharCallback(final Window window, final CharCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetCharCallback(window.address(), (w, cp) -> cb.ch(Window.ofLong(w), cp));
    }

    @Override
    public void glfwSetCharModsCallback(final Window window, final CharModsCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetCharModsCallback(window.address(), (w, cp, m) -> cb.charMods(Window.ofLong(w), cp, m));
    }

    @Override
    public void glfwSetMouseButtonCallback(final Window window, final MouseButtonCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback(window.address(), (l, i, i1, i2) -> cb.mouseButton(Window.ofLong(l), i, i1 == GLFW.GLFW_TRUE, i2));
    }

    @Override
    public void glfwSetCursorPosCallback(final Window window, final CursorPosCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback(window.address(), (w, x, y) -> cb.cursorPos(Window.ofLong(w), x, y));
    }

    @Override
    public void glfwSetCursorEnterCallback(final Window window, final CursorEnterCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetCursorEnterCallback(window.address(), (w, e) -> cb.cursorEnter(Window.ofLong(w), e));
    }

    @Override
    public void glfwSetScrollCallback(final Window window, final ScrollCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetScrollCallback(window.address(), (w, x, y) -> cb.scroll(Window.ofLong(w), x, y));
    }

    @Override
    public void glfwSetDropCallback(final Window window, final DropCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetDropCallback(window.address(), (w, c, n) -> {
            final String[] pathArray = new String[c];
            for (int i = 0; i < c; i++) {
                pathArray[i] = GLFWDropCallback.getName(n, i);
            }
            cb.drop(Window.ofLong(w), List.of(pathArray));
        });
    }

    @Override
    public boolean glfwJoystickPresent(final int jid) {
        return org.lwjgl.glfw.GLFW.glfwJoystickPresent(jid);
    }

    @Override
    public FloatBuffer glfwGetJoystickAxes(final int jid) {
        return org.lwjgl.glfw.GLFW.glfwGetJoystickAxes(jid);
    }

    @Override
    public ByteBuffer glfwGetJoystickButtons(final int jid) {
        return org.lwjgl.glfw.GLFW.glfwGetJoystickButtons(jid);
    }

    @Override
    public ByteBuffer glfwGetJoystickHats(final int jid) {
        return org.lwjgl.glfw.GLFW.glfwGetJoystickHats(jid);
    }

    @Override
    public String glfwGetJoystickName(final int jid) {
        return org.lwjgl.glfw.GLFW.glfwGetJoystickName(jid);
    }

    @Override
    public String glfwGetJoystickGUID(final int jid) {
        return org.lwjgl.glfw.GLFW.glfwGetJoystickGUID(jid);
    }

    @Override
    public void glfwSetJoystickUserPointer(final int jid, final MemoryAddress pointer) {
        org.lwjgl.glfw.GLFW.glfwSetJoystickUserPointer(jid, pointer.toRawLongValue());
    }

    @Override
    public MemoryAddress glfwGetJoystickUserPointer(final int jid) {
        return MemoryAddress.ofLong(org.lwjgl.glfw.GLFW.glfwGetJoystickUserPointer(jid));
    }

    @Override
    public boolean glfwJoystickIsGamepad(final int jid) {
        return org.lwjgl.glfw.GLFW.glfwJoystickIsGamepad(jid);
    }

    @Override
    public void glfwSetJoystickCallback(final JoystickCallback cb) {
        org.lwjgl.glfw.GLFW.glfwSetJoystickCallback((j, c) -> cb.joystick(j, c == GLFW.GLFW_CONNECTED));
    }

    @Override
    public boolean glfwUpdateGamepadMappings(final MemoryAddress string) {
        return GLFW.GLFW_TRUE == org.lwjgl.glfw.GLFW.nglfwUpdateGamepadMappings(string.toRawLongValue());
    }

    @Override
    public String glfwGetGamepadName(final int jid) {
        return org.lwjgl.glfw.GLFW.glfwGetGamepadName(jid);
    }

    @Override
    public boolean glfwGetGamepadState(final int jid, final GamepadState state) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void glfwSetClipboardString(final Window window, final CharSequence string) {
        org.lwjgl.glfw.GLFW.glfwSetClipboardString(window.address(), string);
    }

    @Override
    public String glfwGetClipboardString(final Window window) {
        return org.lwjgl.glfw.GLFW.glfwGetClipboardString(window.address());
    }

    @Override
    public double glfwGetTime() {
        return org.lwjgl.glfw.GLFW.glfwGetTime();
    }

    @Override
    public void glfwSetTime(final double time) {
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
    public void glfwMakeContextCurrent(final Window window) {
        org.lwjgl.glfw.GLFW.glfwMakeContextCurrent(window.address());
    }

    @Override
    public Window glfwGetCurrentContext() {
        return Window.ofLong(org.lwjgl.glfw.GLFW.glfwGetCurrentContext());
    }

    @Override
    public void glfwSwapBuffers(final Window window) {
        org.lwjgl.glfw.GLFW.glfwSwapBuffers(window.address());
    }

    @Override
    public void glfwSwapInterval(final int interval) {
        org.lwjgl.glfw.GLFW.glfwSwapInterval(interval);
    }

    @Override
    public boolean glfwExtensionSupported(final CharSequence extension) {
        return org.lwjgl.glfw.GLFW.glfwExtensionSupported(extension);
    }

    @Override
    public MemoryAddress glfwGetProcAddress(final CharSequence procName) {
        return MemoryAddress.ofLong(org.lwjgl.glfw.GLFW.glfwGetProcAddress(procName));
    }
}
