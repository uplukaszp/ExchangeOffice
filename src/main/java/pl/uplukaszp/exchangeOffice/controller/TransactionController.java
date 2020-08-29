package pl.uplukaszp.exchangeOffice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import pl.uplukaszp.exchangeOffice.domain.Currency;
import pl.uplukaszp.exchangeOffice.domain.User;
import pl.uplukaszp.exchangeOffice.service.PrepareTransactionService;
import pl.uplukaszp.exchangeOffice.service.TransactionService;
import pl.uplukaszp.exchangeOffice.transaction.TransactionStatus;

@RestController
@RequestMapping("/transaction")
@AllArgsConstructor
public class TransactionController {
	private TransactionService transactionService;
	private PrepareTransactionService prepareService;

	@PostMapping("/buying")
	@ResponseBody
	ResponseEntity<TransactionStatus> buy(Currency currencyType, Long amount, Authentication principal) {
		User user = (User) principal.getPrincipal();

		TransactionStatus status = transactionService.executeTransaction(prepareService
				.getBuyingTransactionExecutor(prepareService.getTransactionData(currencyType, amount, user.getId())));
		if (status.getIsOK()) {
			return new ResponseEntity<>(status, HttpStatus.OK);
		}
		return new ResponseEntity<>(status, HttpStatus.UNPROCESSABLE_ENTITY);

	}

	@PostMapping("/selling")
	@ResponseBody
	ResponseEntity<TransactionStatus> sell(Currency currencyType, Long amount, Authentication principal) {
		User user = (User) principal.getPrincipal();
		TransactionStatus status = transactionService.executeTransaction(prepareService
				.getSellingTransactionExecutor(prepareService.getTransactionData(currencyType, amount, user.getId())));
		if (status.getIsOK()) {
			return new ResponseEntity<>(status, HttpStatus.OK);
		}
		return new ResponseEntity<>(status, HttpStatus.UNPROCESSABLE_ENTITY);
	}
}
