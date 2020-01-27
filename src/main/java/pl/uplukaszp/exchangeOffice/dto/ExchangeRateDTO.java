package pl.uplukaszp.exchangeOffice.dto;

import lombok.Data;
import pl.uplukaszp.exchangeOffice.domain.Currency;

@Data
public class ExchangeRateDTO {
	Currency code;
	String unit;
	String purchasePrice;
	Boolean isRateAviable;
}
