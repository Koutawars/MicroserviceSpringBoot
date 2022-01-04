package co.com.pragma.clientimageservice.service;

import co.com.pragma.clientimageservice.exception.ClientImageNotFoundException;
import co.com.pragma.clientimageservice.exception.ClientImageRepeatException;
import co.com.pragma.clientimageservice.model.ClientImage;
import co.com.pragma.clientimageservice.repository.ClientImageRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class ClientImageService {

    @Autowired
    ClientImageRepository repository;

    public ClientImage getClientImageByClientId(Integer clientId) {
        return repository.findClientImageByClientId(clientId)
                .orElseThrow(() ->  new ClientImageNotFoundException(clientId));
    }

    public ClientImage setImageToClient(Integer clientId, String imageBase64) {
        boolean isEmptyImage = repository.findClientImageByClientId(clientId)
                .isEmpty();
        if(isEmptyImage) {
            return repository.insert(new ClientImage(clientId, imageBase64));
        }
        throw new ClientImageRepeatException(clientId, imageBase64);
    }

    public ClientImage updateClientImage(Integer clientId, String imageBase64) {
        AtomicReference<ClientImage> image = new AtomicReference<>();
        repository.findClientImageByClientId(clientId).ifPresentOrElse(
                clientImage -> {
                    clientImage.setBase64(imageBase64);
                    image.set(repository.save(clientImage));
                },
                () -> {
                    ClientImage newImage = new ClientImage(clientId, imageBase64);
                    image.set(repository.insert(newImage));
                }
        );

        return image.get();
    }

    public void deleteClientImage(Integer clientId) {
        repository.findClientImageByClientId(clientId).ifPresentOrElse(
                clientImage -> {
                    repository.delete(clientImage);
                },
                () -> {
                    throw new ClientImageNotFoundException(clientId);
                }
        );
    }
}
