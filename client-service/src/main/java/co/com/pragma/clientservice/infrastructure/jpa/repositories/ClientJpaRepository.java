package co.com.pragma.clientservice.infrastructure.jpa.repositories;


import co.com.pragma.clientservice.infrastructure.jpa.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientJpaRepository extends JpaRepository<ClientEntity, Integer> {
}