package co.com.pragma.clientservice.domain.usecase;

import co.com.pragma.clientservice.domain.model.Client;
import co.com.pragma.clientservice.domain.model.ClientImage;
import co.com.pragma.clientservice.domain.model.gateways.ClientImageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class CrudClientImageUseCase {

    ClientImageRepository clientImageRepository;

    public List<Client> getImagesToClients(List<Client> clients){
        return clients.stream().map(client -> {
            ClientImage image;
            image = clientImageRepository.getClientImage(client.getId());
            client.setImage(image);
            return client;
        }).collect(Collectors.toList());
    }

    public Client getImageToClient(Client client) {
        int id = client.getId();
        ClientImage image = clientImageRepository.getClientImage(id);
        client.setImage(image);
        return client;
    }

    public Client addImageToClient(Client client) {
        String imageBase64;
        if(client.isImageOk()) {
            imageBase64 = client.getImage().getBase64();
            clientImageRepository.setImageToClient(client.getId(), imageBase64);
        }

        return client;
    }

    public Client replaceImageToClient(Client client) {
        int id = client.getId();
        ClientImage image = clientImageRepository.getClientImage(id);
        // Si la imagen esta bien
        if(client.isImageOk()) {
            ClientImage clientImage;
            String base64 = client.getImage().getBase64();
            // No tengo una imagen entonces la creo
            if(image == null){
                clientImage = clientImageRepository.setImageToClient(id, base64);
            } else {
                // Si ya tengo una, la actualizo
                clientImage = clientImageRepository.updateClientImage(id, base64);
            }
            client.setImage(clientImage);
        } else client.setImage(image);
        return client;
    }

    public void deleteClientImage(int id) {
        try {
            clientImageRepository.deleteClientImage(id);
        }catch (Exception e){
            log.info("Error a obtener la imagen: " + e.getMessage());
        }
    }
}
