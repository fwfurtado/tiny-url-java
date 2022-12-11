package com.github.fwfurtado.tinyurl.datasource.persistence;

import com.github.fwfurtado.tinyurl.core.models.ShortenedURL;
import com.github.fwfurtado.tinyurl.core.repository.ShortenedRepository;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.util.Optional;

@Repository
public class ShortenedGateway implements ShortenedRepository {
    private final ShortenedURLRepository repository;
    private final PersistenceConverter converter;

    public ShortenedGateway(ShortenedURLRepository repository, PersistenceConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public boolean existsByHashAndOriginalURL(String hash, URI originalURL) {
        return repository.existsByHashAndUrl(hash, originalURL.toString());
    }

    @Override
    public void save(ShortenedURL shortenedURL) {
        repository.save(converter.toEntity(shortenedURL));
    }

    @Override
    public Optional<ShortenedURL> findByHash(String hash) {
        return repository.findByHash(hash).map(converter::toModel);
    }
}
