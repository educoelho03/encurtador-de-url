package tech.ecoelho.urlshortener.repository;

import tech.ecoelho.urlshortener.domain.entity.UrlEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends MongoRepository<UrlEntity, String> {
}
