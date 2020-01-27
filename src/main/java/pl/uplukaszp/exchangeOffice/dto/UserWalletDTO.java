package pl.uplukaszp.exchangeOffice.dto;


import lombok.Data;
import pl.uplukaszp.exchangeOffice.domain.Currency;

@Data
public class UserWalletDTO {
	Currency currency;
	String unitPrice;
	Float amount;
	String value;
	Boolean isExRateAviable;
}
