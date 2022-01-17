package co.com.pragma.clientimageservice.controller;

import co.com.pragma.clientimageservice.model.ClientImage;
import co.com.pragma.clientimageservice.repository.ClientImageRepository;
import co.com.pragma.clientimageservice.service.ClientImageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ClientImageControllerTest {

    @Mock
    ClientImageRepository clientImageRepositoryMock;

    @InjectMocks
    ClientImageService clientImageService = new ClientImageService(clientImageRepositoryMock);

    @InjectMocks
    ClientImageController clientImageController = new ClientImageController(clientImageService);

    @Test
    void getClientImage() {
        ClientImage image = new ClientImage(0, "imagen");
        Mockito.when(clientImageRepositoryMock.findClientImageByClientId(0)).thenReturn(Optional.of(image));

        ClientImage newImage = clientImageController.getClientImage(0);
        assertEquals(image.getBase64(), newImage.getBase64());
    }

    @Test
    void setImageToClient() {
        ClientImage image = new ClientImage(0, "imagen");
        // Mockito.when(clientImageRepositoryMock.findClientImageByClientId(0)).thenReturn(Optional.of(image));
        Mockito.when(clientImageRepositoryMock.insert(image)).thenReturn(image);

        ClientImage newImage = clientImageController.setImageToClient(0, "imagen");
        assertEquals(image.getBase64(), newImage.getBase64());
    }

    @Test
    void updateClientImage() {

    }

    @Test
    void deleteClientImage() {
    }
}