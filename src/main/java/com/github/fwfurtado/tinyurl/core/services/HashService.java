package com.github.fwfurtado.tinyurl.core.services;

import java.util.function.Predicate;

public interface HashService {
    String generate(String input);
    String generateWithPrefix(String input, int length);
}
