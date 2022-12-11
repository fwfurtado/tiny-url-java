package com.github.fwfurtado.tinyurl.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(TinyURLProperties.class)
public class TinyURLPropertiesConfiguration {
}
