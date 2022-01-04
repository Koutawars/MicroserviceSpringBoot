package co.com.pragma.clientservice.repository;

import co.com.pragma.clientservice.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientRepository extends JpaRepository<Client, Integer> {
}
