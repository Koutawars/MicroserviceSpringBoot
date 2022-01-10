package co.com.pragma.clientservice.model;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
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
    @Transient
    private ClientImage image;
}

