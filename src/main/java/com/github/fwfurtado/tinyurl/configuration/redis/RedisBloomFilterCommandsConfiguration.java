package com.github.fwfurtado.tinyurl.configuration.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.dynamic.RedisCommandFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class RedisBloomFilterCommandsConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisBloomFilterCommandsConfiguration.class);

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public BloomFilterCommand bloomFilterCommand(RedisClient client) {
        var connection = client.connect();
        return new RedisCommandFactory(connection).getCommands(BloomFilterCommand.class);
    }

    @Bean(destroyMethod = "close")
    public RedisClient redisClient(RedisURI redisURI) {
        return RedisClient.create(redisURI);
    }

    @Bean
    public RedisURI redisURI(RedisProperties configuration) {
        return RedisURI.builder()
                .withHost(configuration.getHost())
                .withPort(configuration.getPort())
                .build();
    }
}
