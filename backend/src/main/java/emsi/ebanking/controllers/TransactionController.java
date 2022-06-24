package emsi.ebanking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import emsi.ebanking.entities.Transaction;
import emsi.ebanking.services.TransactionService;

@RestController
@CrossOrigin("*")
public class TransactionController {

	@Autowired
	TransactionService srv;

	@PostMapping("/transaction/transfert/{senderId}/{receiverId}")
	public Boolean addTransaction(@PathVariable Long senderId, @PathVariable Long receiverId,
			@RequestBody Transaction transaction) {
		return srv.addTransaction(senderId, receiverId, transaction);
	}

	@PostMapping("/transaction/virement/{receiverId}")
	public Boolean virement(@PathVariable Long receiverId, @RequestBody Transaction transaction) {
		return srv.verser(receiverId, transaction);
	}

	@PostMapping("/transaction/retrait/{receiverId}")
	public Boolean retrait(@PathVariable Long receiverId, @RequestBody Transaction transaction) {
		return srv.retrait(receiverId, transaction);
	}

}