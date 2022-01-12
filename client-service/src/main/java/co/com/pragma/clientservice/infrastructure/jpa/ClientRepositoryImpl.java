package co.com.pragma.clientservice.infrastructure.jpa;

import co.com.pragma.clientservice.domain.model.Client;
import co.com.pragma.clientservice.domain.model.exception.ApiRequestException;
import co.com.pragma.clientservice.domain.model.gateways.ClientRepository;
import co.com.pragma.clientservice.infrastructure.jpa.entities.ClientEntity;
import co.com.pragma.clientservice.infrastructure.jpa.repositories.ClientJpaRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
@Data
public class ClientRepositoryImpl implements ClientRepository {

    ClientJpaRepository clientJpaRepository;
    ModelMapper modelMapper;

    @Override
    public List<Client> getAll() {
        return clientJpaRepository.findAll().stream().map(client ->
            modelMapper.map(client, Client.class)
        ).collect(Collectors.toList());
    }

    @Override
    public Client read(int id) {
        Optional<ClientEntity> clientEntity = clientJpaRepository.findById(id);
        clientEntity.orElseThrow(() -> new ApiRequestException("No se encontro cliente", HttpStatus.NOT_FOUND));
        return modelMapper.map(clientEntity.get(), Client.class);
    }

    @Override
    public Client create(Client client) {
        ClientEntity newClientEntity = modelMapper.map(client, ClientEntity.class);
        newClientEntity = clientJpaRepository.save(newClientEntity);
        newClientEntity.setImage(client.getImage());
        return modelMapper.map(newClientEntity, Client.class);
    }

    @Override
    public Client update(int id, Client client) {
        client.setId(id);
        client = this.read(id);
        ClientEntity updateClientEntity = modelMapper.map(client, ClientEntity.class);
        updateClientEntity = clientJpaRepository.save(updateClientEntity);
        updateClientEntity.setImage(client.getImage());
        return modelMapper.map(updateClientEntity, Client.class);
    }

    @Override
    public void delete(int id) {
        this.read(id);
        clientJpaRepository.deleteById(id);
    }

}
