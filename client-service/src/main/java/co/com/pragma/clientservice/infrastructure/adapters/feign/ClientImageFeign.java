package co.com.pragma.clientservice.infrastructure.adapters.feign;

import co.com.pragma.clientservice.domain.model.ClientImage;
import lombok.AllArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
@FeignClient(name="CLIENT-IMAGE")
public interface ClientImageFeign {

    @GetMapping("{clientId}")
    ClientImage getClientImage(@PathVariable Integer clientId);

    @PostMapping("{clientId}")
    ClientImage setImageToClient(@PathVariable Integer clientId, @RequestBody String imageBase64);

    @PutMapping("{clientId}")
    ClientImage updateClientImage(@PathVariable Integer clientId, @RequestBody String imageBase64);

    @DeleteMapping("{clientId}")
    void deleteClientImage(@PathVariable Integer clientId);

}
