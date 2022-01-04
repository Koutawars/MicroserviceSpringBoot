package co.com.pragma.clientservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientImage {
    private String id;
    private Integer clientId;
    private String base64;
}