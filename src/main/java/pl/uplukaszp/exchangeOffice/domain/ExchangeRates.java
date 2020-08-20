package pl.uplukaszp.exchangeOffice.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ExchangeRates {
	@JsonProperty("Items")
	private List<ExchangeRate> items;
	@JsonProperty("PublicationDate")
	private Date publicationDate;
}
