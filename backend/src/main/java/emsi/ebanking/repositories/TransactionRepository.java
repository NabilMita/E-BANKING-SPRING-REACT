package emsi.ebanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import emsi.ebanking.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
