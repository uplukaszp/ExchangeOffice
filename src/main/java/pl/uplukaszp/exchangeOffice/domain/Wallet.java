package pl.uplukaszp.exchangeOffice.domain;

import java.io.Serializable;
import java.math.BigDecimal;

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
	private BigDecimal amount;

	public Wallet(Long id, Currency currency, BigDecimal amount) {
		this.id = new WalletId();
		this.id.setCurrency(currency);
		this.id.setUserId(id);
		this.amount = amount;
	}

	public Currency getCurrency() {
		return id.getCurrency();
	}
}
