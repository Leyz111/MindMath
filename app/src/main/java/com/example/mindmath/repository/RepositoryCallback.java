package com.example.mindmath.repository;

public interface RepositoryCallback<T> {
    void onSuccess(T result);
    void onFail(String error);
}
