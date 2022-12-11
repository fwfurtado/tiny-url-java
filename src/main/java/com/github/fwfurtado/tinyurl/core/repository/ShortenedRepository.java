package com.github.fwfurtado.tinyurl.core.repository;

import com.github.fwfurtado.tinyurl.core.models.ShortenedURL;

import java.net.URI;
import java.util.Optional;

public interface ShortenedRepository {
    boolean existsByHashAndOriginalURL(String hash, URI originalURL);

    void save(ShortenedURL shortenedURL);

    Optional<ShortenedURL> findByHash(String hash);
}
