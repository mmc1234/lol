package com.github.mmc1234.lol.base;

import java.util.function.Supplier;

public interface Ref<T> extends Supplier<T> {
    void set(T o);

    T get();

    static <T> Ref<T> newRef(T t) {
        return new ObjRef<>(t);
    }
    
    

}
