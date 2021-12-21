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

package com.github.mmc1234.lol.glfw;

import com.github.mmc1234.lol.base.*;
import com.github.mmc1234.lol.glfw.backend.*;
import com.google.inject.*;
import jdk.incubator.foreign.*;

import java.nio.*;

public class GLFW {
    public static final int GLFW_VERSION_MAJOR = 3;
    public static final int GLFW_VERSION_MINOR = 4;
    public static final int GLFW_VERSION_REVISION = 0;
    public static final int GLFW_TRUE = 1;
    public static final int GLFW_FALSE = 0;
    public static final int GLFW_RELEASE = 0;
    public static final int GLFW_PRESS = 1;
    public static final int GLFW_REPEAT = 2;
    public static final int GLFW_HAT_CENTERED = 0;
    public static final int GLFW_HAT_UP = 1;
    public static final int GLFW_HAT_RIGHT = 2;
    public static final int GLFW_HAT_DOWN = 4;
    public static final int GLFW_HAT_LEFT = 8;
    public static final int GLFW_HAT_RIGHT_UP = 3;
    public static final int GLFW_HAT_RIGHT_DOWN = 6;
    public static final int GLFW_HAT_LEFT_UP = 9;
    public static final int GLFW_HAT_LEFT_DOWN = 12;
    public static final int GLFW_KEY_UNKNOWN = -1;
    public static final int GLFW_KEY_SPACE = 32;
    public static final int GLFW_KEY_APOSTROPHE = 39;
    public static final int GLFW_KEY_COMMA = 44;
    public static final int GLFW_KEY_MINUS = 45;
    public static final int GLFW_KEY_PERIOD = 46;
    public static final int GLFW_KEY_SLASH = 47;
    public static final int GLFW_KEY_0 = 48;
    public static final int GLFW_KEY_1 = 49;
    public static final int GLFW_KEY_2 = 50;
    public static final int GLFW_KEY_3 = 51;
    public static final int GLFW_KEY_4 = 52;
    public static final int GLFW_KEY_5 = 53;
    public static final int GLFW_KEY_6 = 54;
    public static final int GLFW_KEY_7 = 55;
    public static final int GLFW_KEY_8 = 56;
    public static final int GLFW_KEY_9 = 57;
    public static final int GLFW_KEY_SEMICOLON = 59;
    public static final int GLFW_KEY_EQUAL = 61;
    public static final int GLFW_KEY_A = 65;
    public static final int GLFW_KEY_B = 66;
    public static final int GLFW_KEY_C = 67;
    public static final int GLFW_KEY_D = 68;
    public static final int GLFW_KEY_E = 69;
    public static final int GLFW_KEY_F = 70;
    public static final int GLFW_KEY_G = 71;
    public static final int GLFW_KEY_H = 72;
    public static final int GLFW_KEY_I = 73;
    public static final int GLFW_KEY_J = 74;
    public static final int GLFW_KEY_K = 75;
    public static final int GLFW_KEY_L = 76;
    public static final int GLFW_KEY_M = 77;
    public static final int GLFW_KEY_N = 78;
    public static final int GLFW_KEY_O = 79;
    public static final int GLFW_KEY_P = 80;
    public static final int GLFW_KEY_Q = 81;
    public static final int GLFW_KEY_R = 82;
    public static final int GLFW_KEY_S = 83;
    public static final int GLFW_KEY_T = 84;
    public static final int GLFW_KEY_U = 85;
    public static final int GLFW_KEY_V = 86;
    public static final int GLFW_KEY_W = 87;
    public static final int GLFW_KEY_X = 88;
    public static final int GLFW_KEY_Y = 89;
    public static final int GLFW_KEY_Z = 90;
    public static final int GLFW_KEY_LEFT_BRACKET = 91;
    public static final int GLFW_KEY_BACKSLASH = 92;
    public static final int GLFW_KEY_RIGHT_BRACKET = 93;
    public static final int GLFW_KEY_GRAVE_ACCENT = 96;
    public static final int GLFW_KEY_WORLD_1 = 161;
    public static final int GLFW_KEY_WORLD_2 = 162;
    public static final int GLFW_KEY_ESCAPE = 256;
    public static final int GLFW_KEY_ENTER = 257;
    public static final int GLFW_KEY_TAB = 258;
    public static final int GLFW_KEY_BACKSPACE = 259;
    public static final int GLFW_KEY_INSERT = 260;
    public static final int GLFW_KEY_DELETE = 261;
    public static final int GLFW_KEY_RIGHT = 262;
    public static final int GLFW_KEY_LEFT = 263;
    public static final int GLFW_KEY_DOWN = 264;
    public static final int GLFW_KEY_UP = 265;
    public static final int GLFW_KEY_PAGE_UP = 266;
    public static final int GLFW_KEY_PAGE_DOWN = 267;
    public static final int GLFW_KEY_HOME = 268;
    public static final int GLFW_KEY_END = 269;
    public static final int GLFW_KEY_CAPS_LOCK = 280;
    public static final int GLFW_KEY_SCROLL_LOCK = 281;
    public static final int GLFW_KEY_NUM_LOCK = 282;
    public static final int GLFW_KEY_PRINT_SCREEN = 283;
    public static final int GLFW_KEY_PAUSE = 284;
    public static final int GLFW_KEY_F1 = 290;
    public static final int GLFW_KEY_F2 = 291;
    public static final int GLFW_KEY_F3 = 292;
    public static final int GLFW_KEY_F4 = 293;
    public static final int GLFW_KEY_F5 = 294;
    public static final int GLFW_KEY_F6 = 295;
    public static final int GLFW_KEY_F7 = 296;
    public static final int GLFW_KEY_F8 = 297;
    public static final int GLFW_KEY_F9 = 298;
    public static final int GLFW_KEY_F10 = 299;
    public static final int GLFW_KEY_F11 = 300;
    public static final int GLFW_KEY_F12 = 301;
    public static final int GLFW_KEY_F13 = 302;
    public static final int GLFW_KEY_F14 = 303;
    public static final int GLFW_KEY_F15 = 304;
    public static final int GLFW_KEY_F16 = 305;
    public static final int GLFW_KEY_F17 = 306;
    public static final int GLFW_KEY_F18 = 307;
    public static final int GLFW_KEY_F19 = 308;
    public static final int GLFW_KEY_F20 = 309;
    public static final int GLFW_KEY_F21 = 310;
    public static final int GLFW_KEY_F22 = 311;
    public static final int GLFW_KEY_F23 = 312;
    public static final int GLFW_KEY_F24 = 313;
    public static final int GLFW_KEY_F25 = 314;
    public static final int GLFW_KEY_KP_0 = 320;
    public static final int GLFW_KEY_KP_1 = 321;
    public static final int GLFW_KEY_KP_2 = 322;
    public static final int GLFW_KEY_KP_3 = 323;
    public static final int GLFW_KEY_KP_4 = 324;
    public static final int GLFW_KEY_KP_5 = 325;
    public static final int GLFW_KEY_KP_6 = 326;
    public static final int GLFW_KEY_KP_7 = 327;
    public static final int GLFW_KEY_KP_8 = 328;
    public static final int GLFW_KEY_KP_9 = 329;
    public static final int GLFW_KEY_KP_DECIMAL = 330;
    public static final int GLFW_KEY_KP_DIVIDE = 331;
    public static final int GLFW_KEY_KP_MULTIPLY = 332;
    public static final int GLFW_KEY_KP_SUBTRACT = 333;
    public static final int GLFW_KEY_KP_ADD = 334;
    public static final int GLFW_KEY_KP_ENTER = 335;
    public static final int GLFW_KEY_KP_EQUAL = 336;
    public static final int GLFW_KEY_LEFT_SHIFT = 340;
    public static final int GLFW_KEY_LEFT_CONTROL = 341;
    public static final int GLFW_KEY_LEFT_ALT = 342;
    public static final int GLFW_KEY_LEFT_SUPER = 343;
    public static final int GLFW_KEY_RIGHT_SHIFT = 344;
    public static final int GLFW_KEY_RIGHT_CONTROL = 345;
    public static final int GLFW_KEY_RIGHT_ALT = 346;
    public static final int GLFW_KEY_RIGHT_SUPER = 347;
    public static final int GLFW_KEY_MENU = 348;
    public static final int GLFW_KEY_LAST = 348;
    public static final int GLFW_MOD_SHIFT = 1;
    public static final int GLFW_MOD_CONTROL = 2;
    public static final int GLFW_MOD_ALT = 4;
    public static final int GLFW_MOD_SUPER = 8;
    public static final int GLFW_MOD_CAPS_LOCK = 16;
    public static final int GLFW_MOD_NUM_LOCK = 32;
    public static final int GLFW_MOUSE_BUTTON_1 = 0;
    public static final int GLFW_MOUSE_BUTTON_2 = 1;
    public static final int GLFW_MOUSE_BUTTON_3 = 2;
    public static final int GLFW_MOUSE_BUTTON_4 = 3;
    public static final int GLFW_MOUSE_BUTTON_5 = 4;
    public static final int GLFW_MOUSE_BUTTON_6 = 5;
    public static final int GLFW_MOUSE_BUTTON_7 = 6;
    public static final int GLFW_MOUSE_BUTTON_8 = 7;
    public static final int GLFW_MOUSE_BUTTON_LAST = 7;
    public static final int GLFW_MOUSE_BUTTON_LEFT = 0;
    public static final int GLFW_MOUSE_BUTTON_RIGHT = 1;
    public static final int GLFW_MOUSE_BUTTON_MIDDLE = 2;
    public static final int GLFW_JOYSTICK_1 = 0;
    public static final int GLFW_JOYSTICK_2 = 1;
    public static final int GLFW_JOYSTICK_3 = 2;
    public static final int GLFW_JOYSTICK_4 = 3;
    public static final int GLFW_JOYSTICK_5 = 4;
    public static final int GLFW_JOYSTICK_6 = 5;
    public static final int GLFW_JOYSTICK_7 = 6;
    public static final int GLFW_JOYSTICK_8 = 7;
    public static final int GLFW_JOYSTICK_9 = 8;
    public static final int GLFW_JOYSTICK_10 = 9;
    public static final int GLFW_JOYSTICK_11 = 10;
    public static final int GLFW_JOYSTICK_12 = 11;
    public static final int GLFW_JOYSTICK_13 = 12;
    public static final int GLFW_JOYSTICK_14 = 13;
    public static final int GLFW_JOYSTICK_15 = 14;
    public static final int GLFW_JOYSTICK_16 = 15;
    public static final int GLFW_JOYSTICK_LAST = 15;
    public static final int GLFW_GAMEPAD_BUTTON_A = 0;
    public static final int GLFW_GAMEPAD_BUTTON_B = 1;
    public static final int GLFW_GAMEPAD_BUTTON_X = 2;
    public static final int GLFW_GAMEPAD_BUTTON_Y = 3;
    public static final int GLFW_GAMEPAD_BUTTON_LEFT_BUMPER = 4;
    public static final int GLFW_GAMEPAD_BUTTON_RIGHT_BUMPER = 5;
    public static final int GLFW_GAMEPAD_BUTTON_BACK = 6;
    public static final int GLFW_GAMEPAD_BUTTON_START = 7;
    public static final int GLFW_GAMEPAD_BUTTON_GUIDE = 8;
    public static final int GLFW_GAMEPAD_BUTTON_LEFT_THUMB = 9;
    public static final int GLFW_GAMEPAD_BUTTON_RIGHT_THUMB = 10;
    public static final int GLFW_GAMEPAD_BUTTON_DPAD_UP = 11;
    public static final int GLFW_GAMEPAD_BUTTON_DPAD_RIGHT = 12;
    public static final int GLFW_GAMEPAD_BUTTON_DPAD_DOWN = 13;
    public static final int GLFW_GAMEPAD_BUTTON_DPAD_LEFT = 14;
    public static final int GLFW_GAMEPAD_BUTTON_LAST = 14;
    public static final int GLFW_GAMEPAD_BUTTON_CROSS = 0;
    public static final int GLFW_GAMEPAD_BUTTON_CIRCLE = 1;
    public static final int GLFW_GAMEPAD_BUTTON_SQUARE = 2;
    public static final int GLFW_GAMEPAD_BUTTON_TRIANGLE = 3;
    public static final int GLFW_GAMEPAD_AXIS_LEFT_X = 0;
    public static final int GLFW_GAMEPAD_AXIS_LEFT_Y = 1;
    public static final int GLFW_GAMEPAD_AXIS_RIGHT_X = 2;
    public static final int GLFW_GAMEPAD_AXIS_RIGHT_Y = 3;
    public static final int GLFW_GAMEPAD_AXIS_LEFT_TRIGGER = 4;
    public static final int GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER = 5;
    public static final int GLFW_GAMEPAD_AXIS_LAST = 5;
    public static final int GLFW_NO_ERROR = 0;
    public static final int GLFW_NOT_INITIALIZED = 65537;
    public static final int GLFW_NO_CURRENT_CONTEXT = 65538;
    public static final int GLFW_INVALID_ENUM = 65539;
    public static final int GLFW_INVALID_VALUE = 65540;
    public static final int GLFW_OUT_OF_MEMORY = 65541;
    public static final int GLFW_API_UNAVAILABLE = 65542;
    public static final int GLFW_VERSION_UNAVAILABLE = 65543;
    public static final int GLFW_PLATFORM_ERROR = 65544;
    public static final int GLFW_FORMAT_UNAVAILABLE = 65545;
    public static final int GLFW_NO_WINDOW_CONTEXT = 65546;
    public static final int GLFW_FOCUSED = 131073;
    public static final int GLFW_ICONIFIED = 131074;
    public static final int GLFW_RESIZABLE = 131075;
    public static final int GLFW_VISIBLE = 131076;
    public static final int GLFW_DECORATED = 131077;
    public static final int GLFW_AUTO_ICONIFY = 131078;
    public static final int GLFW_FLOATING = 131079;
    public static final int GLFW_MAXIMIZED = 131080;
    public static final int GLFW_CENTER_CURSOR = 131081;
    public static final int GLFW_TRANSPARENT_FRAMEBUFFER = 131082;
    public static final int GLFW_HOVERED = 131083;
    public static final int GLFW_FOCUS_ON_SHOW = 131084;
    public static final int GLFW_CURSOR = 208897;
    public static final int GLFW_STICKY_KEYS = 208898;
    public static final int GLFW_STICKY_MOUSE_BUTTONS = 208899;
    public static final int GLFW_LOCK_KEY_MODS = 208900;
    public static final int GLFW_RAW_MOUSE_MOTION = 208901;
    public static final int GLFW_CURSOR_NORMAL = 212993;
    public static final int GLFW_CURSOR_HIDDEN = 212994;
    public static final int GLFW_CURSOR_DISABLED = 212995;
    public static final int GLFW_ARROW_CURSOR = 221185;
    public static final int GLFW_IBEAM_CURSOR = 221186;
    public static final int GLFW_CROSSHAIR_CURSOR = 221187;
    public static final int GLFW_HAND_CURSOR = 221188;
    public static final int GLFW_HRESIZE_CURSOR = 221189;
    public static final int GLFW_VRESIZE_CURSOR = 221190;
    public static final int GLFW_CONNECTED = 262145;
    public static final int GLFW_DISCONNECTED = 262146;
    public static final int GLFW_JOYSTICK_HAT_BUTTONS = 327681;
    public static final int GLFW_COCOA_CHDIR_RESOURCES = 331777;
    public static final int GLFW_COCOA_MENUBAR = 331778;
    public static final int GLFW_DONT_CARE = -1;
    public static final int GLFW_RED_BITS = 135169;
    public static final int GLFW_GREEN_BITS = 135170;
    public static final int GLFW_BLUE_BITS = 135171;
    public static final int GLFW_ALPHA_BITS = 135172;
    public static final int GLFW_DEPTH_BITS = 135173;
    public static final int GLFW_STENCIL_BITS = 135174;
    public static final int GLFW_ACCUM_RED_BITS = 135175;
    public static final int GLFW_ACCUM_GREEN_BITS = 135176;
    public static final int GLFW_ACCUM_BLUE_BITS = 135177;
    public static final int GLFW_ACCUM_ALPHA_BITS = 135178;
    public static final int GLFW_AUX_BUFFERS = 135179;
    public static final int GLFW_STEREO = 135180;
    public static final int GLFW_SAMPLES = 135181;
    public static final int GLFW_SRGB_CAPABLE = 135182;
    public static final int GLFW_REFRESH_RATE = 135183;
    public static final int GLFW_DOUBLEBUFFER = 135184;
    public static final int GLFW_CLIENT_API = 139265;
    public static final int GLFW_CONTEXT_VERSION_MAJOR = 139266;
    public static final int GLFW_CONTEXT_VERSION_MINOR = 139267;
    public static final int GLFW_CONTEXT_REVISION = 139268;
    public static final int GLFW_CONTEXT_ROBUSTNESS = 139269;
    public static final int GLFW_OPENGL_FORWARD_COMPAT = 139270;
    public static final int GLFW_OPENGL_DEBUG_CONTEXT = 139271;
    public static final int GLFW_OPENGL_PROFILE = 139272;
    public static final int GLFW_CONTEXT_RELEASE_BEHAVIOR = 139273;
    public static final int GLFW_CONTEXT_NO_ERROR = 139274;
    public static final int GLFW_CONTEXT_CREATION_API = 139275;
    public static final int GLFW_SCALE_TO_MONITOR = 139276;
    public static final int GLFW_COCOA_RETINA_FRAMEBUFFER = 143361;
    public static final int GLFW_COCOA_FRAME_NAME = 143362;
    public static final int GLFW_COCOA_GRAPHICS_SWITCHING = 143363;
    public static final int GLFW_X11_CLASS_NAME = 147457;
    public static final int GLFW_X11_INSTANCE_NAME = 147458;
    public static final int GLFW_NO_API = 0;
    public static final int GLFW_OPENGL_API = 196609;
    public static final int GLFW_OPENGL_ES_API = 196610;
    public static final int GLFW_NO_ROBUSTNESS = 0;
    public static final int GLFW_NO_RESET_NOTIFICATION = 200705;
    public static final int GLFW_LOSE_CONTEXT_ON_RESET = 200706;
    public static final int GLFW_OPENGL_ANY_PROFILE = 0;
    public static final int GLFW_OPENGL_CORE_PROFILE = 204801;
    public static final int GLFW_OPENGL_COMPAT_PROFILE = 204802;
    public static final int GLFW_ANY_RELEASE_BEHAVIOR = 0;
    public static final int GLFW_RELEASE_BEHAVIOR_FLUSH = 217089;
    public static final int GLFW_RELEASE_BEHAVIOR_NONE = 217090;
    public static final int GLFW_NATIVE_CONTEXT_API = 221185;
    public static final int GLFW_EGL_CONTEXT_API = 221186;
    public static final int GLFW_OSMESA_CONTEXT_API = 221187;

