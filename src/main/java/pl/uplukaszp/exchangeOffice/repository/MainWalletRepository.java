package pl.uplukaszp.exchangeOffice.repository;

import org.springframework.data.repository.CrudRepository;

import pl.uplukaszp.exchangeOffice.domain.Currency;
import pl.uplukaszp.exchangeOffice.domain.MainWallet;

public interface MainWalletRepository extends CrudRepository<MainWallet, Currency> {

}
