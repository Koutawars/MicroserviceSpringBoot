package co.com.pragma.clientservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.lang.Nullable;


@JsonIgnoreProperties({ "client" })
@Getter @Setter
@NoArgsConstructor
public class ClientPayload extends Client {
    @Nullable
    private String imageBase64;

    public ClientPayload(Integer id, String names, String lastNames, int age, String city, TypeIDNumber typeIDNumber, long idNumber, @Nullable String imageBase64) {
        super(id, names, lastNames, age, city, typeIDNumber, idNumber);
        this.imageBase64 = imageBase64;
    }

    public ClientPayload(Client client, String clientImage) {
        super(client);
        this.imageBase64 = clientImage;
    }

    public ClientPayload(Client client) {
        super(client);
    }

    public Client getClient() {
        return new Client(this);
    }

    public void setClient(Client client) {
        this.updateClient(client);
        this.setId(client.getId());
    }
}
