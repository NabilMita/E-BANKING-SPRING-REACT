package emsi.ebanking.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import emsi.ebanking.entities.Compte;
import emsi.ebanking.services.CompteService;

@RestController
@CrossOrigin("*")
public class CompteController {

	@Autowired
	CompteService srv;

	@GetMapping("/compte/{compteId}")
	public Compte getAccountById(@PathVariable Long compteId) {
		return srv.getCompteByNumber(compteId);
	}

	@GetMapping("/compte/all")
	public List<Compte> getAccounts() {
		return srv.getAllAccounts();
	}

	@PostMapping("/compte/addCompte")
	public void addCompte(@RequestBody Compte compte) {
		srv.addCompte(compte);
	}

	@PostMapping("/compte/addAdminCompte")
	public void addAdmin(@RequestBody Compte compte) {
		srv.addAdminCompte(compte);
	}

	@GetMapping("/login")
	public Compte login(@RequestBody Compte compte) {
		return srv.loginCompte(compte);
	}

	@PostMapping("/compte/edit/{compteId}")
	public void editCompte(@PathVariable Long compteId, @RequestBody Compte compte) {
		srv.updateAccount(compteId, compte);
	}

	@DeleteMapping("/compte/delete/{compteId}")
	public void deleteCompte(@PathVariable Long compteId) {
		srv.deleteAccount(compteId);
	}
}