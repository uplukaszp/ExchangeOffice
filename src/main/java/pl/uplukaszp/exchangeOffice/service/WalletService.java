package pl.uplukaszp.exchangeOffice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pl.uplukaszp.exchangeOffice.domain.Currency;
import pl.uplukaszp.exchangeOffice.domain.ExchangeRate;
import pl.uplukaszp.exchangeOffice.domain.Wallet;
import pl.uplukaszp.exchangeOffice.dto.UserWalletDTO;
import pl.uplukaszp.exchangeOffice.repository.ExchangeRateRepository;
import pl.uplukaszp.exchangeOffice.repository.WalletRepository;

@Service
@AllArgsConstructor
public class WalletService {
	WalletRepository walletRepo;
	ExchangeRateRepository repository;

	public Wallet getUserSettlementWallet(Long userId) {
		return walletRepo.findByIdUserIdAndIdCurrency(userId,Currency.PLN);
	}
	public List<Wallet> getUserWallets(Long userId) {
		return walletRepo.findAllByIdUserId(userId);
	}

	public List<UserWalletDTO> getUserWalletsDTO(Long id) {
		List<Wallet> userWallets = walletRepo.findAllByIdUserIdAndIdCurrencyNotIn(id, Arrays.asList(Currency.PLN));
		List<UserWalletDTO> userWalletDTOs = new ArrayList<>();
		List<ExchangeRate> rates = getMainWallets(userWallets);
		
		for (Wallet wallet : userWallets) {
			UserWalletDTO dto = getUserWalletDTO(rates, wallet);
			userWalletDTOs.add(dto);
		}
		return userWalletDTOs;
	}

	private List<ExchangeRate> getMainWallets(List<Wallet> userWallets) {
		List<Currency> currencies = userWallets.stream().map(Wallet::getCurrency).collect(Collectors.toList());
		currencies.remove(Currency.PLN);
		return (List<ExchangeRate>) repository.findAllById(currencies);
	}
	
	private UserWalletDTO getUserWalletDTO(List<ExchangeRate> rates, Wallet wallet) {
		UserWalletDTO dto=new UserWalletDTO();
		dto.setAmount(wallet.getAmount());
		dto.setCurrency(wallet.getCurrency());
		ExchangeRate exRate = getRate(rates, wallet.getCurrency());
		if(exRate!=null) {
			Float price=exRate.getSellPrice();
			dto.setUnitPrice(String.valueOf(price));
			dto.setValue(String.valueOf(dto.getAmount()*price/exRate.getUnit()));
			
		}else {
			dto.setUnitPrice("-");
			dto.setValue("-");
		}
		dto.setIsExRateAviable(exRate!=null);
		return dto;
	}

	private ExchangeRate getRate(List<ExchangeRate> rates, Currency currency) {
		for (ExchangeRate exchangeRate : rates) {
			if(exchangeRate.getCode().equals(currency))return exchangeRate;
		}
		return null;
		}

	
}
