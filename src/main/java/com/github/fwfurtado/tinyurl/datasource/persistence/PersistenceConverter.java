package com.github.fwfurtado.tinyurl.datasource.persistence;

import com.github.fwfurtado.tinyurl.core.models.ShortenedURL;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
class PersistenceConverter {

    ShortenedEntity toEntity(ShortenedURL model) {
        return new ShortenedEntity(model.hash(), model.tinyURL().toString(), model.originalURL().toString());
    }

    ShortenedURL toModel(ShortenedEntity entity) {
        return new ShortenedURL(
                URI.create(entity.getUrl()),
                entity.getHash(),
                URI.create(entity.getTinyUrl())
        );
    }
}
