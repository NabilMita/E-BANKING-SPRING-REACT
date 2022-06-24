package emsi.ebanking.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import emsi.ebanking.entities.Compte;
import emsi.ebanking.entities.Transaction;
import emsi.ebanking.repositories.CompteRepository;
import emsi.ebanking.repositories.TransactionRepository;

@Service
@Transactional
public class TransactionService {

	@Autowired
	private TransactionRepository repo;

	@Autowired
	private CompteRepository cptRepo;

	@Autowired
	private CompteService cptSrv;

	// INSERT TRANSACTION : TRANSFERT DE FONDS
	public Boolean addTransaction(Long sendercompteId, Long receivercompteId, Transaction transaction) {

		Compte sender = cptRepo.findById(sendercompteId).get();
		Compte receiver = cptRepo.findById(receivercompteId).get();

		if (transaction.getAmount() > sender.getBalance()) {
			return false;
		} else {
			sender.setBalance(sender.getBalance() - transaction.getAmount());
			receiver.setBalance(receiver.getBalance() + transaction.getAmount());

			cptSrv.updateAccount(sendercompteId, sender); // MAJ DU SOLDE SENDER
			cptSrv.updateAccount(receivercompteId, receiver); // MAJ DU SOLDE RECEIVER

			transaction.setCompte(sender);
			repo.save(transaction);

			Transaction tR = new Transaction(); // RECEIVER aura une transaction CREDIT
			tR.setType("credit");
			tR.setCompte(receiver);
			String title = "Fund transfer from " + sender.getFullname() + " #" + sender.getNumber();
			tR.setTitle(title);
			tR.setAmount(transaction.getAmount());

			repo.save(tR);
		}
		return true;
	}

	public Boolean verser(Long compteId, Transaction transaction) {
		Compte cpt = cptRepo.findById(compteId).get();

		cpt.setBalance(cpt.getBalance() + transaction.getAmount());
		cptSrv.updateAccount(compteId, cpt);

		addTransfert(cpt, transaction);
		return true;
	}

	public Boolean retrait(Long compteId, Transaction transaction) {
		Compte cpt = cptRepo.findById(compteId).get();

		if (transaction.getAmount() > cpt.getBalance()) {
			return false;
		} else {
			cpt.setBalance(cpt.getBalance() - transaction.getAmount());
			cptSrv.updateAccount(compteId, cpt);

			addTransfert(cpt, transaction);
		}
		return true;
	}

	public List<Transaction> getAllTransactions() {
		return repo.findAll();
	}

	public void addTransfert(Compte compte, Transaction transaction) {
		transaction.setCompte(compte);
		repo.save(transaction);
	}

}