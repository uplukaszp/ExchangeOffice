package pl.uplukaszp.exchangeOffice.domain;

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
	private Float purchasePrice;
	@JsonProperty("SellPrice")
	private Float sellPrice;
	@JsonProperty("AveragePrice")
	private Float averagePrice;
	@JsonIgnore
	private Date date;
}
