package com.github.fwfurtado.tinyurl.core.models;

import java.net.URI;

public record ShortenedURL(
        URI originalURL,
        String hash,
        URI tinyURL
) {
}