    protected GLFW() {
        throw new UnsupportedOperationException();
    }

    @Inject
    private static GlfwBackend IMPL;

    public static boolean glfwInit() {
        return IMPL.glfwInit();
    }

    public static void glfwTerminate() {
        IMPL.glfwTerminate();
    }

    public static void glfwInitHint(int hint, int value) {
        IMPL.glfwInitHint(hint,value);
    }

    public static void glfwGetVersion(long majorOut, long minorOut, long revOut) {
        IMPL.glfwGetVersion(majorOut, minorOut, revOut);
    }

    public static String glfwGetVersionString() {
        return IMPL.glfwGetVersionString();
    }

    public static int glfwGetError(ObjRef<String> description) {
        return IMPL.glfwGetError(description);
    }

    public static void glfwSetErrorCallback(ErrorCallback cb) {
        IMPL.glfwSetErrorCallback(cb);
    }

    public static Monitor[] glfwGetMonitors() {
        return IMPL.glfwGetMonitors();
    }

    public static Monitor glfwGetPrimaryMonitor() {
        return IMPL.glfwGetPrimaryMonitor();
    }

    public static void glfwGetMonitorPos(Monitor monitor, long xOut, long yOut) {
        IMPL.glfwGetMonitorPos(monitor, xOut,yOut);
    }

