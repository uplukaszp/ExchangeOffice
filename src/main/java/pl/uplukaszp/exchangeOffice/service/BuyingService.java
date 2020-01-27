package pl.uplukaszp.exchangeOffice.service;

import java.util.Arrays;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.uplukaszp.exchangeOffice.domain.Currency;
import pl.uplukaszp.exchangeOffice.domain.ExchangeRate;
import pl.uplukaszp.exchangeOffice.domain.MainWallet;
import pl.uplukaszp.exchangeOffice.domain.Wallet;
import pl.uplukaszp.exchangeOffice.repository.ExchangeRateRepository;
import pl.uplukaszp.exchangeOffice.repository.MainWalletRepository;
import pl.uplukaszp.exchangeOffice.repository.UserRepository;
import pl.uplukaszp.exchangeOffice.repository.WalletRepository;
import pl.uplukaszp.exchangeOffice.util.Status;
import pl.uplukaszp.exchangeOffice.util.TransactionData;

@Service
@AllArgsConstructor
@Slf4j
public class BuyingService {
	WalletRepository walletRepo;
	MainWalletRepository mainWalletRepo;
	ExchangeRateRepository exchangeRateRepo;

	@Transactional
	public Status execute(Currency currency, Long amount, Long userId) {
		TransactionData transactionData = getTransactionData(currency, amount, userId);

		Status exchangeOfficeStatus = hasExchangeOfficeEnoughMoney(transactionData);
		Status userStatus = hasUserEnoughMoney(transactionData);

		Boolean userHasEnoughtMoney = userStatus.getIsOK();
		Boolean exchangeOfficeHasEnoughtMoney = exchangeOfficeStatus.getIsOK();

		if (userHasEnoughtMoney) {

			if (exchangeOfficeHasEnoughtMoney) {
				return buyCurrencyForUser(transactionData);
			} else {
				return exchangeOfficeStatus;
			}

		} else {
			return userStatus;
		}
	}

	private TransactionData getTransactionData(Currency currency, Long amount, Long userId) {
		TransactionData data = new TransactionData();
		ExchangeRate exchangeRate = exchangeRateRepo.findById(currency).get();
		data.setCurrency(currency);
		data.setPrice(exchangeRate.getPurchasePrice());
		data.setAmount(amount);
		data.setUnit(exchangeRate.getUnit());

		data.setUserWallet(walletRepo.findByIdUserIdAndIdCurrency(userId, data.getCurrency()));
		data.setUserSettlementWallet(walletRepo.findByIdUserIdAndIdCurrency(userId, Currency.PLN));
		for (MainWallet mainWallet : mainWalletRepo.findAll()) {
			System.out.println(mainWallet);
		}
		data.setMainWallet(mainWalletRepo.findById(data.getCurrency()).get());
		data.setMainSettlementWallet(mainWalletRepo.findById(Currency.PLN).get());
		return data;
	}

	private Status hasUserEnoughMoney(TransactionData data) {
		if (data.getAmount() * data.getPrice() <= data.getUserSettlementWallet().getAmount()) {
			return new Status(true, "user has enought money");
		}
		return new Status(false, "user has not enought money");
	}

	private Status hasExchangeOfficeEnoughMoney(TransactionData data) {
		if (data.getAmount() * data.getUnit() <= data.getMainWallet().getAmount()) {
			return new Status(true, "exchange office has enought money");

		}
		return new Status(false, "exchange office has not enought money");
	}

	private Status buyCurrencyForUser(TransactionData data) {
		log.info("buyCurrencyForUser: " + data.toString());

		try {
			Wallet userWallet = data.getUserWallet();
			Wallet userSettlementWallet = data.getUserSettlementWallet();
			MainWallet mainWallet = data.getMainWallet();
			MainWallet mainSettlementWallet = data.getMainSettlementWallet();

			userSettlementWallet.setAmount(userSettlementWallet.getAmount() - data.getAmount() * data.getPrice());
			mainSettlementWallet.setAmount(mainSettlementWallet.getAmount() + data.getAmount() * data.getPrice());
			mainWallet.setAmount(mainWallet.getAmount() - data.getAmount() * data.getUnit());
			userWallet.setAmount(userWallet.getAmount() + data.getAmount() * data.getUnit());

			walletRepo.save(userWallet);
			walletRepo.save(userSettlementWallet);
			mainWalletRepo.save(mainWallet);
			mainWalletRepo.save(mainSettlementWallet);
		} catch (Exception e) {
			log.trace("Error while buying", e);
			return new Status(false, e.getCause().toString());
		}
		return new Status(true, "Transaction Complete");
	}
}
