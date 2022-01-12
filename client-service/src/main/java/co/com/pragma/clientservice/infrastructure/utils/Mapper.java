package co.com.pragma.clientservice.infrastructure.utils;


import co.com.pragma.clientservice.domain.model.Client;
import co.com.pragma.clientservice.infrastructure.controller.dto.ClientWithImageDTO;

import java.util.List;

public interface Mapper {
    Client clientWithImageDTOToClient(ClientWithImageDTO clientWithImageDTO);
    ClientWithImageDTO clientToClientWithImageDTO(Client client);
    List<ClientWithImageDTO> listClientToListClientWithImageDTO(List<Client> clients);
}