    public static Int2 glfwGetMonitorPos(Monitor monitor) {

        throw new UnsupportedOperationException();
        //return IMPL.glfwGetMonitorPos(monitor);
    }

    public static IntRectangle glfwGetMonitorWorkarea(Monitor monitor) {

        throw new UnsupportedOperationException();
        //return IMPL.glfwGetMonitorWorkarea(monitor);
    }

    public static void glfwGetMonitorWorkarea(Monitor monitor, long xOut, long yOut, long wOut, long hOut) {
        IMPL.glfwGetMonitorWorkarea(monitor, xOut,yOut,wOut, hOut);
    }

    public static void glfwGetMonitorPhysicalSize(Monitor monitor, long wOut, long hOut)  {
        IMPL.glfwGetMonitorPhysicalSize(monitor, wOut, hOut);
    }

    public static void glfwGetMonitorContentScale(Monitor monitor, long xScaleOut, long yScaleOut) {
         IMPL.glfwGetMonitorContentScale(monitor, xScaleOut,yScaleOut);
    }

    public static String glfwGetMonitorName(Monitor monitor) {
        return IMPL.glfwGetMonitorName(monitor);
    }

    public static void glfwSetMonitorUserPointer(Monitor monitor, MemoryAddress pointer) {
        IMPL.glfwSetMonitorUserPointer(monitor, pointer);
    }

