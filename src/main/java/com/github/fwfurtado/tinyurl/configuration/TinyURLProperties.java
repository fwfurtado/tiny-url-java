package com.github.fwfurtado.tinyurl.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@ConfigurationProperties(prefix = "tiny-url")
public record TinyURLProperties(
        URI basePath,
        Generator generator
) {
    public record Generator(
            String key,
            int length,
            long capacity,
            double errorRate,
            String salt
    ) {
    }

}
