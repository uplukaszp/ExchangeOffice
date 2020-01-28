package pl.uplukaszp.exchangeOffice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;
import pl.uplukaszp.exchangeOffice.domain.User;
import pl.uplukaszp.exchangeOffice.dto.UserWalletDTO;
import pl.uplukaszp.exchangeOffice.service.WalletService;

@Controller
@RequestMapping("/wallet")
@AllArgsConstructor
public class WalletController {
	WalletService service;

	@GetMapping
	@ResponseBody
	public ResponseEntity<Map<String,Object>> getUserWallet(Authentication principal) {
		User user = (User) principal.getPrincipal();
		Map<String,Object> map=new HashMap<>();
		 List<UserWalletDTO> userWallets = service.getUserWalletsDTO(user.getId());
		 map.put("userWallets", userWallets);
		 map.put("settlementAmount",service.getUserSettlementWallet(user.getId()).getAmount());
		 return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}
}
