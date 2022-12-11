package com.github.fwfurtado.tinyurl.datasource.cache;

import com.github.fwfurtado.tinyurl.configuration.TinyURLProperties;
import com.github.fwfurtado.tinyurl.configuration.redis.BloomFilterCommand;
import com.github.fwfurtado.tinyurl.core.repository.HashCacheRepository;
import io.lettuce.core.output.ArrayOutput;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class GeneratedHashGateway implements HashCacheRepository {
    private final TinyURLProperties.Generator properties;
    private final BloomFilterCommand command;

    public GeneratedHashGateway(TinyURLProperties properties, BloomFilterCommand command) {
        this.properties = properties.generator();
        this.command = command;
    }

    public void initializeCache() {
        command.bfReserve(properties.key(), properties.errorRate(), properties.capacity());
    }

    public void add(String hash) {
        command.bfAdd(properties.key(), hash);
    }

    public boolean exists(String hash) {
        return command.bfExists(properties.key(), hash);
    }

    public Optional<BloomFilterInfo> info() {
        try {
            List<Object> output = command.bfInfo(properties.key());
            if (output.isEmpty()) {
                return Optional.empty();
            }

            return Optional.of(
                    new BloomFilterInfo(
                            (long) output.get(1),
                            (long) output.get(3),
                            (long) output.get(5),
                            (long) output.get(7),
                            (long) output.get(9)
                    )
            );
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
