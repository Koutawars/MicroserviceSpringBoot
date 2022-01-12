package co.com.pragma.clientservice.application;

import co.com.pragma.clientservice.domain.model.Client;
import co.com.pragma.clientservice.domain.model.ClientImage;
import co.com.pragma.clientservice.domain.model.TypeIDNumber;
import co.com.pragma.clientservice.infrastructure.controller.dto.ClientWithImageDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
//@DataJpaTest
@SpringBootTest(classes = ClientServiceApplication.class)
class ClientServiceApplicationTest {

    @Test
    void main() {
    }

}