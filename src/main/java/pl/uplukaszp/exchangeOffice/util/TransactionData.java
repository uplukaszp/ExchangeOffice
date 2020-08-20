package pl.uplukaszp.exchangeOffice.util;

import java.math.BigDecimal;

import lombok.Data;
import pl.uplukaszp.exchangeOffice.domain.Currency;
import pl.uplukaszp.exchangeOffice.domain.MainWallet;
import pl.uplukaszp.exchangeOffice.domain.Wallet;

@Data
public class TransactionData {
	
	private Wallet userWallet;
	private Wallet userSettlementWallet;
	private MainWallet mainWallet;
	private MainWallet mainSettlementWallet;
	private BigDecimal price;
	private Long amount;
	private Integer unit;
	private Currency currency;

}
