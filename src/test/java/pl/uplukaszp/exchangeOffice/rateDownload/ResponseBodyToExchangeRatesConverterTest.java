package pl.uplukaszp.exchangeOffice.rateDownload;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;

import pl.uplukaszp.exchangeOffice.domain.Currency;
import pl.uplukaszp.exchangeOffice.domain.ExchangeRate;
import pl.uplukaszp.exchangeOffice.domain.ExchangeRates;

public class ResponseBodyToExchangeRatesConverterTest {

	@Test
	public void emptyBodyTest() {
		// given
		ResponseBodyToExchangeRatesConverter converter = new ResponseBodyToExchangeRatesConverter();
		JsonNode body = NullNode.getInstance();

		// when
		ExchangeRates convert = converter.convert(body);
		// then
		assertTrue(convert.getItems().isEmpty());
		assertNotNull(convert.getPublicationDate());
	}

	@Test
	public void expectedBodyTest() throws IOException {
		// given
		ResponseBodyToExchangeRatesConverter converter = new ResponseBodyToExchangeRatesConverter();
		JsonNode body = getExpectedBody();

		// when
		ExchangeRates convert = converter.convert(body);
		// then
		assertFalse(convert.getItems().isEmpty());
		assertNotNull(convert.getItems().get(0));
		assertNotNull(convert.getPublicationDate());
		assertEquals(getExpectedDate(), convert.getPublicationDate());
		assertEquals(getExpectedExchangeRate(), convert.getItems().get(0));

	}

	private JsonNode getExpectedBody() throws IOException {
		ClassLoader classLoader = this.getClass().getClassLoader();
		InputStream is = classLoader.getResource("ExpectedResponse.json").openStream();
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readTree(is);

	}

	private Date getExpectedDate() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 2020);
		c.set(Calendar.DAY_OF_YEAR, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	private ExchangeRate getExpectedExchangeRate() {
		ExchangeRate e = new ExchangeRate();
		e.setCode(Currency.USD);
		e.setDate(getExpectedDate());
		e.setAveragePrice(new BigDecimal(1));
		e.setPurchasePrice(new BigDecimal(1));
		e.setSellPrice(new BigDecimal(1));
		e.setUnit(1);

		return e;
	}
}
