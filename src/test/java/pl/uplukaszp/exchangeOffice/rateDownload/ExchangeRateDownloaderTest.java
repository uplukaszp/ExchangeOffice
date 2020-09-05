package pl.uplukaszp.exchangeOffice.rateDownload;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import pl.uplukaszp.exchangeOffice.domain.ExchangeRates;
import pl.uplukaszp.exchangeOffice.rateDownload.ExchangeRateDownloader;
import pl.uplukaszp.exchangeOffice.rateDownload.ResponseBodyToExchangeRatesConverter;
import pl.uplukaszp.exchangeOffice.service.ExchangeRateService;

public class ExchangeRateDownloaderTest {
	@Mock
	private RestTemplate restTemplate;
	@Mock
	private ExchangeRateService service;
	@Mock
	private ResponseBodyToExchangeRatesConverter converter;

	private ExchangeRateDownloader downloader;

	@BeforeEach
	public void createMocks() {
		MockitoAnnotations.initMocks(this);
		downloader = new ExchangeRateDownloader(service, restTemplate, converter);
	}

	@Test
	public void downloadTest() throws ParseException {
		// given
		ExchangeRates exRate = new ExchangeRates();
		ResponseEntity<JsonNode> response = new ResponseEntity<JsonNode>(mock(JsonNode.class), HttpStatus.OK);

		when(converter.convert(ArgumentMatchers.any(JsonNode.class))).thenReturn(exRate);
		when(restTemplate.getForEntity(ArgumentMatchers.anyString(), ArgumentMatchers.<Class<JsonNode>>any()))
				.thenReturn(response);
		// when
		downloader.downlaodRates();
		// then
		verify(service, times(1)).saveAll(null);
	}
}
