package co.com.pragma.clientservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@JsonIgnoreProperties({ "client" })
@Getter @Setter
@NoArgsConstructor
public class ClientPayload extends Client {
    private String imageBase64;

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
