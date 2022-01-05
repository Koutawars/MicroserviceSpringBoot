package co.com.pragma.clientservice.controller;

import co.com.pragma.clientservice.model.Client;
import co.com.pragma.clientservice.model.ClientPayload;
import co.com.pragma.clientservice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    List<ClientPayload> getClients() {
        return clientService.getClients();
    }

    @PostMapping
    ClientPayload newClient(@RequestBody ClientPayload client) {
        return clientService.addClient(client);
    }

    @GetMapping("{id}")
    ClientPayload getClient(@PathVariable int id) {
        return clientService.getClient(id);
    }

    @PutMapping("{id}")
    ClientPayload replaceClient(@RequestBody ClientPayload client, @PathVariable int id) {
        return clientService.replaceClient(client, id);
    }

    @DeleteMapping("{id}")
    void deleteClient(@PathVariable int id) {
        clientService.deleteById(id);
    }
}
