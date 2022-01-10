package co.com.pragma.clientservice.service;

import co.com.pragma.clientservice.dto.ClientWithImageDTO;
import co.com.pragma.clientservice.feign.ClientImageFeign;
import co.com.pragma.clientservice.model.Client;
import co.com.pragma.clientservice.model.ClientImage;
import co.com.pragma.clientservice.model.TypeIDNumber;
import co.com.pragma.clientservice.repository.ClientRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClientServiceTest {

    final String image64 = "data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz4KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDIwLjEuMCwgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPgo8c3ZnIHZlcnNpb249IjEuMSIgaWQ9IkNhcGFfMSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayIgeD0iMHB4IiB5PSIwcHgiCgkgdmlld0JveD0iMCAwIDUxMiA1MTIiIHN0eWxlPSJlbmFibGUtYmFja2dyb3VuZDpuZXcgMCAwIDUxMiA1MTI7IiB4bWw6c3BhY2U9InByZXNlcnZlIj4KPHN0eWxlIHR5cGU9InRleHQvY3NzIj4KCS5zdDB7ZmlsbDojOUJBQkM5O30KPC9zdHlsZT4KPGc+Cgk8cGF0aCBjbGFzcz0ic3QwIiBkPSJNNDE2LDM1MmgtMzJjLTM1LjMsMC02NC0yOC43LTY0LTY0di0yMC42YzE0LjItMTYuOCwyNC4zLTM2LjcsMzAuNi01Ny44YzAuNy0zLjUsNC4xLTUuMyw2LjMtNy43CgkJYzEyLjMtMTIuMiwxNC43LTMyLjksNS41LTQ3LjZjLTEuMy0yLjItMy41LTQuMi0zLjQtNi45YzAtMTguOCwwLjEtMzcuNiwwLTU2LjNjLTAuNS0yMi42LTctNDYuMi0yMi44LTYzCgkJQzMyMy40LDE0LjUsMzA1LjgsNi41LDI4Ny42LDNjLTIyLjktNC40LTQ2LjktNC4yLTY5LjcsMS42Yy0xOS43LDUtMzguMiwxNi41LTQ5LjYsMzMuNmMtMTAuMSwxNC45LTE0LjYsMzIuOS0xNS4zLDUwLjcKCQljLTAuMywxOS4xLTAuMSwzOC4yLTAuMSw1Ny40YzAuNCwzLjgtMi44LDYuNC00LjMsOS42Yy04LjcsMTUuNy00LjgsMzcuMSw5LjEsNDguNWMzLjUsMi40LDQuMiw2LjksNS41LDEwLjcKCQljNi4xLDE4LjksMTYuMSwzNi40LDI4LjgsNTEuNlYyODhjMCwzNS4zLTI4LjcsNjQtNjQsNjRIOTZjMCwwLTU4LDE2LTk2LDk2djMyYzAsMTcuNywxNC4zLDMyLDMyLDMyaDQ0OGMxNy43LDAsMzItMTQuMywzMi0zMnYtMzIKCQlDNDc0LDM2OCw0MTYsMzUyLDQxNiwzNTJ6Ii8+CjwvZz4KPC9zdmc+Cg==";
    final String image64Change = "data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz4KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDIwLjEuMCwgU1ZHIEV4cG9ydCBQbHVnLUluIC656sdfaslcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPgo8c3ZnIHZlcnNpb249IjEuMSIgaWQ9IkNhcGFfMSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayIgeD0iMHB4IiB5PSIwcHgiCgkgdmlld0JveD0iMCAwIDUxMiA1MTIiIHN0eWxlPSJlbmFibGUtYmFja2dyb3VuZDpuZXcgMCAwIDUxMiA1MTI7IiB4bWw6c3BhY2U9InByZXNlcnZlIj4KPHN0eWxlIHR5cGU9InRleHQvY3NzIj4KCS5zdDB7ZmlsbDojOUJBQkM5O30KPC9zdHlsZT4KPGc+Cgk8cGF0aCBjbGFzcz0ic3QwIiBkPSJNNDE2LDM1MmgtMzJjLTM1LjMsMC02NC0yOC43LTY0LTY0di0yMC42YzE0LjItMTYuOCwyNC4zLTM2LjcsMzAuNi01Ny44YzAuNy0zLjUsNC4xLTUuMyw2LjMtNy43CgkJYzEyLjMtMTIuMiwxNC43LTMyLjksNS41LTQ3LjZjLTEuMy0yLjItMy41LTQuMi0zLjQtNi45YzAtMTguOCwwLjEtMzcuNiwwLTU2LjNjLTAuNS0yMi42LTctNDYuMi0yMi44LTYzCgkJQzMyMy40LDE0LjUsMzA1LjgsNi41LDI4Ny42LDNjLTIyLjktNC40LTQ2LjktNC4yLTY5LjcsMS42Yy0xOS43LDUtMzguMiwxNi41LTQ5LjYsMzMuNmMtMTAuMSwxNC45LTE0LjYsMzIuOS0xNS4zLDUwLjcKCQljLTAuMywxOS4xLTAuMSwzOC4yLTAuMSw1Ny40YzAuNCwzLjgtMi44LDYuNC00LjMsOS42Yy04LjcsMTUuNy00LjgsMzcuMSw5LjEsNDguNWMzLjUsMi40LDQuMiw2LjksNS41LDEwLjcKCQljNi4xLDE4LjksMTYuMSwzNi40LDI4LjgsNTEuNlYyODhjMCwzNS4zLTI4LjcsNjQtNjQsNjRIOTZjMCwwLTU4LDE2LTk2LDk2djMyYzAsMTcuNywxNC4zLDMyLDMyLDMyaDQ0OGMxNy43LDAsMzItMTQuMywzMi0zMnYtMzIKCQlDNDc0LDM2OCw0MTYsMzUyLDQxNiwzNTJ6Ii8+CjwvZz4KPC9zdmc+Cg==";


    @Mock
    private ClientRepository clientRepositoryMock;
    @Mock
    private ClientImageFeign clientImageFeignMock;

    private ModelMapper modelMapper;
    private ClientService clientService;
    private ClientImageService clientImageService;

    @BeforeAll
    void setUpAll(){
        MockitoAnnotations.initMocks(this);
        modelMapper = new ModelMapper();
        clientImageService = new ClientImageService(clientImageFeignMock, modelMapper, null);
        clientService = new ClientService(clientRepositoryMock,
                 modelMapper, clientImageService);
    }

    @BeforeEach
    void setUp() {
        // SQL Test
        ClientImage image0 = new ClientImage("0", 0, image64);
        ClientImage image0Update = new ClientImage("61d4a7871bffc47fab13dab1", 0, image64Change);

        Client client0 = new Client(0, "Prueba nombre", "Prueba apellido",
                20, "Santa Marta", TypeIDNumber.TI, 123456, image0);
        Client client1 = new Client(1, "Prueba nombre1", "Prueba apellido1",
                20, "Santa Marta", TypeIDNumber.CC, 123456, null);
        Client client0New = new Client(null, "Prueba nombre", "Prueba apellido",
                20, "Santa Marta", TypeIDNumber.TI, 123456, image0);
        Client client0Update = new Client(0, "Prueba nombre actualizado", "Prueba apellido",
                20, "Cartagena", TypeIDNumber.TI, 123456, image0Update);

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

        Mockito.when(clientImageFeignMock.getClientImage(0))
                .thenReturn(image0);
        Mockito.when(clientImageFeignMock.getClientImage(1))
                .thenReturn(null);
        Mockito.when(clientImageFeignMock.setImageToClient(0, image64))
                .thenReturn(image0);
        Mockito.when(clientImageFeignMock.updateClientImage(0, image64Change))
                .thenReturn(image0Update);

    }

    @Test
    void getClients() {
        List<ClientWithImageDTO> clientWithImageDTOs = clientService.getClients();
        assertEquals(0, clientWithImageDTOs.get(0).getId());
        assertEquals(image64, clientWithImageDTOs.get(0).getImageBase64());
        assertEquals(1, clientWithImageDTOs.get(1).getId());
        assertNull(clientWithImageDTOs.get(1).getImageBase64());
    }

    @Test
    void getClientWithNullImage() {
        ClientWithImageDTO clientWithImageDTO = clientService.getClient(0);
        assertEquals(clientWithImageDTO.getNames(), "Prueba nombre");
        assertEquals(clientWithImageDTO.getImageBase64(), image64);
    }

    @Test
    void getClientWithImage() {
        ClientWithImageDTO clientWithImageDTO = clientService.getClient(1);
        assertEquals(clientWithImageDTO.getNames(), "Prueba nombre1");
        assertNull(clientWithImageDTO.getImageBase64());
    }

    @Test
    void addClient() {
        ClientWithImageDTO clientWithImageDTO = new ClientWithImageDTO(0,"Prueba nombre", "Prueba apellido",
                20, "Santa Marta", TypeIDNumber.TI, 123456, image64);
        ClientWithImageDTO clientPayloadSave = clientService.addClient(clientWithImageDTO);
        assertEquals(clientPayloadSave.getId(), clientWithImageDTO.getId());
    }
    
    @Test
    void DTOaImagen(){
        ClientWithImageDTO clientWithImageDTO = new ClientWithImageDTO(0,"Prueba nombre", "Prueba apellido",
                20, "Santa Marta", TypeIDNumber.TI, 123456, image64);
        Client client = modelMapper.map(clientWithImageDTO, Client.class);
        assertEquals(clientWithImageDTO.getId(), client.getId());

    }

    @Test
    void replaceClient() {
        ClientWithImageDTO clientWithImageDTO = new ClientWithImageDTO(0, "Prueba nombre actualizado", "Prueba apellido",
                20, "Cartagena", TypeIDNumber.TI, 123456, image64);

        clientWithImageDTO = clientService.replaceClient(clientWithImageDTO, 0);
        assertEquals(clientWithImageDTO.getCity(), "Cartagena");
    }

    @Test
    void deleteById() {

        Client client0 = new Client(0, "Prueba nombre", "Prueba apellido",
                20, "Santa Marta", TypeIDNumber.TI, 123456, null);

        Mockito.when(clientRepositoryMock.findById(0)).thenReturn(Optional.of(client0)).thenReturn(null);

        final boolean result = clientService.deleteById(0);

        Mockito.verify(clientRepositoryMock, times(1))
                .delete(client0);

        assertEquals(result, true);
    }
}