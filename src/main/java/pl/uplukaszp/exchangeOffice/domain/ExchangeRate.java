package pl.uplukaszp.exchangeOffice.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Entity
public class ExchangeRate {
	@JsonProperty("Name")
	private String name;
	@Id
	@JsonProperty("Code")
	private Currency code;
	@JsonProperty("Unit")
	private Integer unit;
	@JsonProperty("PurchasePrice")
	private BigDecimal purchasePrice;
	@JsonProperty("SellPrice")
	private BigDecimal sellPrice;
	@JsonProperty("AveragePrice")
	private BigDecimal averagePrice;
	@JsonIgnore
	private Date date;
}
