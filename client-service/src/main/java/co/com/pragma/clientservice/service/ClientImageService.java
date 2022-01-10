package co.com.pragma.clientservice.service;

import co.com.pragma.clientservice.dto.ClientWithImageDTO;
import co.com.pragma.clientservice.feign.ClientImageFeign;
import co.com.pragma.clientservice.model.Client;
import co.com.pragma.clientservice.model.ClientImage;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClientImageService {
    private static ClientImageFeign clientImageFeign;
    private ModelMapper modelMapper;
    private CircuitBreakerFactory circuitBreakerFactory;

    @Autowired
    public ClientImageService(ClientImageFeign clientImageFeign, ModelMapper modelMapper,
                              CircuitBreakerFactory circuitBreakerFactory) {
        this.clientImageFeign = clientImageFeign;
        this.modelMapper = modelMapper;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    public List<ClientWithImageDTO> getImagesToClients(List<Client> clients){
        return clients.stream().map(client -> {
            ClientImage image;
            image = circuitBreakerFactory.create("getAClient")
                    .run(() -> clientImageFeign.getClientImage(client.getId()),
                            throwable -> new ClientImage());
            client.setImage(image);
            return modelMapper.map(client, ClientWithImageDTO.class);
        }).collect(Collectors.toList());
    }

    public ClientWithImageDTO getImageToClient(Client client) {
        ClientImage image;
        int id = client.getId();
        Supplier<ClientImage> supplierClient = () -> clientImageFeign.getClientImage(id);
        image = circuitBreakerFactory.create("getAClient")
                .run(supplierClient, throwable -> new ClientImage());
        client.setImage(image);
        return modelMapper.map(client, ClientWithImageDTO.class);
    }

    public ClientWithImageDTO addImageToClient(Client client, String imageBase64) {
        ClientWithImageDTO clientWithImageDTO;
        ClientImage clientImage;
        if(imageBase64 != null) {
            if (!imageBase64.isEmpty()) {
                clientImage = clientImageFeign.setImageToClient(client.getId(), imageBase64);
                imageBase64 = clientImage.getBase64();
            }
        }
        clientWithImageDTO = modelMapper.map(client, ClientWithImageDTO.class);
        clientWithImageDTO.setImageBase64(imageBase64);

        return clientWithImageDTO;
    }

    public ClientWithImageDTO replaceImageToClient(Client client, String imageBase64) {
        int id = client.getId();
        ClientWithImageDTO clientWithImageDTO = modelMapper.map(client, ClientWithImageDTO.class);
        clientImageFeign.updateClientImage(id, imageBase64);
        clientWithImageDTO.setId(id);
        return clientWithImageDTO;
    }

    public void deleteClientImage(int id) {
        try {
            clientImageFeign.deleteClientImage(id);
        }catch (Exception e){
            log.info("Error a obtener la imagen: " + e.getMessage());
        }
    }
}
