package co.com.pragma.clientservice.service;

import co.com.pragma.clientservice.feign.ClientImageFeign;
import co.com.pragma.clientservice.model.Client;
import co.com.pragma.clientservice.model.ClientImage;
import co.com.pragma.clientservice.model.ClientPayload;
import co.com.pragma.clientservice.model.TypeIDNumber;
import co.com.pragma.clientservice.repository.ClientRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

class ClientServiceTest {

    final String image64 = "data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz4KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDIwLjEuMCwgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPgo8c3ZnIHZlcnNpb249IjEuMSIgaWQ9IkNhcGFfMSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayIgeD0iMHB4IiB5PSIwcHgiCgkgdmlld0JveD0iMCAwIDUxMiA1MTIiIHN0eWxlPSJlbmFibGUtYmFja2dyb3VuZDpuZXcgMCAwIDUxMiA1MTI7IiB4bWw6c3BhY2U9InByZXNlcnZlIj4KPHN0eWxlIHR5cGU9InRleHQvY3NzIj4KCS5zdDB7ZmlsbDojOUJBQkM5O30KPC9zdHlsZT4KPGc+Cgk8cGF0aCBjbGFzcz0ic3QwIiBkPSJNNDE2LDM1MmgtMzJjLTM1LjMsMC02NC0yOC43LTY0LTY0di0yMC42YzE0LjItMTYuOCwyNC4zLTM2LjcsMzAuNi01Ny44YzAuNy0zLjUsNC4xLTUuMyw2LjMtNy43CgkJYzEyLjMtMTIuMiwxNC43LTMyLjksNS41LTQ3LjZjLTEuMy0yLjItMy41LTQuMi0zLjQtNi45YzAtMTguOCwwLjEtMzcuNiwwLTU2LjNjLTAuNS0yMi42LTctNDYuMi0yMi44LTYzCgkJQzMyMy40LDE0LjUsMzA1LjgsNi41LDI4Ny42LDNjLTIyLjktNC40LTQ2LjktNC4yLTY5LjcsMS42Yy0xOS43LDUtMzguMiwxNi41LTQ5LjYsMzMuNmMtMTAuMSwxNC45LTE0LjYsMzIuOS0xNS4zLDUwLjcKCQljLTAuMywxOS4xLTAuMSwzOC4yLTAuMSw1Ny40YzAuNCwzLjgtMi44LDYuNC00LjMsOS42Yy04LjcsMTUuNy00LjgsMzcuMSw5LjEsNDguNWMzLjUsMi40LDQuMiw2LjksNS41LDEwLjcKCQljNi4xLDE4LjksMTYuMSwzNi40LDI4LjgsNTEuNlYyODhjMCwzNS4zLTI4LjcsNjQtNjQsNjRIOTZjMCwwLTU4LDE2LTk2LDk2djMyYzAsMTcuNywxNC4zLDMyLDMyLDMyaDQ0OGMxNy43LDAsMzItMTQuMywzMi0zMnYtMzIKCQlDNDc0LDM2OCw0MTYsMzUyLDQxNiwzNTJ6Ii8+CjwvZz4KPC9zdmc+Cg==";
    final String image64Change = "data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz4KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDIwLjEuMCwgU1ZHIEV4cG9ydCBQbHVnLUluIC656sdfaslcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPgo8c3ZnIHZlcnNpb249IjEuMSIgaWQ9IkNhcGFfMSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayIgeD0iMHB4IiB5PSIwcHgiCgkgdmlld0JveD0iMCAwIDUxMiA1MTIiIHN0eWxlPSJlbmFibGUtYmFja2dyb3VuZDpuZXcgMCAwIDUxMiA1MTI7IiB4bWw6c3BhY2U9InByZXNlcnZlIj4KPHN0eWxlIHR5cGU9InRleHQvY3NzIj4KCS5zdDB7ZmlsbDojOUJBQkM5O30KPC9zdHlsZT4KPGc+Cgk8cGF0aCBjbGFzcz0ic3QwIiBkPSJNNDE2LDM1MmgtMzJjLTM1LjMsMC02NC0yOC43LTY0LTY0di0yMC42YzE0LjItMTYuOCwyNC4zLTM2LjcsMzAuNi01Ny44YzAuNy0zLjUsNC4xLTUuMyw2LjMtNy43CgkJYzEyLjMtMTIuMiwxNC43LTMyLjksNS41LTQ3LjZjLTEuMy0yLjItMy41LTQuMi0zLjQtNi45YzAtMTguOCwwLjEtMzcuNiwwLTU2LjNjLTAuNS0yMi42LTctNDYuMi0yMi44LTYzCgkJQzMyMy40LDE0LjUsMzA1LjgsNi41LDI4Ny42LDNjLTIyLjktNC40LTQ2LjktNC4yLTY5LjcsMS42Yy0xOS43LDUtMzguMiwxNi41LTQ5LjYsMzMuNmMtMTAuMSwxNC45LTE0LjYsMzIuOS0xNS4zLDUwLjcKCQljLTAuMywxOS4xLTAuMSwzOC4yLTAuMSw1Ny40YzAuNCwzLjgtMi44LDYuNC00LjMsOS42Yy04LjcsMTUuNy00LjgsMzcuMSw5LjEsNDguNWMzLjUsMi40LDQuMiw2LjksNS41LDEwLjcKCQljNi4xLDE4LjksMTYuMSwzNi40LDI4LjgsNTEuNlYyODhjMCwzNS4zLTI4LjcsNjQtNjQsNjRIOTZjMCwwLTU4LDE2LTk2LDk2djMyYzAsMTcuNywxNC4zLDMyLDMyLDMyaDQ0OGMxNy43LDAsMzItMTQuMywzMi0zMnYtMzIKCQlDNDc0LDM2OCw0MTYsMzUyLDQxNiwzNTJ6Ii8+CjwvZz4KPC9zdmc+Cg==";

