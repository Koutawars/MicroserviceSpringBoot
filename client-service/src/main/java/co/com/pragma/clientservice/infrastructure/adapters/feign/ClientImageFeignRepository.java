package co.com.pragma.clientservice.infrastructure.adapters.feign;

import co.com.pragma.clientservice.domain.model.ClientImage;
import co.com.pragma.clientservice.domain.model.gateways.ClientImageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Repository;

import java.util.function.Supplier;

@Repository
@Slf4j
@AllArgsConstructor
public class ClientImageFeignRepository implements ClientImageRepository {
     private ClientImageFeign clientImageFeign;
     private CircuitBreakerFactory circuitBreakerFactory;

    @Override
    public ClientImage getClientImage(Integer clientId) {
        Supplier<ClientImage> supplierClient = () -> clientImageFeign.getClientImage(clientId);
        return circuitBreakerFactory.create("getAClient")
                .run(supplierClient, throwable -> new ClientImage());
    }

    @Override
    public ClientImage setImageToClient(Integer clientId, String imageBase64) {
        Supplier<ClientImage> supplierClient = () -> clientImageFeign.setImageToClient(clientId, imageBase64);
        return circuitBreakerFactory.create("setAImageToClient")
                .run(supplierClient, throwable -> new ClientImage());
    }

    @Override
    public ClientImage updateClientImage(Integer clientId, String imageBase64) {
        Supplier<ClientImage> supplierClient = () -> clientImageFeign.updateClientImage(clientId, imageBase64);
        return circuitBreakerFactory.create("updateImageToClient")
                .run(supplierClient, throwable -> new ClientImage());
    }

    @Override
    public void deleteClientImage(Integer clientId) {
        try {
            clientImageFeign.deleteClientImage(clientId);
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
    }
}
