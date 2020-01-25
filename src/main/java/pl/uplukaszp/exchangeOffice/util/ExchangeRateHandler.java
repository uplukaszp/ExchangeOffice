package pl.uplukaszp.exchangeOffice.util;

import java.util.List;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.uplukaszp.exchangeOffice.domain.ExchangeRate;
import pl.uplukaszp.exchangeOffice.domain.ExchangeRates;
import pl.uplukaszp.exchangeOffice.repository.ExchangeRateRepository;

@Component
@AllArgsConstructor
@Slf4j
public class ExchangeRateHandler extends TextWebSocketHandler {
	ExchangeRateRepository repository;
	SimpMessagingTemplate messagingTemplate;
	ObjectMapper mapper;

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		super.handleTextMessage(session, message);
		log.info("Incoming text message: " + message.getPayload());
		List<ExchangeRate> savedRated = saveRatesFromMessage(message);
		messagingTemplate.convertAndSend("/currencies/", savedRated);
	}

	private List<ExchangeRate> saveRatesFromMessage(TextMessage message) throws JsonProcessingException, JsonMappingException {
		ExchangeRates rates = mapper.readValue(message.getPayload(), ExchangeRates.class);
		rates.getItems().stream().forEach(item -> item.setDate(rates.getPublicationDate()));
		List<ExchangeRate> savedRated = (List<ExchangeRate>) repository.saveAll(rates.getItems());
		return savedRated;
	}

}