    public static MemoryAddress glfwGetMonitorUserPointer(Monitor monitor) {
        return IMPL.glfwGetMonitorUserPointer(monitor);
    }

    public static void glfwSetMonitorCallback(MonitorCallback cb) {
        IMPL.glfwSetMonitorCallback(cb);
    }

    public static VideoMode[] glfwGetVideoModes(Monitor monitor) {
        return IMPL.glfwGetVideoModes(monitor);
    }

    public static VideoMode glfwGetVideoMode(Monitor monitor) {
        return IMPL.glfwGetVideoMode(monitor);
    }

    public static void glfwSetGamma(Monitor monitor, float gamma) {
        IMPL.glfwSetGamma(monitor,gamma);
    }

    public static GammaRamp glfwGetGammaRamp(Monitor monitor) {
        return IMPL.glfwGetGammaRamp(monitor);
    }

    public static void glfwSetGammaRamp(Monitor monitor, GammaRamp ramp) {
        IMPL.glfwSetGammaRamp(monitor, ramp);
    }

    public static void glfwDefaultWindowHints() {
        IMPL.glfwDefaultWindowHints();
    }

    public static void glfwWindowHint(int hint, int value) {
        IMPL.glfwWindowHint(hint, value);
    }

    public static void glfwWindowHintString(int hint, CharSequence value) {
        IMPL.glfwWindowHintString(hint, value);
    }

