package pl.uplukaszp.exchangeOffice.util;

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
import pl.uplukaszp.exchangeOffice.domain.ExchangeRates;
import pl.uplukaszp.exchangeOffice.repository.ExchangeRateRepository;
import pl.uplukaszp.exchangeOffice.service.ExchangeRateService;

@Component
@AllArgsConstructor
@Slf4j
public class ExchangeRateHandler extends TextWebSocketHandler {
	ExchangeRateRepository repository;
	ExchangeRateService service;
	SimpMessagingTemplate messagingTemplate;
	ObjectMapper mapper;

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		super.handleTextMessage(session, message);
		log.info("Incoming text message: " + message.getPayload());
		saveRatesFromMessage(message);
		messagingTemplate.convertAndSend("/currencies/",service.getRates() );
	}

	private void saveRatesFromMessage(TextMessage message) throws JsonProcessingException, JsonMappingException {
		ExchangeRates rates = mapper.readValue(message.getPayload(), ExchangeRates.class);
		rates.getItems().stream().forEach(item -> item.setDate(rates.getPublicationDate()));
		service.saveAll(rates.getItems());
	
	}

}