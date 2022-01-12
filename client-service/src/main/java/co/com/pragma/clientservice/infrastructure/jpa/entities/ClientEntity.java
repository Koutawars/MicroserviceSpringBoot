package co.com.pragma.clientservice.infrastructure.jpa.entities;

import co.com.pragma.clientservice.domain.model.ClientImage;
import co.com.pragma.clientservice.domain.model.TypeIDNumber;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientEntity {

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
    @Transient
    private ClientImage image;
}

