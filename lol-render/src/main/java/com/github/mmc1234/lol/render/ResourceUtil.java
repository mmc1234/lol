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

package com.github.mmc1234.lol.render;

import com.github.mmc1234.lol.base.*;
import org.apache.commons.io.*;

import java.io.*;
import java.nio.charset.*;

public final class ResourceUtil {
    private ResourceUtil() {
        throw new IllegalCallerException();
    }

    public static final InputStream getModuleResourceAsStream(String name) throws IOException {
        return ResourceUtil.class.getModule().getResourceAsStream(name);
    }

    public static final String loadModuleText(String name, Charset charset) {
        return Sugars.noCatch(() -> IOUtils.toString(Sugars.noCatch(() -> getModuleResourceAsStream(name)), charset));
    }

    public static final String loadModuleText(String name) {
        return Sugars.noCatch(() -> IOUtils.toString(Sugars.noCatch(() -> getModuleResourceAsStream(name)),
                Charset.defaultCharset()));
    }
}
