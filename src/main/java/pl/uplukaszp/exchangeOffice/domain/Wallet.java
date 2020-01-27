package pl.uplukaszp.exchangeOffice.domain;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Wallet implements Serializable {

	@EmbeddedId
	WalletId id;
	Float amount;

	public Wallet(Long id, Currency currency, Float amount) {
		this.id = new WalletId();
		this.id.currency = currency;
		this.id.userId = id;
		this.amount = amount;
	}

	public Currency getCurrency() {
		return id.currency;
	}
}
