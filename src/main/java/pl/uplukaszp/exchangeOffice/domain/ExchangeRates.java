package pl.uplukaszp.exchangeOffice.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ExchangeRates {
	private List<ExchangeRate> items;
	private Date publicationDate;
}
