package pl.uplukaszp.exchangeOffice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import pl.uplukaszp.exchangeOffice.domain.Currency;
import pl.uplukaszp.exchangeOffice.domain.User;
import pl.uplukaszp.exchangeOffice.service.BuyingService;
import pl.uplukaszp.exchangeOffice.service.SelllingService;

@Controller
@RequestMapping("/transaction")
@AllArgsConstructor
public class TransactionController {
	BuyingService buying;
	SelllingService sell;
	@PostMapping("/buying")
	ResponseEntity<String> buy(Currency currencyType,Long amount,Authentication principal){
		User user = (User) principal.getPrincipal();
		
		return new ResponseEntity<>(buying.execute(currencyType, amount, user.getId()).getReason(),HttpStatus.OK);
		
	}
	@PostMapping("/selling")
	ResponseEntity<String> sell(Currency currencyType,Long amount,Authentication principal){
		User user = (User) principal.getPrincipal();
		return new ResponseEntity<>(sell.execute(currencyType, amount, user.getId()).getReason(),HttpStatus.OK);
	}
}
