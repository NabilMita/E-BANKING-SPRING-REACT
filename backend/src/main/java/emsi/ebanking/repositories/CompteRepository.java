package emsi.ebanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import emsi.ebanking.entities.Compte;

public interface CompteRepository extends JpaRepository<Compte, Long> {

}
