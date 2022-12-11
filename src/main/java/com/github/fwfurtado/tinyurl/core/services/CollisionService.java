package com.github.fwfurtado.tinyurl.core.services;

import com.github.fwfurtado.tinyurl.core.repository.HashCacheRepository;
import com.github.fwfurtado.tinyurl.core.repository.ShortenedRepository;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class CollisionService {
    private final HashCacheRepository hashCacheRepository;
    private final ShortenedRepository shortenedRepository;

    public CollisionService(HashCacheRepository hashCacheRepository, ShortenedRepository shortenedRepository) {
        this.hashCacheRepository = hashCacheRepository;
        this.shortenedRepository = shortenedRepository;
    }

    public Collision evaluate(String hash, URI originalURL) {
        if (isInBloomFilter(hash)) {
            if (isInDB(hash, originalURL)) {
                return Collision.IN_CACHE_AND_DB;
            }

            return Collision.COLLISION;
        }

        return Collision.NONE;
    }

    private boolean isInDB(String hash, URI originalURL) {
        return shortenedRepository.existsByHashAndOriginalURL(hash, originalURL);
    }

    private boolean isInBloomFilter(String hash) {
        return hashCacheRepository.exists(hash);
    }

    public enum Collision {
        NONE,
        IN_CACHE_AND_DB,
        COLLISION
    }
}
