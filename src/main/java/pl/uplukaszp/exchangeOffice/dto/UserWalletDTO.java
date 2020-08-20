package pl.uplukaszp.exchangeOffice.dto;


import lombok.Data;
import pl.uplukaszp.exchangeOffice.domain.Currency;

@Data
public class UserWalletDTO {
	private Currency currency;
	private String unitPrice;
	private Float amount;
	private String value;
	private Boolean isExRateAviable;
}