    public static Window glfwCreateWindow(int width, int height, CharSequence title, Monitor monitor, Window share) {
        return IMPL.glfwCreateWindow(width, height, title, monitor, share);
    }

    public static void glfwDestroyWindow(Window window) {
        IMPL.glfwDestroyWindow(window);
    }

    public static boolean glfwWindowShouldClose(Window window) {
        return IMPL.glfwWindowShouldClose(window);
    }

    public static void glfwSetWindowShouldClose(Window window, boolean value) {
        IMPL.glfwSetWindowShouldClose(window, value);
    }

    public static void glfwSetWindowTitle(Window window, CharSequence title) {
        IMPL.glfwSetWindowTitle(window, title);
    }

    public static void glfwSetWindowIcon(Window window, MemorySegment images, int count) {
        IMPL.glfwSetWindowIcon(window, images, count);
    }
    public static void glfwGetWindowPos(Window window, long xOut, long yOut) {
        IMPL.glfwGetWindowPos(window, xOut, yOut);
    }

    public static void glfwSetWindowPos(Window window, int x, int y) {
        IMPL.glfwSetWindowPos(window, x, y);
    }
    public static void glfwGetWindowSize(Window window, long xOut, long yOut) {
        IMPL.glfwGetWindowSize(window, xOut, yOut);
    }

