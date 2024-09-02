package tech.ecoelho.urlshortener.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import tech.ecoelho.urlshortener.domain.dto.ShortenUrlRequest;
import tech.ecoelho.urlshortener.domain.dto.ShortenUrlResponse;
import tech.ecoelho.urlshortener.domain.entity.UrlEntity;
import tech.ecoelho.urlshortener.repository.UrlRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public ShortenUrlResponse shortenUrl(ShortenUrlRequest request) {
        String id;
        do {
            id = RandomStringUtils.randomAlphanumeric(5, 10);
        } while (urlRepository.existsById(id));

        urlRepository.save(new UrlEntity(id, request.url(), LocalDateTime.now().plusMinutes(1)));
        return new ShortenUrlResponse(id);
    }

    public Optional<UrlEntity> getUrlById(String id) {
        return urlRepository.findById(id);
    }
}
