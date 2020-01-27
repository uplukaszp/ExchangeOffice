package pl.uplukaszp.exchangeOffice.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import pl.uplukaszp.exchangeOffice.domain.User;
import pl.uplukaszp.exchangeOffice.dto.UserWalletDTO;
import pl.uplukaszp.exchangeOffice.service.ExchangeRateService;
import pl.uplukaszp.exchangeOffice.service.WalletService;

@Controller
@RequestMapping
@AllArgsConstructor
public class IndexController {

	WalletService walletService;
	ExchangeRateService exchangeRateService;

	@GetMapping
	public String getIndex(Model model, Authentication auth) {
		User user = (User) auth.getPrincipal();
		addUserWalletData(model, user);
		addCurrenciesData(model);
		addUserInfo(model,user);
		addSettlementInfo(model,user);
		return "index";
	}

	private void addUserWalletData(Model model, User user) {
		List<UserWalletDTO> userWallets = walletService.getUserWalletsDTO(user.getId());
		model.addAttribute("userWallets", userWallets);
	}

	private void addCurrenciesData(Model model) {
		model.addAttribute("exchangeRates", exchangeRateService.getRates());
	}
	
	private void addUserInfo(Model model, User user) {
		model.addAttribute("userInfo", user.getUsername());
	}
	private void addSettlementInfo(Model model, User user) {
		model.addAttribute("settlementAmount", walletService.getUserSettlementWallet(user.getId()).getAmount());
	}
}
