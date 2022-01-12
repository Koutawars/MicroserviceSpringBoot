package co.com.pragma.clientservice.domain.model.gateways;

import co.com.pragma.clientservice.domain.model.Client;

import java.util.List;

public interface ClientRepository {
    List<Client> getAll();

    Client read(int id);

    Client create(Client client);

    Client update(int id, Client client);

    void delete(int id);
}
