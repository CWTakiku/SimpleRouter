package com.stl.lib_router;

public interface CommLibCallback<T> {
    void onSuccess(T var1);

    void onError(int var1, String var2);

    void onCompleted();
}