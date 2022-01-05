package co.com.pragma.clientservice.model;

import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.persistence.*;

@Entity
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Client {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String names;
    private String lastNames;
    private int age;
    private String city;
    /**
     * Tipo de numero de identificación <br>
     * Para más info: {@link TypeIDNumber}
     * @See TypeIDNumber
     */
    private TypeIDNumber typeIDNumber;
    private long idNumber;

    public void updateClient(Client client) {
        this.names = client.getNames();
        this.lastNames = client.getLastNames();
        this.age = client.getAge();
        this.city = client.getCity();
        this.typeIDNumber = client.getTypeIDNumber();
        this.idNumber = client.getIdNumber();
    }

    public Client(Client client) {
        this.id = client.getId();
        this.names = client.getNames();
        this.lastNames = client.getLastNames();
        this.age = client.getAge();
        this.city = client.getCity();
        this.typeIDNumber = client.getTypeIDNumber();
        this.idNumber = client.getIdNumber();
    }


}

