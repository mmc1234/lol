package com.github.mmc1234.lol.glfw.backend;

import com.github.mmc1234.lol.base.*;
import com.github.mmc1234.lol.glfw.*;
import jdk.incubator.foreign.*;

import java.nio.*;

public interface GlfwBackend {
    void glfwGetVersion(long majorOut, long minorOut, long revOut);

    String glfwGetVersionString();

    int glfwGetWindowAttrib(Window window, int attrib);

    void glfwSetWindowAttrib(Window window, int attrib, int value);

    void glfwSetWindowMonitor(Window window, Monitor monitor, int x, int y, int width, int height, int refreshRate);
    void glfwFocusWindow(Window window);

    Monitor glfwGetWindowMonitor(Window window);

    void glfwHideWindow(Window window);

    void glfwIconifyWindow(Window window);

    boolean glfwInit();

    void glfwMaximizeWindow(Window window);

    void glfwRequestWindowAttention(Window window);

    void glfwRestoreWindow(Window window);

    void glfwShowWindow(Window window);

    void glfwTerminate();
    void glfwInitHint(int hint, int value);

    int glfwGetError(ObjRef<String> description);
    void glfwSetErrorCallback(ErrorCallback cb);
    Monitor[] glfwGetMonitors();
    Monitor glfwGetPrimaryMonitor();
    void glfwGetMonitorPos(Monitor monitor, long xOut, long yOut);
    void glfwGetMonitorWorkarea(Monitor monitor, long xOut, long yOut, long wOut, long hOut);
    void glfwGetMonitorPhysicalSize(Monitor monitor, long wOut, long hOut) ;
    void glfwGetMonitorContentScale(Monitor monitor, long xScaleOut, long yScaleOut);
    String glfwGetMonitorName(Monitor monitor);
    void glfwSetMonitorUserPointer(Monitor monitor, MemoryAddress pointer);
    MemoryAddress glfwGetMonitorUserPointer(Monitor monitor);
    void glfwSetMonitorCallback(MonitorCallback cb);
    VideoMode[] glfwGetVideoModes(Monitor monitor);
    VideoMode glfwGetVideoMode(Monitor monitor);
    void glfwSetGamma(Monitor monitor, float gamma);
    GammaRamp glfwGetGammaRamp(Monitor monitor);
    void glfwSetGammaRamp(Monitor monitor, GammaRamp ramp);
    void glfwDefaultWindowHints();
    void glfwWindowHint(int hint, int value);
    void glfwWindowHintString(int hint, CharSequence value);
    Window glfwCreateWindow(int width, int height, CharSequence title, Monitor monitor, Window share);
    void glfwDestroyWindow(Window window);
    boolean glfwWindowShouldClose(Window window);

    void glfwSetWindowShouldClose(Window window, boolean value);
    void glfwSetWindowTitle(Window window, CharSequence title);
    void glfwSetWindowIcon(Window window, MemorySegment images, int count);
    void glfwGetWindowPos(Window window, long xOut, long yOut);

    void glfwSetWindowPos(Window window, int x, int y);

    void glfwGetWindowSize(Window window, long xOut, long yOut);
    void glfwSetWindowSizeLimits(Window window, Int2 minSize, Int2 maxSize);
    void glfwSetWindowAspectRatio(Window window, int numer, int denom);

    void glfwSetWindowSize(Window window, int width, int height);
    void glfwGetFramebufferSize(Window window, long xOut, long yOut);
    IntRectangle glfwGetWindowFrameRect(Window window);
    /**
     * @param xOut float*
     * @param yOut float*
     * */
    void glfwGetWindowContentScale(Window window, long xOut, long yOut);
    float glfwGetWindowOpacity(Window window);
    void glfwSetWindowOpacity(Window window, float opacity);
    void glfwSetWindowUserPointer(Window window, MemoryAddress pointer);
    MemoryAddress glfwGetWindowUserPointer(Window window);
    void glfwSetWindowPosCallback(Window window, WindowPosCallback cb);
    void glfwSetWindowSizeCallback(Window window, WindowSizeCallback cb);
    void glfwSetWindowCloseCallback(Window window, WindowCloseCallback cb);
    void glfwSetWindowRefreshCallback(Window window, WindowRefreshCallback cb);
    void glfwSetWindowFocusCallback(Window window, WindowFocusCallback cb);
    void glfwSetWindowIconifyCallback(Window window, WindowIconifyCallback cb);
    void glfwSetWindowMaximizeCallback(Window window,   WindowMaximizeCallback cb);
    void glfwSetFramebufferSizeCallback(Window window,  FramebufferSizeCallback cb);
    void glfwSetWindowContentScaleCallback(Window window,  WindowContentScaleCallback cb);

    void glfwPollEvents();
    void glfwWaitEvents();
    void glfwWaitEventsTimeout(double timeout);
    void glfwPostEmptyEvent();

    int glfwGetInputMode(Window window, int mode);
    void glfwSetInputMode(Window window, int mode, int value);
    boolean glfwRawMouseMotionSupported();
    String glfwGetKeyName(int key, int scancode);
    int glfwGetKeyScancode(int key);
    int glfwGetKey(Window window, int key);
    int glfwGetMouseButton(Window window, int button);
    Double2 glfwGetCursorPos(Window window);
    void glfwSetCursorPos(Window window, double x, double y);
    Cursor glfwCreateCursor(MemorySegment image, int xHot, int yHot);
    Cursor glfwCreateStandardCursor(int shape);
    void glfwDestroyCursor(Cursor cursor);
    void glfwSetCursor(Window window, Cursor cursor);
    void glfwSetKeyCallback(Window window,  KeyCallback cb);
    void glfwSetCharCallback(Window window,  CharCallback cb);
    void glfwSetCharModsCallback(Window window, CharModsCallback cb);
    void glfwSetMouseButtonCallback(Window window,  MouseButtonCallback cb);
    void glfwSetCursorPosCallback(Window window, CursorPosCallback cb);
    void glfwSetCursorEnterCallback(Window window,  CursorEnterCallback cb);
    void glfwSetScrollCallback(Window window,  ScrollCallback cb);
    void glfwSetDropCallback(Window window,  DropCallback cb);
    boolean glfwJoystickPresent(int jid);
    FloatBuffer glfwGetJoystickAxes(int jid);
    ByteBuffer glfwGetJoystickButtons(int jid);
    ByteBuffer glfwGetJoystickHats(int jid);
    String glfwGetJoystickName(int jid);
    String glfwGetJoystickGUID(int jid);
    void glfwSetJoystickUserPointer(int jid, MemoryAddress pointer);
    MemoryAddress glfwGetJoystickUserPointer(int jid);
    boolean glfwJoystickIsGamepad(int jid);
    void glfwSetJoystickCallback(JoystickCallback cb);
    boolean glfwUpdateGamepadMappings(MemoryAddress string);
    String glfwGetGamepadName(int jid);
    boolean glfwGetGamepadState(int jid, GamepadState state);
    void glfwSetClipboardString(Window window, CharSequence string);
    String glfwGetClipboardString(Window window);
    double glfwGetTime();
    void glfwSetTime(double time);
    long glfwGetTimerValue();
    long glfwGetTimerFrequency();
    void glfwMakeContextCurrent(Window window);
    Window glfwGetCurrentContext();
    void glfwSwapBuffers(Window window);
    void glfwSwapInterval(int interval);
    boolean glfwExtensionSupported(CharSequence extension);
    MemoryAddress glfwGetProcAddress(CharSequence procName);
}
