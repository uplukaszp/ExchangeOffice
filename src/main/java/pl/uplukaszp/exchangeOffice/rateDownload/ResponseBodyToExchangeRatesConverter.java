package pl.uplukaszp.exchangeOffice.rateDownload;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.slf4j.Slf4j;
import pl.uplukaszp.exchangeOffice.domain.Currency;
import pl.uplukaszp.exchangeOffice.domain.ExchangeRate;
import pl.uplukaszp.exchangeOffice.domain.ExchangeRates;

@Component
@Slf4j
public class ResponseBodyToExchangeRatesConverter {
	private static final String pattern = "yyyy-MM-dd";

	public ExchangeRates convert(JsonNode body) {
		ExchangeRates rates = new ExchangeRates();
		rates.setPublicationDate(getDate(body.get("date")));
		List<ExchangeRate> items = getItems(body.get("rates"), rates.getPublicationDate());
		rates.setItems(items);
		return rates;
	}

	private Date getDate(JsonNode jsonNode) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(jsonNode.asText());
		} catch (ParseException | NullPointerException e) {
//			log.error("Error when parsing date", e);
			return new Date();
		}
	}

	private List<ExchangeRate> getItems(JsonNode rates, Date date) {
		List<ExchangeRate> list = new ArrayList<>();
		if (rates != null) {
			for (Currency currency : Currency.values()) {
				ExchangeRate exRate = new ExchangeRate();
				JsonNode rate = rates.get(currency.toString());
				if (rate == null)
					continue;
				BigDecimal price = rate.decimalValue();
				exRate.setUnit(1);
				exRate.setSellPrice(price);
				exRate.setPurchasePrice(price);
				exRate.setAveragePrice(price);
				exRate.setCode(currency);
				exRate.setDate(date);
				list.add(exRate);
			}
		}
		return list;
	}
}
