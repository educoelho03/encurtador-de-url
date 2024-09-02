package tech.ecoelho.urlshortener.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ecoelho.urlshortener.domain.dto.ShortenUrlRequest;
import tech.ecoelho.urlshortener.domain.dto.ShortenUrlResponse;
import tech.ecoelho.urlshortener.domain.entity.UrlEntity;
import tech.ecoelho.urlshortener.service.UrlService;

import java.net.URI;
import java.util.Optional;

@RestController
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten-url")
    public ResponseEntity<ShortenUrlResponse> shortenUrl(@RequestBody ShortenUrlRequest request, HttpServletRequest servletRequest) {
        ShortenUrlResponse shortenUrlResponse = urlService.shortenUrl(request);
        String redirectUrl = servletRequest.getRequestURL().toString().replace("shorten-url", shortenUrlResponse.url());

        return ResponseEntity.ok(new ShortenUrlResponse(redirectUrl));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void> redirectTo(@PathVariable String id){
        Optional<UrlEntity> urlEntity = urlService.getUrlById(id);

        if(urlEntity.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(urlEntity.get().getFullUrl()));

        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }
}
