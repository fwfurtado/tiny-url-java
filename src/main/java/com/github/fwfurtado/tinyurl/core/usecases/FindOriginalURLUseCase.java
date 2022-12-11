package com.github.fwfurtado.tinyurl.core.usecases;

import com.github.fwfurtado.tinyurl.core.models.ShortenedURL;
import com.github.fwfurtado.tinyurl.core.repository.ShortenedRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindOriginalURLUseCase {
    private final ShortenedRepository repository;

    public FindOriginalURLUseCase(ShortenedRepository repository) {
        this.repository = repository;
    }

    public Optional<ShortenedURL> find(String hash) {
        return repository.findByHash(hash);
    }
}
