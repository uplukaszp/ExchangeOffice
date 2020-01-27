package pl.uplukaszp.exchangeOffice.util;

import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import lombok.AllArgsConstructor;
import pl.uplukaszp.exchangeOffice.util.ExchangeRateHandler;

@Component
@AllArgsConstructor
public class ExchangeRateDownloader {

	ExchangeRateHandler handler;
	private static final String url = "ws://webtask.future-processing.com:8068/ws/currencies?format=json";

	@PostConstruct
	public void connect() throws URISyntaxException {
		WebSocketClient client = new StandardWebSocketClient();
		client.doHandshake(handler, null, new URI(url));
	}
}
