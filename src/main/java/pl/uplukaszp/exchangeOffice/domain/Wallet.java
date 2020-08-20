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
	private WalletId id;
	private Float amount;

	public Wallet(Long id, Currency currency, Float amount) {
		this.id = new WalletId();
		this.id.setCurrency(currency);
		this.id.setUserId(id);
		this.amount = amount;
	}

	public Currency getCurrency() {
		return id.getCurrency();
	}
}
