package pl.uplukaszp.exchangeOffice.transaction;

import java.math.BigDecimal;

import lombok.extern.slf4j.Slf4j;
import pl.uplukaszp.exchangeOffice.domain.MainWallet;
import pl.uplukaszp.exchangeOffice.domain.Wallet;
import pl.uplukaszp.exchangeOffice.repository.MainWalletRepository;
import pl.uplukaszp.exchangeOffice.repository.WalletRepository;

@Slf4j
public class BuyingTransaction extends TransactionExecutor {

	public BuyingTransaction(TransactionData data, WalletRepository walletRepo, MainWalletRepository mainWalletRepo) {
		super(data, walletRepo, mainWalletRepo);
	}

	@Override
	public Boolean hasEnoughtMoney() {
		return (hasUserEnoughMoney() && hasExchangeOfficeEnoughMoney());
	}

	private Boolean hasUserEnoughMoney() {
		if (data.getPrice().multiply(new BigDecimal(data.getAmount()))
				.compareTo(data.getUserSettlementWallet().getAmount()) == -1) {
			return true;
		}
		return false;
	}

	private Boolean hasExchangeOfficeEnoughMoney() {
		if (new BigDecimal(data.getAmount() * data.getUnit()).compareTo(data.getMainWallet().getAmount()) == -1) {
			return true;

		}
		return false;
	}

	@Override
	public Boolean executeTransaction() {
		try {
			Wallet userWallet = data.getUserWallet();
			Wallet userSettlementWallet = data.getUserSettlementWallet();
			MainWallet mainWallet = data.getMainWallet();
			MainWallet mainSettlementWallet = data.getMainSettlementWallet();

			userSettlementWallet.setAmount(userSettlementWallet.getAmount()
					.subtract(data.getPrice().multiply(new BigDecimal(data.getAmount()))));
			mainSettlementWallet.setAmount(
					mainSettlementWallet.getAmount().add(data.getPrice().multiply(new BigDecimal(data.getAmount()))));
			mainWallet.setAmount(mainWallet.getAmount().subtract(new BigDecimal(data.getAmount() * data.getUnit())));
			userWallet.setAmount(userWallet.getAmount().add(new BigDecimal(data.getAmount() * data.getUnit())));

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
