package co.com.pragma.clientservice.infrastructure.utils;

import co.com.pragma.clientservice.domain.model.Client;
import co.com.pragma.clientservice.infrastructure.controller.dto.ClientWithImageDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class MapperImpl implements Mapper {
    ModelMapper modelMapper;

    @Override
    public Client clientWithImageDTOToClient(ClientWithImageDTO clientWithImageDTO) {
        return modelMapper.map(clientWithImageDTO, Client.class);
    }

    @Override
    public ClientWithImageDTO clientToClientWithImageDTO(Client client) {
        return modelMapper.map(client, ClientWithImageDTO.class);
    }

    @Override
    public List<ClientWithImageDTO> listClientToListClientWithImageDTO(List<Client> clients) {
        return clients.stream().map(this::clientToClientWithImageDTO)
                .collect(Collectors.toList());
    }
}
