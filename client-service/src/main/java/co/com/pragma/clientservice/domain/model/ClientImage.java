package co.com.pragma.clientservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientImage {
    private String id;
    private Integer clientId;
    private String base64;
}