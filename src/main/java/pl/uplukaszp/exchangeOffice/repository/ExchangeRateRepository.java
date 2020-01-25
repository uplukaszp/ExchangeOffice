package pl.uplukaszp.exchangeOffice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.uplukaszp.exchangeOffice.domain.Currency;
import pl.uplukaszp.exchangeOffice.domain.ExchangeRate;

@Repository
public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, Currency> {

}
