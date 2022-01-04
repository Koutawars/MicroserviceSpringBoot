package co.com.pragma.clientimageservice.repository;

import co.com.pragma.clientimageservice.model.ClientImage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientImageRepository extends MongoRepository<ClientImage, String> {
    Optional<ClientImage> findClientImageByClientId(Integer clientId);
}