    public static void glfwSetWindowSizeLimits(Window window, Int2 minSize, Int2 maxSize) {
        IMPL.glfwSetWindowSizeLimits(window, minSize, maxSize);
    }

    public static void glfwSetWindowAspectRatio(Window window, int numer, int denom) {
        IMPL.glfwSetWindowAspectRatio(window, numer, denom);
    }

    public static void glfwSetWindowSize(Window window, int width, int height) {
        IMPL.glfwSetWindowSize(window, width, height);
    }

    public static void glfwGetFramebufferSize(Window window, long xOut, long yOut) {
        IMPL.glfwGetFramebufferSize(window, xOut, yOut);
    }

    public static IntRectangle glfwGetWindowFrameRect(Window window) {
        return IMPL.glfwGetWindowFrameRect(window);
    }

    public static void glfwGetWindowContentScale(Window window, long xScaleOut, long yScaleOut) {
        IMPL.glfwGetWindowContentScale(window, xScaleOut, yScaleOut);
    }

    public static float glfwGetWindowOpacity(Window window) {
        return IMPL.glfwGetWindowOpacity(window);
    }

    public static void glfwSetWindowOpacity(Window window, float opacity) {
        IMPL.glfwSetWindowOpacity(window, opacity);
    }

    public static void glfwIconifyWindow(Window window) {
        IMPL.glfwIconifyWindow(window);
    }

    public static void glfwRestoreWindow(Window window) {
        IMPL.glfwRestoreWindow(window);
    }

    public static void glfwMaximizeWindow(Window window) {
        IMPL.glfwMaximizeWindow(window);
    }

    public static void glfwShowWindow(Window window) {
        IMPL.glfwShowWindow(window);
    }

    public static void glfwHideWindow(Window window) {
        IMPL.glfwHideWindow(window);
    }

    public static void glfwFocusWindow(Window window) {
        IMPL.glfwFocusWindow(window);
    }

    public static void glfwRequestWindowAttention(Window window) {
        IMPL.glfwRequestWindowAttention(window);
    }

