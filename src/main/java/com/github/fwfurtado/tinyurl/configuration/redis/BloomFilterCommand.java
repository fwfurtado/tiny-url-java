package com.github.fwfurtado.tinyurl.configuration.redis;

import io.lettuce.core.dynamic.Commands;
import io.lettuce.core.dynamic.annotation.CommandNaming;
import io.lettuce.core.output.ArrayOutput;

import java.util.List;
import java.util.Map;

@CommandNaming(strategy = CommandNaming.Strategy.DOT)
public interface BloomFilterCommand extends Commands {

    Boolean bfAdd(String key, String value);

    Boolean bfExists(String key, String value);

    Boolean bfReserve(String key, double errorRate, long capacity);

    List<Object> bfInfo(String key);
}
