package com.github.fwfurtado.tinyurl.configuration.cache;

import com.github.fwfurtado.tinyurl.configuration.redis.BloomFilterCommand;
import com.github.fwfurtado.tinyurl.datasource.cache.GeneratedHashGateway;
import com.github.fwfurtado.tinyurl.datasource.persistence.ShortenedEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class LoadCacheConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoadCacheConfiguration.class);
    private final GetAllGeneratedHashRepository repository;
    private final GeneratedHashGateway gateway;
    private final BloomFilterCommand command;

    public LoadCacheConfiguration(GetAllGeneratedHashRepository repository, GeneratedHashGateway gateway, BloomFilterCommand command) {
        this.repository = repository;
        this.gateway = gateway;
        this.command = command;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void load() {

        gateway.info().ifPresentOrElse(
                info -> LOGGER.info("Bloom filter already initialized. Info: {}", info),
                () -> {
                    LOGGER.info("Initializing bloom filter");
                    gateway.initializeCache();
                }
        );

        repository
                .findAll()
                .stream()
                .map(ShortenedEntity::getTinyUrl)
                .forEach(gateway::add);
    }

}
