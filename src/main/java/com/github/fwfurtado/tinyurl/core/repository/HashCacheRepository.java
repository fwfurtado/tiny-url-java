package com.github.fwfurtado.tinyurl.core.repository;

public interface HashCacheRepository {
    void add(String value);
    boolean exists(String value);
}
