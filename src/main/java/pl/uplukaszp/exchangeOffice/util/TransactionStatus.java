package pl.uplukaszp.exchangeOffice.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionStatus {
	private Boolean isOK;
	private String reason;
}