    public static Monitor glfwGetWindowMonitor(Window window) {
        return IMPL.glfwGetWindowMonitor(window);
    }

    public static void glfwSetWindowMonitor(Window window, Monitor monitor, int x, int y, int width, int height, int refreshRate) {
        IMPL.glfwSetWindowMonitor(window, monitor, x, y, width, height, refreshRate);
    }

    public static int glfwGetWindowAttrib(Window window, int attrib) {
        return IMPL.glfwGetWindowAttrib(window,attrib);
    }

    public static void glfwSetWindowAttrib(Window window, int attrib, int value) {
        IMPL.glfwSetWindowAttrib(window, attrib, value);
    }

    public static void glfwSetWindowUserPointer(Window window, MemoryAddress pointer) {
        IMPL.glfwSetWindowUserPointer(window, pointer);
    }

    public static MemoryAddress glfwGetWindowUserPointer(Window window) {
        return IMPL.glfwGetWindowUserPointer(window);
    }

    public static void glfwSetWindowPosCallback(Window window, WindowPosCallback cb) {
        IMPL.glfwSetWindowPosCallback(window,cb);
    }

    public static void glfwSetWindowSizeCallback(Window window, WindowSizeCallback cb) {
        IMPL.glfwSetWindowSizeCallback(window,cb);
    }

    public static void glfwSetWindowCloseCallback(Window window, WindowCloseCallback cb) {
        IMPL.glfwSetWindowCloseCallback(window, cb);
    }

    public static void glfwSetWindowRefreshCallback(Window window, WindowRefreshCallback cb) {
        IMPL.glfwSetWindowRefreshCallback(window,cb);
    }

    public static void glfwSetWindowFocusCallback(Window window, WindowFocusCallback cb) {
        IMPL.glfwSetWindowFocusCallback(window,cb);
    }

    public static void glfwSetWindowIconifyCallback(Window window, WindowIconifyCallback cb) {
        IMPL.glfwSetWindowIconifyCallback(window, cb);
    }

    public static void glfwSetWindowMaximizeCallback(Window window,   WindowMaximizeCallback cb) {
        IMPL.glfwSetWindowMaximizeCallback(window, cb);
    }

    public static void glfwSetFramebufferSizeCallback(Window window,  FramebufferSizeCallback cb) {
        IMPL.glfwSetFramebufferSizeCallback(window, cb);
    }

    public static void glfwSetWindowContentScaleCallback(Window window,  WindowContentScaleCallback cb) {
        IMPL.glfwSetWindowContentScaleCallback(window, cb);
    }

    public static void glfwPollEvents() {
        IMPL.glfwPollEvents();
    }

    public static void glfwWaitEvents() {
        IMPL.glfwWaitEvents();
    }

    public static void glfwWaitEventsTimeout(double timeout) {
        IMPL.glfwWaitEventsTimeout(timeout);
    }

    public static void glfwPostEmptyEvent() {
        IMPL.glfwPostEmptyEvent();
    }

    public static int glfwGetInputMode(Window window, int mode) {
        return IMPL.glfwGetInputMode(window, mode);
    }

    public static void glfwSetInputMode(Window window, int mode, int value) {
        IMPL.glfwSetInputMode(window, mode, value);
    }

    public static boolean glfwRawMouseMotionSupported() {
        return IMPL.glfwRawMouseMotionSupported();
    }

    public static String glfwGetKeyName(int key, int scancode) {
        return IMPL.glfwGetKeyName(key, scancode);
    }

    public static int glfwGetKeyScancode(int key) {
        return IMPL.glfwGetKeyScancode(key);
    }

    public static int glfwGetKey(Window window, int key) {
        return IMPL.glfwGetKey(window, key);
    }

    public static int glfwGetMouseButton(Window window, int button) {
        return IMPL.glfwGetMouseButton(window, button);
    }

    public static Double2 glfwGetCursorPos(Window window) {
        return IMPL.glfwGetCursorPos(window);
    }

    public static void glfwSetCursorPos(Window window, double x, double y) {
        IMPL.glfwSetCursorPos(window, x, y);
    }
    public static Cursor glfwCreateCursor(MemorySegment image, int xHot, int yHot) {
        return IMPL.glfwCreateCursor(image, xHot, yHot);
    }

    public static Cursor glfwCreateStandardCursor(int shape) {
        return IMPL.glfwCreateStandardCursor(shape);
    }

    public static void glfwDestroyCursor(Cursor cursor) {
        IMPL.glfwDestroyCursor(cursor);
    }

