package co.com.pragma.clientservice.domain.usecase;

import co.com.pragma.clientservice.domain.model.Client;
import co.com.pragma.clientservice.domain.model.ClientImage;
import co.com.pragma.clientservice.domain.model.TypeIDNumber;
import co.com.pragma.clientservice.domain.model.gateways.ClientRepository;
import co.com.pragma.clientservice.infrastructure.jpa.ClientRepositoryImpl;
import co.com.pragma.clientservice.infrastructure.jpa.entities.ClientEntity;
import co.com.pragma.clientservice.infrastructure.jpa.repositories.ClientJpaRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CrudClientUseCaseTest {

    @Mock
    ClientJpaRepository clientJpaRepository;
    @MockBean
    ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    private ClientRepository clientRepository = new ClientRepositoryImpl(clientJpaRepository, modelMapper);
    @InjectMocks
    private CrudClientImageUseCase imageUseCase;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void beforeEach() {
        String image64 = "Imagen sorpresa";
        ClientImage image0 = new ClientImage("0", 0, image64);
        ClientEntity client0 = new ClientEntity(null, "Prueba nombre", "Prueba apellido",
                20, "Santa Marta", TypeIDNumber.TI, 123456, image0);

        ClientEntity newClient = new ClientEntity(0, "Prueba nombre", "Prueba apellido",
                20, "Santa Marta", TypeIDNumber.TI, 123456, image0);
        Mockito.when(clientJpaRepository.save(client0)).thenReturn(newClient);
        Mockito.when(clientJpaRepository.findById(0)).thenReturn(Optional.of(newClient));
    }

    @Test
    void create() {
        String image64 = "Imagen sorpresa";
        ClientImage image0 = new ClientImage("0", 0, image64);
        Client client0 = new Client(null, "Prueba nombre", "Prueba apellido",
                20, "Santa Marta", TypeIDNumber.TI, 123456, image0);
        Client newClient = clientRepository.create(client0);
        assertEquals(newClient.getAge(), client0.getAge());
        assertEquals(newClient.getImage().getBase64(), client0.getImage().getBase64());
    }

    @Test
    void read() {
        Client client = clientRepository.read(0);
        assertEquals(0, client.getId());
        assertEquals(20, client.getAge());
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void getAll() {
    }
}