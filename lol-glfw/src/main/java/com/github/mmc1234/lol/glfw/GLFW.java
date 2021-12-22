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
        return GLFW.IMPL.glfwInit();
    }

    public static void glfwTerminate() {
        GLFW.IMPL.glfwTerminate();
    }

    public static void glfwInitHint(final int hint, final int value) {
        GLFW.IMPL.glfwInitHint(hint, value);
    }

    public static void glfwGetVersion(final long majorOut, final long minorOut, final long revOut) {
        GLFW.IMPL.glfwGetVersion(majorOut, minorOut, revOut);
    }

    public static String glfwGetVersionString() {
        return GLFW.IMPL.glfwGetVersionString();
    }

    public static int glfwGetError(final ObjRef<String> description) {
        return GLFW.IMPL.glfwGetError(description);
    }

    public static void glfwSetErrorCallback(final ErrorCallback cb) {
        GLFW.IMPL.glfwSetErrorCallback(cb);
    }

    public static Monitor[] glfwGetMonitors() {
        return GLFW.IMPL.glfwGetMonitors();
    }

    public static Monitor glfwGetPrimaryMonitor() {
        return GLFW.IMPL.glfwGetPrimaryMonitor();
    }

    public static void glfwGetMonitorPos(final Monitor monitor, final long xOut, final long yOut) {
        GLFW.IMPL.glfwGetMonitorPos(monitor, xOut, yOut);
    }

    public static Int2 glfwGetMonitorPos(final Monitor monitor) {

        throw new UnsupportedOperationException();
        //return IMPL.glfwGetMonitorPos(monitor);
    }

    public static IntRectangle glfwGetMonitorWorkarea(final Monitor monitor) {

        throw new UnsupportedOperationException();
        //return IMPL.glfwGetMonitorWorkarea(monitor);
    }

    public static void glfwGetMonitorWorkarea(final Monitor monitor, final long xOut, final long yOut, final long wOut, final long hOut) {
        GLFW.IMPL.glfwGetMonitorWorkarea(monitor, xOut, yOut, wOut, hOut);
    }

    public static void glfwGetMonitorPhysicalSize(final Monitor monitor, final long wOut, final long hOut) {
        GLFW.IMPL.glfwGetMonitorPhysicalSize(monitor, wOut, hOut);
    }

    public static void glfwGetMonitorContentScale(final Monitor monitor, final long xScaleOut, final long yScaleOut) {
        GLFW.IMPL.glfwGetMonitorContentScale(monitor, xScaleOut, yScaleOut);
    }

    public static String glfwGetMonitorName(final Monitor monitor) {
        return GLFW.IMPL.glfwGetMonitorName(monitor);
    }

    public static void glfwSetMonitorUserPointer(final Monitor monitor, final MemoryAddress pointer) {
        GLFW.IMPL.glfwSetMonitorUserPointer(monitor, pointer);
    }

    public static MemoryAddress glfwGetMonitorUserPointer(final Monitor monitor) {
        return GLFW.IMPL.glfwGetMonitorUserPointer(monitor);
    }

    public static void glfwSetMonitorCallback(final MonitorCallback cb) {
        GLFW.IMPL.glfwSetMonitorCallback(cb);
    }

    public static VideoMode[] glfwGetVideoModes(final Monitor monitor) {
        return GLFW.IMPL.glfwGetVideoModes(monitor);
    }

    public static VideoMode glfwGetVideoMode(final Monitor monitor) {
        return GLFW.IMPL.glfwGetVideoMode(monitor);
    }

    public static void glfwSetGamma(final Monitor monitor, final float gamma) {
        GLFW.IMPL.glfwSetGamma(monitor, gamma);
    }

    public static GammaRamp glfwGetGammaRamp(final Monitor monitor) {
        return GLFW.IMPL.glfwGetGammaRamp(monitor);
    }

    public static void glfwSetGammaRamp(final Monitor monitor, final GammaRamp ramp) {
        GLFW.IMPL.glfwSetGammaRamp(monitor, ramp);
    }

    public static void glfwDefaultWindowHints() {
        GLFW.IMPL.glfwDefaultWindowHints();
    }

    public static void glfwWindowHint(final int hint, final int value) {
        GLFW.IMPL.glfwWindowHint(hint, value);
    }

    public static void glfwWindowHintString(final int hint, final CharSequence value) {
        GLFW.IMPL.glfwWindowHintString(hint, value);
    }

    public static Window glfwCreateWindow(final int width, final int height, final CharSequence title, final Monitor monitor, final Window share) {
        return GLFW.IMPL.glfwCreateWindow(width, height, title, monitor, share);
    }

    public static void glfwDestroyWindow(final Window window) {
        GLFW.IMPL.glfwDestroyWindow(window);
    }

    public static boolean glfwWindowShouldClose(final Window window) {
        return GLFW.IMPL.glfwWindowShouldClose(window);
    }

    public static void glfwSetWindowShouldClose(final Window window, final boolean value) {
        GLFW.IMPL.glfwSetWindowShouldClose(window, value);
    }

    public static void glfwSetWindowTitle(final Window window, final CharSequence title) {
        GLFW.IMPL.glfwSetWindowTitle(window, title);
    }

    public static void glfwSetWindowIcon(final Window window, final MemorySegment images, final int count) {
        GLFW.IMPL.glfwSetWindowIcon(window, images, count);
    }

    public static void glfwGetWindowPos(final Window window, final long xOut, final long yOut) {
        GLFW.IMPL.glfwGetWindowPos(window, xOut, yOut);
    }

    public static void glfwSetWindowPos(final Window window, final int x, final int y) {
        GLFW.IMPL.glfwSetWindowPos(window, x, y);
    }

    public static void glfwGetWindowSize(final Window window, final long xOut, final long yOut) {
        GLFW.IMPL.glfwGetWindowSize(window, xOut, yOut);
    }

    public static void glfwSetWindowSizeLimits(final Window window, final Int2 minSize, final Int2 maxSize) {
        GLFW.IMPL.glfwSetWindowSizeLimits(window, minSize, maxSize);
    }

    public static void glfwSetWindowAspectRatio(final Window window, final int numer, final int denom) {
        GLFW.IMPL.glfwSetWindowAspectRatio(window, numer, denom);
    }

    public static void glfwSetWindowSize(final Window window, final int width, final int height) {
        GLFW.IMPL.glfwSetWindowSize(window, width, height);
    }

    public static void glfwGetFramebufferSize(final Window window, final long xOut, final long yOut) {
        GLFW.IMPL.glfwGetFramebufferSize(window, xOut, yOut);
    }

    public static IntRectangle glfwGetWindowFrameRect(final Window window) {
        return GLFW.IMPL.glfwGetWindowFrameRect(window);
    }

    public static void glfwGetWindowContentScale(final Window window, final long xScaleOut, final long yScaleOut) {
        GLFW.IMPL.glfwGetWindowContentScale(window, xScaleOut, yScaleOut);
    }

    public static float glfwGetWindowOpacity(final Window window) {
        return GLFW.IMPL.glfwGetWindowOpacity(window);
    }

    public static void glfwSetWindowOpacity(final Window window, final float opacity) {
        GLFW.IMPL.glfwSetWindowOpacity(window, opacity);
    }

    public static void glfwIconifyWindow(final Window window) {
        GLFW.IMPL.glfwIconifyWindow(window);
    }

    public static void glfwRestoreWindow(final Window window) {
        GLFW.IMPL.glfwRestoreWindow(window);
    }

    public static void glfwMaximizeWindow(final Window window) {
        GLFW.IMPL.glfwMaximizeWindow(window);
    }

    public static void glfwShowWindow(final Window window) {
        GLFW.IMPL.glfwShowWindow(window);
    }

    public static void glfwHideWindow(final Window window) {
        GLFW.IMPL.glfwHideWindow(window);
    }

    public static void glfwFocusWindow(final Window window) {
        GLFW.IMPL.glfwFocusWindow(window);
    }

    public static void glfwRequestWindowAttention(final Window window) {
        GLFW.IMPL.glfwRequestWindowAttention(window);
    }

    public static Monitor glfwGetWindowMonitor(final Window window) {
        return GLFW.IMPL.glfwGetWindowMonitor(window);
    }

    public static void glfwSetWindowMonitor(final Window window, final Monitor monitor, final int x, final int y, final int width, final int height, final int refreshRate) {
        GLFW.IMPL.glfwSetWindowMonitor(window, monitor, x, y, width, height, refreshRate);
    }

    public static int glfwGetWindowAttrib(final Window window, final int attrib) {
        return GLFW.IMPL.glfwGetWindowAttrib(window, attrib);
    }

    public static void glfwSetWindowAttrib(final Window window, final int attrib, final int value) {
        GLFW.IMPL.glfwSetWindowAttrib(window, attrib, value);
    }

    public static void glfwSetWindowUserPointer(final Window window, final MemoryAddress pointer) {
        GLFW.IMPL.glfwSetWindowUserPointer(window, pointer);
    }

    public static MemoryAddress glfwGetWindowUserPointer(final Window window) {
        return GLFW.IMPL.glfwGetWindowUserPointer(window);
    }

    public static void glfwSetWindowPosCallback(final Window window, final WindowPosCallback cb) {
        GLFW.IMPL.glfwSetWindowPosCallback(window, cb);
    }

    public static void glfwSetWindowSizeCallback(final Window window, final WindowSizeCallback cb) {
        GLFW.IMPL.glfwSetWindowSizeCallback(window, cb);
    }

    public static void glfwSetWindowCloseCallback(final Window window, final WindowCloseCallback cb) {
        GLFW.IMPL.glfwSetWindowCloseCallback(window, cb);
    }

    public static void glfwSetWindowRefreshCallback(final Window window, final WindowRefreshCallback cb) {
        GLFW.IMPL.glfwSetWindowRefreshCallback(window, cb);
    }

    public static void glfwSetWindowFocusCallback(final Window window, final WindowFocusCallback cb) {
        GLFW.IMPL.glfwSetWindowFocusCallback(window, cb);
    }

    public static void glfwSetWindowIconifyCallback(final Window window, final WindowIconifyCallback cb) {
        GLFW.IMPL.glfwSetWindowIconifyCallback(window, cb);
    }

    public static void glfwSetWindowMaximizeCallback(final Window window, final WindowMaximizeCallback cb) {
        GLFW.IMPL.glfwSetWindowMaximizeCallback(window, cb);
    }

    public static void glfwSetFramebufferSizeCallback(final Window window, final FramebufferSizeCallback cb) {
        GLFW.IMPL.glfwSetFramebufferSizeCallback(window, cb);
    }

    public static void glfwSetWindowContentScaleCallback(final Window window, final WindowContentScaleCallback cb) {
        GLFW.IMPL.glfwSetWindowContentScaleCallback(window, cb);
    }

    public static void glfwPollEvents() {
        GLFW.IMPL.glfwPollEvents();
    }

    public static void glfwWaitEvents() {
        GLFW.IMPL.glfwWaitEvents();
    }

    public static void glfwWaitEventsTimeout(final double timeout) {
        GLFW.IMPL.glfwWaitEventsTimeout(timeout);
    }

    public static void glfwPostEmptyEvent() {
        GLFW.IMPL.glfwPostEmptyEvent();
    }

    public static int glfwGetInputMode(final Window window, final int mode) {
        return GLFW.IMPL.glfwGetInputMode(window, mode);
    }

    public static void glfwSetInputMode(final Window window, final int mode, final int value) {
        GLFW.IMPL.glfwSetInputMode(window, mode, value);
    }

    public static boolean glfwRawMouseMotionSupported() {
        return GLFW.IMPL.glfwRawMouseMotionSupported();
    }

    public static String glfwGetKeyName(final int key, final int scancode) {
        return GLFW.IMPL.glfwGetKeyName(key, scancode);
    }

    public static int glfwGetKeyScancode(final int key) {
        return GLFW.IMPL.glfwGetKeyScancode(key);
    }

    public static int glfwGetKey(final Window window, final int key) {
        return GLFW.IMPL.glfwGetKey(window, key);
    }

    public static int glfwGetMouseButton(final Window window, final int button) {
        return GLFW.IMPL.glfwGetMouseButton(window, button);
    }

    public static Double2 glfwGetCursorPos(final Window window) {
        return GLFW.IMPL.glfwGetCursorPos(window);
    }

    public static void glfwSetCursorPos(final Window window, final double x, final double y) {
        GLFW.IMPL.glfwSetCursorPos(window, x, y);
    }

    public static Cursor glfwCreateCursor(final MemorySegment image, final int xHot, final int yHot) {
        return GLFW.IMPL.glfwCreateCursor(image, xHot, yHot);
    }

    public static Cursor glfwCreateStandardCursor(final int shape) {
        return GLFW.IMPL.glfwCreateStandardCursor(shape);
    }

    public static void glfwDestroyCursor(final Cursor cursor) {
        GLFW.IMPL.glfwDestroyCursor(cursor);
    }

    public static void glfwSetCursor(final Window window, final Cursor cursor) {
        GLFW.IMPL.glfwSetCursor(window, cursor);
    }

    public static void glfwSetKeyCallback(final Window window, final KeyCallback cb) {
        GLFW.IMPL.glfwSetKeyCallback(window, cb);
    }

    public static void glfwSetCharCallback(final Window window, final CharCallback cb) {
        GLFW.IMPL.glfwSetCharCallback(window, cb);
    }

    public static void glfwSetCharModsCallback(final Window window, final CharModsCallback cb) {
        GLFW.IMPL.glfwSetCharModsCallback(window, cb);
    }

    public static void glfwSetMouseButtonCallback(final Window window, final MouseButtonCallback cb) {
        GLFW.IMPL.glfwSetMouseButtonCallback(window, cb);
    }


    public static void glfwSetCursorPosCallback(final Window window, final CursorPosCallback cb) {
        GLFW.IMPL.glfwSetCursorPosCallback(window, cb);
    }

    public static void glfwSetCursorEnterCallback(final Window window, final CursorEnterCallback cb) {
        GLFW.IMPL.glfwSetCursorEnterCallback(window, cb);
    }

    public static void glfwSetScrollCallback(final Window window, final ScrollCallback cb) {
        GLFW.IMPL.glfwSetScrollCallback(window, cb);
    }

    public static void glfwSetDropCallback(final Window window, final DropCallback cb) {
        GLFW.IMPL.glfwSetDropCallback(window, cb);
    }

    public static boolean glfwJoystickPresent(final int jid) {
        return GLFW.IMPL.glfwJoystickPresent(jid);
    }

    public static FloatBuffer glfwGetJoystickAxes(final int jid) {
        return GLFW.IMPL.glfwGetJoystickAxes(jid);
    }

    public static ByteBuffer glfwGetJoystickButtons(final int jid) {
        return GLFW.IMPL.glfwGetJoystickButtons(jid);
    }

    public static ByteBuffer glfwGetJoystickHats(final int jid) {
        return GLFW.IMPL.glfwGetJoystickHats(jid);
    }

    public static String glfwGetJoystickName(final int jid) {
        return GLFW.IMPL.glfwGetJoystickName(jid);
    }

    public static String glfwGetJoystickGUID(final int jid) {
        return GLFW.IMPL.glfwGetJoystickGUID(jid);
    }

    public static void glfwSetJoystickUserPointer(final int jid, final MemoryAddress pointer) {
        GLFW.IMPL.glfwSetJoystickUserPointer(jid, pointer);
    }

    public static MemoryAddress glfwGetJoystickUserPointer(final int jid) {
        return GLFW.IMPL.glfwGetJoystickUserPointer(jid);
    }

    public static boolean glfwJoystickIsGamepad(final int jid) {
        return GLFW.IMPL.glfwJoystickIsGamepad(jid);
    }

    public static void glfwSetJoystickCallback(final JoystickCallback cb) {
        GLFW.IMPL.glfwSetJoystickCallback(cb);
    }

    public static boolean glfwUpdateGamepadMappings(final MemoryAddress string) {
        return GLFW.IMPL.glfwUpdateGamepadMappings(string);
    }

    public static String glfwGetGamepadName(final int jid) {
        return GLFW.IMPL.glfwGetGamepadName(jid);
    }

    public static boolean glfwGetGamepadState(final int jid, final GamepadState state) {
        return GLFW.IMPL.glfwGetGamepadState(jid, state);
    }

    public static void glfwSetClipboardString(final Window window, final CharSequence string) {
        GLFW.IMPL.glfwSetClipboardString(window, string);
    }

    public static String glfwGetClipboardString(final Window window) {
        return GLFW.IMPL.glfwGetClipboardString(window);
    }

    public static double glfwGetTime() {
        return GLFW.IMPL.glfwGetTime();
    }

    public static void glfwSetTime(final double time) {
        GLFW.IMPL.glfwSetTime(time);
    }

    public static long glfwGetTimerValue() {
        return GLFW.IMPL.glfwGetTimerValue();
    }

    public static long glfwGetTimerFrequency() {
        return GLFW.IMPL.glfwGetTimerFrequency();
    }

    public static void glfwMakeContextCurrent(final Window window) {
        GLFW.IMPL.glfwMakeContextCurrent(window);
    }

    public static Window glfwGetCurrentContext() {
        return GLFW.IMPL.glfwGetCurrentContext();
    }

    public static void glfwSwapBuffers(final Window window) {
        GLFW.IMPL.glfwSwapBuffers(window);
    }

    public static void glfwSwapInterval(final int interval) {
        GLFW.IMPL.glfwSwapInterval(interval);
    }

    public static boolean glfwExtensionSupported(final CharSequence extension) {
        return GLFW.IMPL.glfwExtensionSupported(extension);
    }

    public static MemoryAddress glfwGetProcAddress(final CharSequence procName) {
        return GLFW.IMPL.glfwGetProcAddress(procName);
    }

}