    public static void glfwSetCursor(Window window, Cursor cursor) {
        IMPL.glfwSetCursor(window, cursor);
    }

    public static void glfwSetKeyCallback(Window window,  KeyCallback cb) {
        IMPL.glfwSetKeyCallback(window,cb);
    }

    public static void glfwSetCharCallback(Window window,  CharCallback cb) {
        IMPL.glfwSetCharCallback(window, cb);
    }

    public static void glfwSetCharModsCallback(Window window, CharModsCallback cb) {
        IMPL.glfwSetCharModsCallback(window, cb);
    }

    public static void glfwSetMouseButtonCallback(Window window,  MouseButtonCallback cb) {
        IMPL.glfwSetMouseButtonCallback(window, cb);
    }


    public static void glfwSetCursorPosCallback(Window window, CursorPosCallback cb) {
        IMPL.glfwSetCursorPosCallback(window, cb);
    }

    public static void glfwSetCursorEnterCallback(Window window,  CursorEnterCallback cb) {
        IMPL.glfwSetCursorEnterCallback(window, cb);
    }

    public static void glfwSetScrollCallback(Window window,  ScrollCallback cb) {
        IMPL.glfwSetScrollCallback(window, cb);
    }

    public static void glfwSetDropCallback(Window window,  DropCallback cb) {
        IMPL.glfwSetDropCallback(window, cb);
    }

    public static boolean glfwJoystickPresent(int jid) {
        return IMPL.glfwJoystickPresent(jid);
    }

    public static FloatBuffer glfwGetJoystickAxes(int jid) {
        return IMPL.glfwGetJoystickAxes(jid);
    }

    public static ByteBuffer glfwGetJoystickButtons(int jid) {
        return IMPL.glfwGetJoystickButtons(jid);
    }

    public static ByteBuffer glfwGetJoystickHats(int jid) {
        return IMPL.glfwGetJoystickHats(jid);
    }

    public static String glfwGetJoystickName(int jid) {
        return IMPL.glfwGetJoystickName(jid);
    }

    public static String glfwGetJoystickGUID(int jid) {
        return IMPL.glfwGetJoystickGUID(jid);
    }

    public static void glfwSetJoystickUserPointer(int jid, MemoryAddress pointer) {
        IMPL.glfwSetJoystickUserPointer(jid, pointer);
    }

    public static MemoryAddress glfwGetJoystickUserPointer(int jid) {
        return IMPL.glfwGetJoystickUserPointer(jid);
    }

    public static boolean glfwJoystickIsGamepad(int jid) {
        return IMPL.glfwJoystickIsGamepad(jid);
    }

    public static void glfwSetJoystickCallback(JoystickCallback cb) {
        IMPL.glfwSetJoystickCallback(cb);
    }

    public static boolean glfwUpdateGamepadMappings(MemoryAddress string) {
        return IMPL.glfwUpdateGamepadMappings(string);
    }
    public static String glfwGetGamepadName(int jid) {
        return IMPL.glfwGetGamepadName(jid);
    }

    public static boolean glfwGetGamepadState(int jid, GamepadState state) {
        return IMPL.glfwGetGamepadState(jid, state);
    }

    public static void glfwSetClipboardString(Window window, CharSequence string) {
        IMPL.glfwSetClipboardString(window, string);
    }

    public static String glfwGetClipboardString(Window window) {
        return IMPL.glfwGetClipboardString(window);
    }

    public static double glfwGetTime() {
        return IMPL.glfwGetTime();
    }

    public static void glfwSetTime(double time) {
        IMPL.glfwSetTime(time);
    }

    public static long glfwGetTimerValue() {
        return IMPL.glfwGetTimerValue();
    }

    public static long glfwGetTimerFrequency() {
        return IMPL.glfwGetTimerFrequency();
    }

    public static void glfwMakeContextCurrent(Window window) {
        IMPL.glfwMakeContextCurrent(window);
    }

    public static Window glfwGetCurrentContext() {
        return IMPL.glfwGetCurrentContext();
    }

    public static void glfwSwapBuffers(Window window) {
        IMPL.glfwSwapBuffers(window);
    }

    public static void glfwSwapInterval(int interval) {
        IMPL.glfwSwapInterval(interval);
    }

    public static boolean glfwExtensionSupported(CharSequence extension) {
        return IMPL.glfwExtensionSupported(extension);
    }

    public static MemoryAddress glfwGetProcAddress(CharSequence procName) {
        return IMPL.glfwGetProcAddress(procName);
    }

}
