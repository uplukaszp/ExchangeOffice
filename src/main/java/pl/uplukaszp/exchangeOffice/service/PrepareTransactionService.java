package pl.uplukaszp.exchangeOffice.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.uplukaszp.exchangeOffice.domain.Currency;
import pl.uplukaszp.exchangeOffice.domain.ExchangeRate;
import pl.uplukaszp.exchangeOffice.repository.ExchangeRateRepository;
import pl.uplukaszp.exchangeOffice.repository.MainWalletRepository;
import pl.uplukaszp.exchangeOffice.repository.WalletRepository;
import pl.uplukaszp.exchangeOffice.transaction.BuyingTransaction;
import pl.uplukaszp.exchangeOffice.transaction.SellingTransaction;
import pl.uplukaszp.exchangeOffice.transaction.TransactionData;
import pl.uplukaszp.exchangeOffice.transaction.TransactionExecutor;

@Service
@AllArgsConstructor
@Slf4j
public class PrepareTransactionService {
	private ExchangeRateRepository exchangeRateRepo;
	private WalletRepository walletRepo;
	private MainWalletRepository mainWalletRepo;

	public TransactionData getTransactionData(Currency currency, Long amount, Long userId) {
		TransactionData data = new TransactionData();
		ExchangeRate exchangeRate = exchangeRateRepo.findById(currency).get();
		data.setCurrency(currency);
		data.setPrice(exchangeRate.getPurchasePrice());
		data.setAmount(amount);
		data.setUnit(exchangeRate.getUnit());

		data.setUserWallet(walletRepo.findByIdUserIdAndIdCurrency(userId, data.getCurrency()));
		data.setUserSettlementWallet(walletRepo.findByIdUserIdAndIdCurrency(userId, Currency.PLN));
		data.setMainWallet(mainWalletRepo.findById(data.getCurrency()).get());
		data.setMainSettlementWallet(mainWalletRepo.findById(Currency.PLN).get());
		return data;
	}

	public TransactionExecutor getBuyingTransactionExecutor(TransactionData data) {
		return new BuyingTransaction(data, walletRepo, mainWalletRepo);
	}

	public TransactionExecutor getSellingTransactionExecutor(TransactionData data) {
		return new SellingTransaction(data, walletRepo, mainWalletRepo);
	}
}
