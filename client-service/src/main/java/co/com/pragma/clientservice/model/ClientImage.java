package co.com.pragma.clientservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ClientImage {
    private String id;
    private Integer clientId;
    private String base64;

    public ClientImage(String id, Integer clientId, String base64) {
        this.id = id;
        this.clientId = clientId;
        this.base64 = base64;
    }
}