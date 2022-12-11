package com.github.fwfurtado.tinyurl.transport.http;

import com.github.fwfurtado.tinyurl.core.models.ShortenedURL;
import com.github.fwfurtado.tinyurl.core.usecases.FindOriginalURLUseCase;
import com.github.tinyurl.transport.http.IdApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class IdEndpoint implements IdApi {

    private final FindOriginalURLUseCase useCase;

    public IdEndpoint(FindOriginalURLUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    @GetMapping("{id}")
    public ResponseEntity<?> findOriginalUrl(@PathVariable String id) {

        return useCase.find(id)
                .map(ShortenedURL::originalURL)
                .map(uri -> ResponseEntity.status(HttpStatus.FOUND).location(uri).build())
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }
}
