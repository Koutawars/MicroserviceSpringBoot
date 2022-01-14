package co.com.pragma.clientservice.domain.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ClientImage {
    private String id;
    private Integer clientId;
    private String base64;
}