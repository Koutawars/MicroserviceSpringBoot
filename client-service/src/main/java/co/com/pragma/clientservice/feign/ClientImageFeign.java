package co.com.pragma.clientservice.feign;

import co.com.pragma.clientservice.model.ClientImage;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="CLIENT-IMAGE", fallback = ClientImageFeign.HystrixClientFallback.class)
public interface ClientImageFeign {

    @GetMapping("{clientId}")
    ClientImage getClientImage(@PathVariable Integer clientId);

    @PostMapping("{clientId}")
    ClientImage setImageToClient(@PathVariable Integer clientId, @RequestBody String ImageBase64);

    @PutMapping("{clientId}")
    ClientImage updateClientImage(@PathVariable Integer clientId, @RequestBody String ImageBase64);

    @DeleteMapping("{clientId}")
    void deleteClientImage(@PathVariable Integer clientId);


    @Component
    class HystrixClientFallback implements ClientImageFeign {

        @Override
        public ClientImage getClientImage(Integer clientId) {
            return null;
        }

        @Override
        public ClientImage setImageToClient(Integer clientId, String ImageBase64) {
            return null;
        }

        @Override
        public ClientImage updateClientImage(Integer clientId, String ImageBase64) {
            return null;
        }

        @Override
        public void deleteClientImage(Integer clientId) {
            throw new RuntimeException();
        }

    }

}
