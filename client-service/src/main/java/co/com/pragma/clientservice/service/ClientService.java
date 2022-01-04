package co.com.pragma.clientservice.service;

import co.com.pragma.clientservice.exception.ClientNotFoundException;
import co.com.pragma.clientservice.feign.ClientImageFeign;
import co.com.pragma.clientservice.model.Client;
import co.com.pragma.clientservice.model.ClientImage;
import co.com.pragma.clientservice.model.ClientPayload;
import co.com.pragma.clientservice.repository.ClientRepository;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
@Log4j2
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientImageFeign clientImageFeign;

    public List<ClientPayload> getClients() {
        List<Client> clients = clientRepository.findAll();
        List <ClientPayload> clientsPayload = clients.stream().map(client -> {
            ClientImage clientImage;
            String imageBase64 = null;
            try {
                clientImage = clientImageFeign.getClientImage(client.getId());
                imageBase64 = clientImage.getBase64();
            }catch (Exception e){};
            return new ClientPayload(client, imageBase64);
        }).collect(Collectors.toList());
        return clientsPayload;
    }

    public ClientPayload getClient(int id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
        ClientImage clientImage = null;
        try {
            clientImage = clientImageFeign.getClientImage(client.getId());
        } catch (Exception e) {};
        return new ClientPayload(client, clientImage.getBase64());
    }

    public ClientPayload addClient(ClientPayload clientPayload) {
        Client newClient;
        int id;
        String imageBase64;

        clientPayload.setId(null);
        newClient = clientRepository.save(clientPayload.getClient());
        id = newClient.getId();

        imageBase64 = clientPayload.getImageBase64();
        clientImageFeign.setImageToClient(id, imageBase64);

        clientPayload.setClient(newClient);
        clientPayload.setImageBase64(imageBase64);

        return clientPayload;
    }

    public ClientPayload replaceClient(ClientPayload clientPayload, int id) {
        String imageBase64 = clientPayload.getImageBase64();

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
        // actualiza la imagen de mongo
        clientImageFeign.updateClientImage(id, imageBase64);
        // Actualizo el cliente encontrado
        client = clientRepository.save(client);
        // Actualizo la playload
        clientPayload.setClient(client);
        clientPayload.setImageBase64(imageBase64);
        return clientPayload;
    }

    public void deleteById(int id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
        clientRepository.delete(client);
        try {
            clientImageFeign.deleteClientImage(id);
        }catch (Exception e){};
    }
}
