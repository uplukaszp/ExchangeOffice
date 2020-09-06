package pl.uplukaszp.exchangeOffice.transaction;

import java.math.BigDecimal;

import lombok.extern.slf4j.Slf4j;
import pl.uplukaszp.exchangeOffice.domain.MainWallet;
import pl.uplukaszp.exchangeOffice.domain.Wallet;
import pl.uplukaszp.exchangeOffice.repository.MainWalletRepository;
import pl.uplukaszp.exchangeOffice.repository.WalletRepository;

@Slf4j
public class SellingTransaction extends TransactionExecutor {

	public SellingTransaction(TransactionData data, WalletRepository walletRepo, MainWalletRepository mainWalletRepo) {
		super(data, walletRepo, mainWalletRepo);
	}

	public Boolean hasEnoughtMoney() {
		return hasUserEnoughtMoney() && hasExchangeOfficeEnoughMoney();
	}

	private Boolean hasUserEnoughtMoney() {
		if (new BigDecimal(data.getAmount()).compareTo(data.getUserWallet().getAmount()) < 1) {

			return true;
		}
		return false;
	}

	private Boolean hasExchangeOfficeEnoughMoney() {
		if (new BigDecimal(data.getAmount()).multiply(data.getPrice())
				.compareTo(data.getMainSettlementWallet().getAmount()) < 1) {
			return true;

		}
		return false;
	}

	public Boolean executeTransaction() {
		try {
			Wallet userWallet = data.getUserWallet();
			Wallet userSettlementWallet = data.getUserSettlementWallet();
			MainWallet mainWallet = data.getMainWallet();
			MainWallet mainSettlementWallet = data.getMainSettlementWallet();

			userSettlementWallet.setAmount(
					userSettlementWallet.getAmount().add(data.getPrice().multiply(new BigDecimal(data.getAmount()))));
			mainSettlementWallet.setAmount(mainSettlementWallet.getAmount()
					.subtract(data.getPrice().multiply(new BigDecimal(data.getAmount()))));
			mainWallet.setAmount(mainWallet.getAmount().add(new BigDecimal(data.getAmount())));
			userWallet.setAmount(userWallet.getAmount().subtract(new BigDecimal(data.getAmount())));

			walletRepo.save(userWallet);
			walletRepo.save(userSettlementWallet);
			mainWalletRepo.save(mainWallet);
			mainWalletRepo.save(mainSettlementWallet);
		} catch (Exception e) {
			log.trace("Error while buying", e);
			return false;
		}
		return true;
	}

}
