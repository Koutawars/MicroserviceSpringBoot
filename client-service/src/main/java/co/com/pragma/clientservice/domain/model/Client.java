package co.com.pragma.clientservice.domain.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    private Integer id;
    private String names;
    private String lastNames;
    private int age;
    private String city;
    private TypeIDNumber typeIDNumber;
    private long idNumber;
    private ClientImage image;

    public boolean isImageOk() {
        if(this.image != null) {
            if(this.image.getBase64() != null) {
                return !(this.image.getBase64().isEmpty() && this.image.getBase64().isBlank());
            }
        }
        return false;
    }
}
