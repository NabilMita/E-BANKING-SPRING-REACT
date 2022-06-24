package emsi.ebanking.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import emsi.ebanking.entities.Compte;
import emsi.ebanking.repositories.CompteRepository;

@Service
public class CompteService {

	@Autowired
	private CompteRepository repo;

	// INSERT
	public void addCompte(Compte compte) {
		compte.setRole("CLIENT");
		repo.save(compte);
	}

	public void addAdminCompte(Compte compte) {
		compte.setRole("ADMIN");
		compte.setType("ADMIN: " + compte.getType());
		repo.save(compte);
	}

	// FIND BY ID
	public Compte getCompteByNumber(Long id) {
		return repo.findById(id).get();
	}

	// FIND ALL
	public List<Compte> getAllAccounts() {
		return repo.findAll();
	}

	// DELETE Account
	public void deleteAccount(Long id) {
		repo.deleteById(id);
	}

	// UPDATE Account Solde
	public void updateAccount(Long compteNumber, Compte compte) {
		Compte existingAcc = getCompteByNumber(compteNumber);
		existingAcc.setFullname(compte.getFullname());
		existingAcc.setBalance(compte.getBalance());
		repo.save(existingAcc);
	}

	// LOGIN
	public Compte loginCompte(Compte compte) {
		String email = compte.getEmail();
		String password = compte.getPassword();
		List<Compte> comptes = getAllAccounts();
		for (Compte c : comptes) {
			if (c.getEmail().equals(email) && c.getPassword().equals(password)) {
				return c;
			}
		}
		return null;
	}
}