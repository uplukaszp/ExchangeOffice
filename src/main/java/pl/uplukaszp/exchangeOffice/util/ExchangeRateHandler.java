package pl.uplukaszp.exchangeOffice.util;

import java.nio.ByteBuffer;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.uplukaszp.exchangeOffice.domain.Currencies;
import pl.uplukaszp.exchangeOffice.repository.ExchangeRateRepository;

@Component
@AllArgsConstructor
@Slf4j
public class ExchangeRateHandler implements WebSocketHandler {
	ObjectMapper mapper;
	ExchangeRateRepository repository;
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("Connection established");
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		if (message instanceof TextMessage) {
			TextMessage text = (TextMessage) message;
			log.info("Incoming text message: " + text.getPayload());
			Currencies currencies = mapper.readValue(text.asBytes(), Currencies.class);
			log.info(currencies.toString());
			repository.saveAll(currencies.getItems());
		}
		if (message instanceof PongMessage) {
			PongMessage pong = (PongMessage) message;
			ByteBuffer payload = pong.getPayload();
			log.info("Incoming pong message: " + new String(payload.array()));
		}

	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		log.info("exception " + exception);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		log.info("closed: " + closeStatus.getReason());

	}

	@Override
	public boolean supportsPartialMessages() {
		return true;
	}

}