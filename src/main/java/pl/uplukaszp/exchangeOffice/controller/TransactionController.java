package pl.uplukaszp.exchangeOffice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import pl.uplukaszp.exchangeOffice.domain.Currency;
import pl.uplukaszp.exchangeOffice.domain.User;
import pl.uplukaszp.exchangeOffice.service.BuyingService;
import pl.uplukaszp.exchangeOffice.service.SelllingService;
import pl.uplukaszp.exchangeOffice.util.Status;

@RestController
@RequestMapping("/transaction")
@AllArgsConstructor
public class TransactionController {
	BuyingService buying;
	SelllingService sell;
	@PostMapping("/buying")
	@ResponseBody
	ResponseEntity<Status> buy(Currency currencyType,Long amount,Authentication principal){
		User user = (User) principal.getPrincipal();
		
		Status status = buying.execute(currencyType, amount, user.getId());
		System.out.println(status);
		if(status.getIsOK()) {
			return new ResponseEntity<>(status,HttpStatus.OK);
		}
		return new ResponseEntity<>(status,HttpStatus.UNPROCESSABLE_ENTITY);
		
	}
	@PostMapping("/selling")
	@ResponseBody
	ResponseEntity<Status> sell(Currency currencyType,Long amount,Authentication principal){
		User user = (User) principal.getPrincipal();
		Status status = sell.execute(currencyType, amount, user.getId());
		System.out.println(status);
		if(status.getIsOK()) {
			return new ResponseEntity<>(status,HttpStatus.OK);
		}
		return new ResponseEntity<>(status,HttpStatus.UNPROCESSABLE_ENTITY);
	}
}
