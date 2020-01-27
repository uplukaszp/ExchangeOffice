package pl.uplukaszp.exchangeOffice.dto;

import lombok.Data;
import pl.uplukaszp.exchangeOffice.domain.Currency;

@Data
public class CurrencyDTO {
	Currency currency;
	Integer unit;
	Float value;
}
