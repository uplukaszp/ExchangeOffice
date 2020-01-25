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
	String name;
	@Id
	@JsonProperty("Code")
	Currency code;
	@JsonProperty("Unit")
	Integer unit;
	@JsonProperty("PurchasePrice")
	Float purchasePrice;
	@JsonProperty("SellPrice")
	Float sellPrice;
	@JsonProperty("AveragePrice")
	Float averagePrice;
	@JsonIgnore
	Date date;
}
