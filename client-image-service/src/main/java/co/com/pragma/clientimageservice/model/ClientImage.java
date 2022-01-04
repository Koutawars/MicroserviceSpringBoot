package co.com.pragma.clientimageservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Data
public class ClientImage {

    @Id
    private String id;
    @Indexed(unique=true)
    private Integer clientId;
    private String base64;

    public ClientImage(Integer clientId, String base64) {
        this.clientId = clientId;
        this.base64 = base64;
    }
}