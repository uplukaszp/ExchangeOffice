package pl.uplukaszp.exchangeOffice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;
import pl.uplukaszp.exchangeOffice.domain.User;
import pl.uplukaszp.exchangeOffice.domain.Wallet;
import pl.uplukaszp.exchangeOffice.service.WalletService;

@Controller
@RequestMapping("/wallet")
@AllArgsConstructor
public class WalletController {
	WalletService service;

	@GetMapping
	@ResponseBody
	public ResponseEntity<List<Wallet>> getUserWallet(Authentication principal) {
		User user = (User) principal.getPrincipal();
		 List<Wallet> userWallets = service.getUserWallets(user.getLogin());
		 return new ResponseEntity<List<Wallet>>(userWallets,HttpStatus.OK);
	}
}
