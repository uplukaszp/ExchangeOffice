package pl.uplukaszp.exchangeOffice.dto;

import java.util.Date;

import lombok.Data;
import pl.uplukaszp.exchangeOffice.domain.Currency;

@Data
public class ExchangeRateDTO {
	private Currency code;
	private String unit;
	private String purchasePrice;
	private Boolean isRateAviable;
	private Date date;
}
