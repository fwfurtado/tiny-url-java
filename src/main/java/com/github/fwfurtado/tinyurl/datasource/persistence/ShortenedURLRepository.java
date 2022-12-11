package com.github.fwfurtado.tinyurl.datasource.persistence;


import org.springframework.data.repository.Repository;

import java.util.Optional;

interface ShortenedURLRepository extends Repository<ShortenedEntity, Long> {
    boolean existsByHashAndUrl(String hash, String url);

    void save(ShortenedEntity shortenedEntity);

    Optional<ShortenedEntity> findByHash(String hash);
}
