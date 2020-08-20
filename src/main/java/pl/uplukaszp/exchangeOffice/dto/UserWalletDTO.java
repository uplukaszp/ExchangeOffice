package pl.uplukaszp.exchangeOffice.dto;


import java.math.BigDecimal;

import lombok.Data;
import pl.uplukaszp.exchangeOffice.domain.Currency;

@Data
public class UserWalletDTO {
	private Currency currency;
	private String unitPrice;
	private BigDecimal amount;
	private String value;
	private Boolean isExRateAviable;
}
