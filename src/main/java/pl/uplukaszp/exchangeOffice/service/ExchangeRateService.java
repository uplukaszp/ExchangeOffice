package pl.uplukaszp.exchangeOffice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pl.uplukaszp.exchangeOffice.domain.Currency;
import pl.uplukaszp.exchangeOffice.domain.ExchangeRate;
import pl.uplukaszp.exchangeOffice.dto.ExchangeRateDTO;
import pl.uplukaszp.exchangeOffice.repository.ExchangeRateRepository;

@Service
@AllArgsConstructor
public class ExchangeRateService {
	ExchangeRateRepository repo;

	public List<ExchangeRateDTO> getRates() {
		List<ExchangeRate> rates = (List<ExchangeRate>) repo.findAll();
		List<ExchangeRateDTO> dtos = new ArrayList<>();
		List<Currency> currencies = new ArrayList<Currency>(Arrays.asList(Currency.values()));
		currencies.remove(Currency.PLN);
		for (Currency currency : currencies) {
			ExchangeRateDTO dto = new ExchangeRateDTO();
			dto.setCode(currency);
			dto.setIsRateAviable(false);
			dto.setPurchasePrice("-");
			dto.setUnit("-");
			for (ExchangeRate rate : rates) {
				if (rate.getCode().equals(currency)) {
					dto.setIsRateAviable(true);
					dto.setPurchasePrice(String.valueOf(rate.getPurchasePrice()));
					dto.setUnit(String.valueOf(rate.getUnit()));
					dto.setDate(rate.getDate());
					dtos.add(dto);
				}
			}
		}
		return dtos;
	}
	public List<ExchangeRate> saveAll(List<ExchangeRate> rates){
		return (List<ExchangeRate>) repo.saveAll(rates);
	}

}
