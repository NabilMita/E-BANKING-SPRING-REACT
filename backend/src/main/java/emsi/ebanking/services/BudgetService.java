package emsi.ebanking.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import emsi.ebanking.entities.Budget;
import emsi.ebanking.entities.Compte;
import emsi.ebanking.repositories.BudgetRepository;
import emsi.ebanking.repositories.CompteRepository;

@Service
public class BudgetService {

	@Autowired
	private BudgetRepository repo;

	@Autowired
	private CompteRepository cptRepo;

	// INSERT
	public void addBudget(Long compteNumber, Budget budget) {
		Compte cpt = cptRepo.findById(compteNumber).get();
		budget.setCompte(cpt);
		repo.save(budget);
	}

	// FIND BY ID
	public List<Budget> getBudgetById(Long id) {
		return repo.findByCompteNumber(id);
	}

	// DELETE Budget
	public void deleteBudget(Long id) {
		repo.deleteById(id);
	}

	// UPDATE Budget
	public void updateBudget(Long compteNumber, Budget budget) {
		Budget existingBud = repo.findById(compteNumber).get();
		existingBud.setTitle(budget.getTitle());
		existingBud.setAmount(budget.getAmount());
		repo.save(existingBud);
	}
}