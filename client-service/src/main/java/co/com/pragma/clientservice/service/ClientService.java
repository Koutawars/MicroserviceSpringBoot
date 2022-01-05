package co.com.pragma.clientservice.service;

import co.com.pragma.clientservice.exception.ClientNotFoundException;
import co.com.pragma.clientservice.feign.ClientImageFeign;
import co.com.pragma.clientservice.model.Client;
import co.com.pragma.clientservice.model.ClientImage;
import co.com.pragma.clientservice.model.ClientPayload;
import co.com.pragma.clientservice.repository.ClientRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientImageFeign clientImageFeign;

    public List<ClientPayload> getClients() {
        List<Client> clients = clientRepository.findAll();
        List <ClientPayload> clientsPayloads = clients.stream().map(client -> {
            ClientImage clientImage;
            String imageBase64 = null;
            try {
                clientImage = clientImageFeign.getClientImage(client.getId());
                imageBase64 = clientImage.getBase64();
            }catch (Exception e){};
            return new ClientPayload(client, imageBase64);
        }).collect(Collectors.toList());
        return clientsPayloads;
    }

    public ClientPayload getClient(int id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
        ClientImage clientImage = null;
        try {
            clientImage = clientImageFeign.getClientImage(client.getId());
        } catch (Exception e) {};

        if(clientImage == null){
            return new ClientPayload(client, null);
        }
        return new ClientPayload(client, clientImage.getBase64());
    }

    public ClientPayload addClient(ClientPayload clientPayload) {
        Client newClient;
        int id;
        String imageBase64;
        ClientImage clientImage;

        newClient = clientRepository.save(clientPayload.getClient());
        id = newClient.getId();

        imageBase64 = clientPayload.getImageBase64();
        if(imageBase64 != null) {
            if (!imageBase64.isEmpty()) {
                clientImage = clientImageFeign.setImageToClient(id, imageBase64);
                imageBase64 = clientImage.getBase64();
            }
        }

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
        client.updateClient(clientPayload.getClient());
        client = clientRepository.save(client);
        // Actualizo la playload
        clientPayload.setClient(client);
        clientPayload.setImageBase64(imageBase64);
        return clientPayload;
    }

    public boolean deleteById(int id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
        clientRepository.delete(client);
        try {
            clientImageFeign.deleteClientImage(id);
        }catch (Exception e){};
        return clientRepository.findById(id) == null;
    }
}
