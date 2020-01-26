package pl.uplukaszp.exchangeOffice.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class MainWallet {
	@Id
	Currency currency;
	Float amount;
}
