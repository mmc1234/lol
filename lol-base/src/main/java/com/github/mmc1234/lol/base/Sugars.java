package com.github.mmc1234.lol.base;

import java.util.function.Consumer;
import java.util.function.Function;

public class Sugars {

    public interface SafeCall<R, E extends Throwable> {
        public R get() throws E;
    }

    public interface SafeRunnable<E extends Throwable> {
        public void run() throws E;
    }

    @SuppressWarnings("unchecked")
    public static <R, E extends Throwable> R noCatch(SafeCall<R, E> func,
                                                     Function<E, R> catchCallback) {
        try {
            return func.get();
        } catch (Throwable throwable) {
            return catchCallback.apply((E) throwable);
        }
    }


    public static <R, E extends Throwable> R noCatch(SafeCall<R, E> func) {
        try {
            return func.get();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <E extends Throwable> void noCatchConsumer(SafeRunnable<E> func,
                                                             Consumer<E> catchCallback) {
        try {
            func.run();
        } catch (Throwable throwable) {
            catchCallback.accept((E) throwable);
        }
    }

    public static <E extends Throwable> void noCatchRunnable(SafeRunnable<E> func) {
        try {
            func.run();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public static void notNull(Object obj, Runnable exec) {
        if (obj != null) {
            exec.run();
        }
    }

    public static <T> void notNull(T obj, Consumer<T> exec) {
        if (obj != null) {
            exec.accept(obj);
        }
    }

    public static <T> void only(T obj, Consumer<T> exec, Function<T, Boolean> exp) {
        if (exp.apply(obj)) {
            exec.accept(obj);
        }
    }

    public static <T> void not(T obj, Consumer<T> exec, Function<T, Boolean> exp) {
        if (!exp.apply(obj)) {
            exec.accept(obj);
        }
    }
}
