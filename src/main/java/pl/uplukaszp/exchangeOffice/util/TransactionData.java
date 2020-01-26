package pl.uplukaszp.exchangeOffice.util;

import lombok.Data;
import pl.uplukaszp.exchangeOffice.domain.Currency;
import pl.uplukaszp.exchangeOffice.domain.MainWallet;
import pl.uplukaszp.exchangeOffice.domain.Wallet;

@Data
public class TransactionData {
	
	Wallet userWallet;
	Wallet userSettlementWallet;
	MainWallet mainWallet;
	MainWallet mainSettlementWallet;
	Float price;
	Long amount;
	Integer unit;
	Currency currency;

}
