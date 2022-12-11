package com.github.fwfurtado.tinyurl.core.usecases;

import com.github.fwfurtado.tinyurl.configuration.TinyURLProperties;
import com.github.fwfurtado.tinyurl.core.models.ShortenedURL;
import com.github.fwfurtado.tinyurl.core.repository.HashCacheRepository;
import com.github.fwfurtado.tinyurl.core.repository.ShortenedRepository;
import com.github.fwfurtado.tinyurl.core.services.CollisionService;
import com.github.fwfurtado.tinyurl.core.services.HashService;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class ShortenURLUseCase {
    private final HashService hashService;
    private final ShortenedRepository shortenedRepository;
    private final HashCacheRepository hashCacheRepository;
    private final TinyURLProperties properties;
    private final CollisionService collisionService;

    public ShortenURLUseCase(HashService hashService, ShortenedRepository shortenedRepository, HashCacheRepository hashCacheRepository, TinyURLProperties properties, CollisionService collisionService) {
        this.hashService = hashService;
        this.shortenedRepository = shortenedRepository;
        this.hashCacheRepository = hashCacheRepository;
        this.properties = properties;
        this.collisionService = collisionService;
    }


    public ShortenedURL shorten(URI originalURL) {
        var originalURLString = originalURL.toString();
        var hash = hashService.generate(originalURLString);

        var collision = collisionService.evaluate(hash, originalURL);

        var prefixLength = 0;

        while (collision != CollisionService.Collision.NONE) {
            if (collision == CollisionService.Collision.IN_CACHE_AND_DB) {
                return loadFromDB(hash);
            }

            hash = hashService.generateWithPrefix(originalURLString, prefixLength);
            collision = collisionService.evaluate(hash, originalURL);
            prefixLength++;
        }

        var tinyURL = properties.basePath().resolve(hash);

        ShortenedURL shortenedURL = new ShortenedURL(originalURL, hash, tinyURL);

        shortenedRepository.save(shortenedURL);
        hashCacheRepository.add(hash);

        return shortenedURL;
    }

    private ShortenedURL loadFromDB(String hash) {
        return shortenedRepository.findByHash(hash).get();
    }
}
