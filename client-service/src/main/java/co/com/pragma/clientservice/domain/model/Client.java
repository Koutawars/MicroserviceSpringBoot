package co.com.pragma.clientservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
            return !(this.image.getBase64().isEmpty() && this.image.getBase64().isBlank());
        }
        return false;
    }
}
