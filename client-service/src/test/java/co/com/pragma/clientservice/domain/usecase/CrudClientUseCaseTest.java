package co.com.pragma.clientservice.domain.usecase;

import co.com.pragma.clientservice.domain.model.Client;
import co.com.pragma.clientservice.domain.model.ClientImage;
import co.com.pragma.clientservice.domain.model.TypeIDNumber;
import co.com.pragma.clientservice.infrastructure.adapters.feign.ClientImageFeignRepository;
import co.com.pragma.clientservice.infrastructure.jpa.ClientRepositoryImpl;
import co.com.pragma.clientservice.infrastructure.jpa.entities.ClientEntity;
import co.com.pragma.clientservice.infrastructure.jpa.repositories.ClientJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CrudClientUseCaseTest {


    @MockBean
    ModelMapper modelMapper = new ModelMapper();

    @Mock
    ClientJpaRepository clientJpaRepositoryMock;

    @Mock
    ClientImageFeignRepository clientImageFeignRepositoryMock;

    @InjectMocks
    ClientRepositoryImpl clientRepositoryImpl = new ClientRepositoryImpl(clientJpaRepositoryMock, modelMapper);

    @InjectMocks
    private CrudClientImageUseCase imageUseCase = new CrudClientImageUseCase(clientImageFeignRepositoryMock);

    @InjectMocks
    private CrudClientUseCase crudClientUseCase = new CrudClientUseCase(clientRepositoryImpl, imageUseCase);


    @Test
    void create() {
        String image64 = "Imagen sorpresa";
        ClientImage image0 = new ClientImage("0", 0, image64);
        ClientEntity clientEntity0 = new ClientEntity(null, "Prueba nombre", "Prueba apellido",
                20, "Santa Marta", TypeIDNumber.TI, 123456, image0);
        ClientEntity newClientEntity = new ClientEntity(0, "Prueba nombre", "Prueba apellido",
                20, "Santa Marta", TypeIDNumber.TI, 123456, image0);
        Mockito.when(clientJpaRepositoryMock.save(clientEntity0)).thenReturn(newClientEntity);
        Mockito.when(clientImageFeignRepositoryMock.setImageToClient(0, image64)).thenReturn(image0);

        // post (create)
        Client newClient = new Client(null, "Prueba nombre", "Prueba apellido",
                20, "Santa Marta", TypeIDNumber.TI, 123456, image0);
        newClient = crudClientUseCase.create(newClient); // clientRepository.create(newClient);
        assertEquals(20, newClient.getAge());
        assertEquals(image64, newClient.getImage().getBase64());
    }

    @Test
    void read() {
        // config mockito
        String image64 = "Imagen sorpresa";
        ClientImage image0 = new ClientImage("0", 0, image64);
        ClientEntity newClientEntity = new ClientEntity(0, "Prueba nombre", "Prueba apellido",
                20, "Santa Marta", TypeIDNumber.TI, 123456, image0);

        Mockito.when(clientJpaRepositoryMock.findById(0)).thenReturn(Optional.of(newClientEntity));
        Mockito.when(clientImageFeignRepositoryMock.getClientImage(0)).thenReturn(image0);


        // test
        Client client = crudClientUseCase.read(0);
        assertEquals(0, client.getId());
        assertEquals(20, client.getAge());
    }

    @Test
    void update() {
        String image64 = "Imagen sorpresa";
        ClientImage image0 = new ClientImage("0", 0, image64);
        ClientImage image1 = new ClientImage("0", 1, image64);

        ClientEntity clientEntity0 = new ClientEntity(0, "Prueba", "Prueba apellido",
                22, "Santa Marta", TypeIDNumber.TI, 1236, image0);
        ClientEntity clientEntity1 = new ClientEntity(1, "Prueba", "Prueba apellido",
                22, "Santa Marta", TypeIDNumber.TI, 1236, image1);

        ClientEntity oldClientEntity = new ClientEntity(0, "Prueba nombre", "Prueba apellido",
                20, "Santa Marta", TypeIDNumber.TI, 123456, image0);
        ClientEntity oldClientEntity1 = new ClientEntity(1, "Prueba 1", "Prueba apellido",
                20, "Santa Marta", TypeIDNumber.TI, 123456, null);

        Mockito.when(clientJpaRepositoryMock.findById(0)).thenReturn(Optional.of(oldClientEntity));
        Mockito.when(clientJpaRepositoryMock.save(clientEntity0)).thenReturn(clientEntity0);
        Mockito.when(clientJpaRepositoryMock.findById(1)).thenReturn(Optional.of(oldClientEntity1));
        Mockito.when(clientJpaRepositoryMock.save(clientEntity1)).thenReturn(clientEntity1);
        Mockito.when(clientImageFeignRepositoryMock.setImageToClient(1, image64)).thenReturn(image1);
        Mockito.when(clientImageFeignRepositoryMock.updateClientImage(0, image64)).thenReturn(image0);

        Client client0 = new Client(0, "Prueba", "Prueba apellido",
                22, "Santa Marta", TypeIDNumber.TI, 1236, image0);
        Client client1 = new Client(1, "Prueba", "Prueba apellido",
                22, "Santa Marta", TypeIDNumber.TI, 1236, image1);
        Client updateClient0 = crudClientUseCase.update(0, client0);
        Client updateClient1 = crudClientUseCase.update(1, client1);
        assertEquals("Prueba", updateClient0.getNames());
        assertEquals(22, updateClient0.getAge());
        assertEquals(image1, updateClient1.getImage());

    }

    @Test
    void delete() {

        String image64 = "Imagen sorpresa";
        ClientImage image0 = new ClientImage("0", 0, image64);
        ClientEntity clientEntity0 = new ClientEntity(0, "Prueba", "Prueba apellido",
                22, "Santa Marta", TypeIDNumber.TI, 1236, image0);
        Optional<ClientEntity> optionalEntityType = Optional.of(clientEntity0);
        Mockito.when(clientJpaRepositoryMock.findById(0)).thenReturn(optionalEntityType);
        // when
        crudClientUseCase.delete(0);

        // then
        Mockito.verify(clientJpaRepositoryMock, times(1)).deleteById(0);
        Mockito.verify(clientImageFeignRepositoryMock, times(1)).deleteClientImage(0);
    }

    @Test
    void getAll() {
        String image64 = "Imagen sorpresa";
        ClientImage image0 = new ClientImage("0", 0, image64);
        ClientEntity clientEntity = new ClientEntity(0, "Prueba", "Prueba apellido",
                22, "Santa Marta", TypeIDNumber.TI, 1236, image0);
        List<ClientEntity> clientsIdentity = new ArrayList<>();
        clientsIdentity.add(clientEntity);
        Mockito.when(clientJpaRepositoryMock.findAll()).thenReturn(clientsIdentity);

        List<Client> clients = crudClientUseCase.getAll();
        assertEquals("Prueba", clients.get(0).getNames());
    }
}