package pl.uplukaszp.exchangeOffice.service;

import org.springframework.stereotype.Service;

import pl.uplukaszp.exchangeOffice.transaction.FaultDescription;
import pl.uplukaszp.exchangeOffice.transaction.TransactionExecutor;
import pl.uplukaszp.exchangeOffice.transaction.TransactionStatus;

@Service
public class TransactionService {

	public TransactionStatus executeTransaction(TransactionExecutor t) {
		if (t.hasEnoughtMoney()) {
			if (t.executeTransaction()) {
				return new TransactionStatus(true, FaultDescription.ok);
			} else {
				return new TransactionStatus(true, FaultDescription.not_completed);
			}
		} else {
			return new TransactionStatus(true, FaultDescription.not_enought_money);

		}
	}

}
