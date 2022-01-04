package co.com.pragma.clientimageservice.controller;

import co.com.pragma.clientimageservice.model.ClientImage;
import co.com.pragma.clientimageservice.service.ClientImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class ClientImageController {

    @Autowired
    ClientImageService clientImageService;

    @GetMapping("{clientId}")
    ClientImage getClientImage(@PathVariable Integer clientId) {
        return clientImageService.getClientImageByClientId(clientId);
    }

    @PostMapping("{clientId}")
    ClientImage setImageToClient(@PathVariable Integer clientId, @RequestBody String ImageBase64) {
        return clientImageService.setImageToClient(clientId, ImageBase64);
    }

    @PutMapping("{clientId}")
    ClientImage updateClientImage(@PathVariable Integer clientId, @RequestBody String ImageBase64){
        return clientImageService.updateClientImage(clientId, ImageBase64);
    }

    @DeleteMapping("{clientId}")
    void deleteClientImage(@PathVariable Integer clientId){
        clientImageService.deleteClientImage(clientId);
    }
}
