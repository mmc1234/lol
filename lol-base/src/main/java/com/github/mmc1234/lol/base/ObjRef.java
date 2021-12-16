package com.github.mmc1234.lol.base;


public class ObjRef<T> implements Ref<T> {
    private T object;

    public ObjRef(T obj) {
        this.object = obj;
    }

    @Override
    public void set(T o) {
        this.object = o;
    }

    @Override
    public T get() {
        return object;
    }
}
