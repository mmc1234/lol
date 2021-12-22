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

package com.github.mmc1234.lol.base;

import java.util.function.Consumer;
import java.util.function.Function;

public class Sugars {

    public interface SafeCall<R, E extends Throwable> {
        R get() throws E;
    }

    public interface SafeRunnable<E extends Throwable> {
        void run() throws E;
    }

    @SuppressWarnings("unchecked")
    public static <R, E extends Throwable> R noCatch(final SafeCall<R, E> func,
                                                     final Function<E, R> catchCallback) {
        try {
            return func.get();
        } catch (final Throwable throwable) {
            return catchCallback.apply((E) throwable);
        }
    }


    public static <R, E extends Throwable> R noCatch(final SafeCall<R, E> func) {
        try {
            return func.get();
        } catch (final Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <E extends Throwable> void noCatchConsumer(final SafeRunnable<E> func,
                                                             final Consumer<E> catchCallback) {
        try {
            func.run();
        } catch (final Throwable throwable) {
            catchCallback.accept((E) throwable);
        }
    }

    public static <E extends Throwable> void noCatchRunnable(final SafeRunnable<E> func) {
        try {
            func.run();
        } catch (final Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public static void notNull(final Object obj, final Runnable exec) {
        if (obj != null) {
            exec.run();
        }
    }

    public static <T> void notNull(final T obj, final Consumer<T> exec) {
        if (obj != null) {
            exec.accept(obj);
        }
    }

    public static <T> void only(final T obj, final Consumer<T> exec, final Function<T, Boolean> exp) {
        if (exp.apply(obj)) {
            exec.accept(obj);
        }
    }

    public static <T> void not(final T obj, final Consumer<T> exec, final Function<T, Boolean> exp) {
        if (!exp.apply(obj)) {
            exec.accept(obj);
        }
    }
}
