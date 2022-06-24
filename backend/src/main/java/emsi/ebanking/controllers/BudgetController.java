package emsi.ebanking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import emsi.ebanking.entities.Budget;
import emsi.ebanking.entities.Compte;
import emsi.ebanking.services.BudgetService;
import emsi.ebanking.services.CompteService;

@RestController
@CrossOrigin("*")
public class BudgetController {

	@Autowired
	BudgetService srv;

	@Autowired
	CompteService cSrv;

	@GetMapping("/budget/{compteId}")
	public Compte getBudgetsByAccount(@PathVariable Long compteId) {
		return cSrv.getCompteByNumber(compteId);
	}

	@PostMapping("/budget/addBudget/{compteId}")
	public void addBudget(@PathVariable Long compteId, @RequestBody Budget budget) {
		srv.addBudget(compteId, budget);
	}

	@PostMapping("/budget/editBudget/{compteId}")
	public void editBudget(@PathVariable Long compteId, @RequestBody Budget budget) {
		srv.updateBudget(compteId, budget);
	}

	@DeleteMapping("/budget/deleteBudget/{budgetId}")
	public void deleteBudget(@PathVariable Long budgetId) {
		srv.deleteBudget(budgetId);
	}
}