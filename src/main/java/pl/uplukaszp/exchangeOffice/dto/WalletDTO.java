package pl.uplukaszp.exchangeOffice.dto;


import com.sun.istack.NotNull;

import lombok.Data;
import pl.uplukaszp.exchangeOffice.domain.Currency;

@Data
public class WalletDTO {
	@NotNull
	Currency currency;
	@NotNull
	Float amount;
}
