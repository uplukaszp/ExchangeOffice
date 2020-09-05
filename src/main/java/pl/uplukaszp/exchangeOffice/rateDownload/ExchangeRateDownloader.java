package pl.uplukaszp.exchangeOffice.rateDownload;

import java.text.ParseException;

import javax.annotation.PostConstruct;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.uplukaszp.exchangeOffice.domain.ExchangeRates;
import pl.uplukaszp.exchangeOffice.service.ExchangeRateService;

@Component
@AllArgsConstructor
@Slf4j
public class ExchangeRateDownloader {
	private static final String URL = "https://api.exchangeratesapi.io/latest?base=PLN";
	private ExchangeRateService service;
	private RestTemplate restTemplate;
	private ResponseBodyToExchangeRatesConverter converter;

	@Scheduled(fixedDelay = 5000)
	@PostConstruct
	public void downlaodRates() throws ParseException {
		ResponseEntity<JsonNode> response = restTemplate.getForEntity(URL, JsonNode.class);
		ExchangeRates rates = converter.convert(response.getBody());
		log.info("downloaded rates:" + rates);
		service.saveAll(rates.getItems());
	}

}
