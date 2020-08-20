package pl.uplukaszp.exchangeOffice.dto;

import lombok.Data;
import pl.uplukaszp.exchangeOffice.domain.Currency;

@Data
public class CurrencyDTO {
	private Currency currency;
	private Integer unit;
	private Float value;
}
