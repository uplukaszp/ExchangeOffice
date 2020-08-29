package pl.uplukaszp.exchangeOffice.transaction;

import pl.uplukaszp.exchangeOffice.repository.MainWalletRepository;
import pl.uplukaszp.exchangeOffice.repository.WalletRepository;

public abstract class TransactionExecutor {
	protected TransactionData data;
	protected WalletRepository walletRepo;
	protected MainWalletRepository mainWalletRepo;

	public TransactionExecutor(TransactionData data, WalletRepository walletRepo, MainWalletRepository mainWalletRepo) {
		this.data = data;
		this.walletRepo = walletRepo;
		this.mainWalletRepo = mainWalletRepo;

	}

	public abstract Boolean hasEnoughtMoney();

	public abstract Boolean executeTransaction();

}
