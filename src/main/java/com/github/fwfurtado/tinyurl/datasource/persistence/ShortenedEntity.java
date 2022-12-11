package com.github.fwfurtado.tinyurl.datasource.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "urls")
public class ShortenedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String tinyUrl;
    @NotNull
    private String url;

    private String hash;

    @UpdateTimestamp
    private Instant updatedAt;

    protected ShortenedEntity() {
    }

    public ShortenedEntity(String hash, String tinyUrl, String url) {
        this.hash = hash;
        this.tinyUrl = tinyUrl;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public String getTinyUrl() {
        return tinyUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getHash() {
        return hash;
    }
}
