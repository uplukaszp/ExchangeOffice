package pl.uplukaszp.exchangeOffice.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class MainWallet {
	@Id
	private Currency currency;
	private BigDecimal amount;
}
