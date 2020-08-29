package pl.uplukaszp.exchangeOffice.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionStatus {
	private Boolean isOK;
	private FaultDescription faultDescription;
}
