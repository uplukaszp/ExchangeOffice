package pl.uplukaszp.exchangeOffice.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.uplukaszp.exchangeOffice.domain.Currency;
import pl.uplukaszp.exchangeOffice.domain.Wallet;

@Repository
public interface WalletRepository extends CrudRepository<Wallet,Long> {
	List<Wallet> findAllByIdUserId(Long userId);
	Wallet findByIdUserIdAndIdCurrency(Long userId,Currency currency);
	List<Wallet> findAllByIdUserIdAndIdCurrencyNotIn(Long userId,List<Currency> currency);


}