    private ClientRepository clientRepositoryMock = Mockito.mock(ClientRepository.class);
    private ClientImageFeign clientImageFeignMock = Mockito.mock(ClientImageFeign.class);

    @Autowired
    private ClientService clientService = new ClientService(clientRepositoryMock, clientImageFeignMock);

    @BeforeEach
    void setUp() {
        // SQL Test
        Client client0 = new Client(0, "Prueba nombre", "Prueba apellido",
                20, "Santa Marta", TypeIDNumber.TI, 123456);
        Client client1 = new Client(1, "Prueba nombre1", "Prueba apellido1",
                20, "Santa Marta", TypeIDNumber.CC, 123456);
        Client client0New = new Client(null, "Prueba nombre", "Prueba apellido",
                20, "Santa Marta", TypeIDNumber.TI, 123456);
        Client client1New = new Client(null, "Prueba nombre1", "Prueba apellido1",
                20, "Santa Marta", TypeIDNumber.CC, 123456);
        Client client0Update = new Client(0, "Prueba nombre actualizado", "Prueba apellido",
                20, "Cartagena", TypeIDNumber.TI, 123456);
        Client client1Update = new Client(1, "Prueba nombre actualizado", "Prueba apellido1",
                20, "Cartagena", TypeIDNumber.CC, 123456);

        List<Client> mockClients = List.of(
                client0,
                client1
        );
        // find
        Mockito.when(clientRepositoryMock.findAll()).thenReturn(mockClients);
        Mockito.when(clientRepositoryMock.findById(client0.getId())).thenReturn(Optional.of(client0));
        Mockito.when(clientRepositoryMock.findById(client1.getId())).thenReturn(Optional.of(client1));
        // save
        Mockito.when(clientRepositoryMock.save(client0New)).thenReturn(client0);
        // update
        Mockito.when(clientRepositoryMock.save(client0Update)).thenReturn(client0Update);

        // mongodb test

        ClientImage clientImage0 = new ClientImage("61d4a7871bffc47fab13dab1", 0, image64);
        ClientImage clientImage1 = new ClientImage("61d4a7871bffc47fab13dab1", 1, null);
        ClientImage clientImage0Update = new ClientImage("61d4a7871bffc47fab13dab1", 0, image64Change);

        Mockito.when(clientImageFeignMock.getClientImage(0))
                .thenReturn(clientImage0);
        Mockito.when(clientImageFeignMock.getClientImage(1))
                .thenReturn(clientImage1);

        Mockito.when(clientImageFeignMock.setImageToClient(0, image64))
                .thenReturn(clientImage0);

        Mockito.when(clientImageFeignMock.updateClientImage(0, image64Change))
                .thenReturn(clientImage0Update);

    }

    @Test
    void getClients() {
        List<ClientPayload> clientPayloads = clientService.getClients();
        assertEquals(0, clientPayloads.get(0).getId());
        assertEquals(image64, clientPayloads.get(0).getImageBase64());
        assertEquals(1, clientPayloads.get(1).getId());
        assertNull(clientPayloads.get(1).getImageBase64());
    }

    @Test
    void getClientWithNullImage() {
        ClientPayload clientPayload0 = clientService.getClient(0);
        assertEquals(clientPayload0.getNames(), "Prueba nombre");
        assertEquals(clientPayload0.getImageBase64(), image64);
    }

    @Test
    void getClientWithImage() {
        ClientPayload clientPayload0 = clientService.getClient(1);
        assertEquals(clientPayload0.getNames(), "Prueba nombre1");
        assertNull(clientPayload0.getImageBase64());
    }

    @Test
    void addClient() {
        ClientPayload clientPayloadNew = new ClientPayload(null, "Prueba nombre", "Prueba apellido",
                20, "Santa Marta", TypeIDNumber.TI, 123456, image64);
        ClientPayload clientPayloadSave = clientService.addClient(clientPayloadNew);
        assertEquals(clientPayloadSave.getId(), clientPayloadNew.getId());
    }

    @Test
    void replaceClient() {
        ClientPayload clientPayloadParameter = new ClientPayload(0, "Prueba nombre actualizado", "Prueba apellido",
                20, "Cartagena", TypeIDNumber.TI, 123456, image64);

        ClientPayload clientPayload = clientService.replaceClient(clientPayloadParameter, 0);
        assertEquals(clientPayload.getCity(), clientPayloadParameter.getCity());
    }

    @Test
    void deleteById() {
        Client client0 = new Client(0, "Prueba nombre", "Prueba apellido",
                20, "Santa Marta", TypeIDNumber.TI, 123456);

        Mockito.when(clientRepositoryMock.findById(0)).thenReturn(Optional.of(client0)).thenReturn(null);

        final boolean result = clientService.deleteById(0);

        Mockito.verify(clientRepositoryMock, times(1))
                .delete(client0);

        assertEquals(result, true);
    }
}