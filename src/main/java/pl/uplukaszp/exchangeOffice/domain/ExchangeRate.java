package pl.uplukaszp.exchangeOffice.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class ExchangeRate {
	@Id
	private Currency code;
	private Integer unit;
	private BigDecimal purchasePrice;
	private BigDecimal sellPrice;
	private BigDecimal averagePrice;
	private Date date;
}
