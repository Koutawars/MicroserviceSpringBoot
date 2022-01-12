package co.com.pragma.clientservice.domain.usecase;

import co.com.pragma.clientservice.domain.model.Client;
import co.com.pragma.clientservice.domain.model.gateways.ClientRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@AllArgsConstructor
public class CrudClientUseCase {
    private ClientRepository clientRepository;
    private CrudClientImageUseCase imageUseCase;

    public Client create(Client client) {
        Client created = clientRepository.create(client);
        return imageUseCase.addImageToClient(created);
    }

    public Client read(int id) {
        Client client = clientRepository.read(id);
        return imageUseCase.getImageToClient(client);
    }

    public Client update(int id, Client client) {
        Client clientUpdate = clientRepository.update(id, client);
        return imageUseCase.replaceImageToClient(clientUpdate, client.getImage().getBase64());
    }

    public void delete(int id) {
        clientRepository.delete(id);
        imageUseCase.deleteClientImage(id);
    }

    public List<Client> getAll() {
        List<Client> clients = clientRepository.getAll();
        return imageUseCase.getImagesToClients(clients);
    }
}
