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

package com.github.mmc1234.lol.render.legacy.demo;

import org.apache.commons.io.*;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

/**
 * Demo: Load resource
 * */
public class Demo1_1_2 {
    public static void main(final String[] args) throws IOException {
        final var is = Demo1_1_2.class.getModule().getResourceAsStream("demo_1_1_2.txt");
        final var str = IOUtils.toString(Objects.requireNonNull(is), Charset.defaultCharset());

        System.out.println(str);
    }
}
