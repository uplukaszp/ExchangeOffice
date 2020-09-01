package pl.uplukaszp.exchangeOffice.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.uplukaszp.exchangeOffice.domain.Currency;
import pl.uplukaszp.exchangeOffice.domain.ExchangeRate;
import pl.uplukaszp.exchangeOffice.domain.ExchangeRates;
import pl.uplukaszp.exchangeOffice.service.ExchangeRateService;

@Component
@AllArgsConstructor
@Slf4j
public class ExchangeRateDownloader {
	private static final String URL = "https://api.exchangeratesapi.io/latest?base=PLN";
	private static final String pattern = "yyyy-MM-dd";
	private ExchangeRateService service;
	private RestTemplate restTemplate;

	@Scheduled(fixedDelay = 5000)
	@PostConstruct
	public void downlaodRates() throws ParseException {
		ResponseEntity<JsonNode> response = restTemplate.getForEntity(URL, JsonNode.class);
		ExchangeRates rates = getRatesFromBody(response.getBody());
		log.info("downloaded rates:" + rates);
		service.saveAll(rates.getItems());
	}

	private ExchangeRates getRatesFromBody(JsonNode body) throws ParseException {
		ExchangeRates rates = new ExchangeRates();
		rates.setPublicationDate(getDate(body.get("date").asText()));
		List<ExchangeRate> items = getItems(body.get("rates"), rates.getPublicationDate());
		rates.setItems(items);
		return rates;
	}

	private Date getDate(String dateString) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.parse(dateString);
	}

	private List<ExchangeRate> getItems(JsonNode rates, Date date) {
		List<ExchangeRate> list = new ArrayList<>();
		for (Currency currency : Currency.values()) {
			ExchangeRate exRate = new ExchangeRate();
			JsonNode rate = rates.get(currency.toString());
			BigDecimal price = rate.decimalValue();
			exRate.setUnit(1);
			exRate.setSellPrice(price);
			exRate.setPurchasePrice(price);
			exRate.setAveragePrice(price);
			exRate.setCode(currency);
			exRate.setDate(date);
			list.add(exRate);
		}
		return list;
	}

}
