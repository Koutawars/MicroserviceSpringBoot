package co.com.pragma.clientservice.domain.model.gateways;


import co.com.pragma.clientservice.domain.model.ClientImage;

public interface ClientImageRepository {
    ClientImage getClientImage(Integer clientId);

    ClientImage setImageToClient(Integer clientId, String ImageBase64);

    ClientImage updateClientImage(Integer clientId, String ImageBase64);

    void deleteClientImage(Integer clientId);
}
