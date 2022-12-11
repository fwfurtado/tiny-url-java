package com.github.fwfurtado.tinyurl.transport.http;

import com.github.fwfurtado.tinyurl.core.models.ShortenedURL;
import com.github.fwfurtado.tinyurl.core.usecases.ShortenURLUseCase;
import com.github.tinyurl.transport.http.ShortenerApi;
import com.github.tinyurl.transport.http.UrlRequest;
import com.github.tinyurl.transport.http.UrlResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class ShortenAdapter implements ShortenerApi {

    private final ShortenURLUseCase shortenUseCase;

    public ShortenAdapter(ShortenURLUseCase shortenUseCase) {
        this.shortenUseCase = shortenUseCase;
    }

    @Override
    @PostMapping("shorten")
    public ResponseEntity<UrlResponse> createTinyUrl(@RequestBody @Validated UrlRequest urlRequest) {
        var url = urlRequest.getUrl();

        var shortenedURL = shortenUseCase.shorten(url);

        var response = new UrlResponse()
                .url(shortenedURL.originalURL())
                .tinyUrl(shortenedURL.tinyURL());

        return ResponseEntity
                .created(shortenedURL.tinyURL())
                .body(response);
    }
}
