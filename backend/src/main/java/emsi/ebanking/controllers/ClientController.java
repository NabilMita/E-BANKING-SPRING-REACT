package emsi.ebanking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import emsi.ebanking.entities.Compte;
import emsi.ebanking.entities.Transaction;
import emsi.ebanking.services.CompteService;
import emsi.ebanking.services.TransactionService;

@RestController
@CrossOrigin("*")
public class ClientController {

	@Autowired
	CompteService srv;

	@Autowired
	TransactionService tSrv;

	@PostMapping("/client/transaction/transfert/{senderId}/{receiverId}")
	public void addTransaction(@PathVariable Long senderId, @PathVariable Long receiverId,
			@RequestBody Transaction transaction) {
		tSrv.addTransaction(senderId, receiverId, transaction);
	}

	@GetMapping("/client/compte/{compteId}")
	public Compte getAccountById(@PathVariable Long compteId) {
		return srv.getCompteByNumber(compteId);
	}

}