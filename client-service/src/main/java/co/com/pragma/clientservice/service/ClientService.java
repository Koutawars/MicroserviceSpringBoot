package co.com.pragma.clientservice.service;

import co.com.pragma.clientservice.dto.ClientWithImageDTO;
import co.com.pragma.clientservice.exception.ClientCustomException;
import co.com.pragma.clientservice.feign.ClientImageFeign;
import co.com.pragma.clientservice.model.Client;
import co.com.pragma.clientservice.model.ClientImage;
import co.com.pragma.clientservice.repository.ClientRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class ClientService {

    private ClientRepository clientRepository;
    private ModelMapper modelMapper;
    private ClientImageService clientImageService;

    @Autowired
    private ClientCustomException clientCustomException;

    @Autowired
    public ClientService(ClientRepository clientRepository,
                         ModelMapper modelMapper, ClientImageService clientImageService) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
        this.clientImageService = clientImageService;
    }

    public List<ClientWithImageDTO> getClients() {
        List<Client> clients = clientRepository.findAll();
        return clientImageService.getImagesToClients(clients);
    }

    public ClientWithImageDTO getClient(int id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> clientCustomException.statusNotFound(id));
        return clientImageService.getImageToClient(client);

    }

    public ClientWithImageDTO addClient(ClientWithImageDTO clientWithImageDTO) {
        String imageBase64 = clientWithImageDTO.getImageBase64();
        Client client = modelMapper.map(clientWithImageDTO, Client.class);

        client = clientRepository.save(client);

        return clientImageService.addImageToClient(client, imageBase64);
    }

    public ClientWithImageDTO replaceClient(ClientWithImageDTO clientWithImageDTO, int id) {
        String imageBase64 = clientWithImageDTO.getImageBase64();
        Client client = modelMapper.map(clientWithImageDTO, Client.class);
        client.setId(id);

        clientRepository.findById(id).orElseThrow(() -> clientCustomException.statusNotFound(id));
        clientRepository.save(client);

        return clientImageService.replaceImageToClient(client, imageBase64);
    }

    public boolean deleteById(int id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> clientCustomException.statusNotFound(id));
        clientRepository.delete(client);
        clientImageService.deleteClientImage(id);
        return clientRepository.findById(id) == null;
    }
}
