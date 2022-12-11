package com.github.fwfurtado.tinyurl.configuration.cache;

import com.github.fwfurtado.tinyurl.datasource.persistence.ShortenedEntity;
import org.springframework.data.repository.Repository;

import java.util.List;

interface GetAllGeneratedHashRepository extends Repository<ShortenedEntity, Long> {
    List<ShortenedEntity> findAll();
}
