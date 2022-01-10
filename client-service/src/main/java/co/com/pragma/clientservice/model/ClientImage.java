package co.com.pragma.clientservice.model;

import lombok.*;

import java.util.function.Supplier;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ClientImage {
    private String id;
    private Integer clientId;
    private String base64;
}