package emsi.ebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import emsi.ebanking.entities.Budget;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

	List<Budget> findByCompteNumber(Long compteNumber);
}
