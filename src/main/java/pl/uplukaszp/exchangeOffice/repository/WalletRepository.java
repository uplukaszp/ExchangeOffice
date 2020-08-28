package pl.uplukaszp.exchangeOffice.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.uplukaszp.exchangeOffice.domain.Currency;
import pl.uplukaszp.exchangeOffice.domain.Wallet;

@Repository
public interface WalletRepository extends CrudRepository<Wallet,Long> {
	public List<Wallet> findAllByIdUserId(Long userId);
	public Wallet findByIdUserIdAndIdCurrency(Long userId,Currency currency);
	public List<Wallet> findAllByIdUserIdAndIdCurrencyNotIn(Long userId,List<Currency> currency);
	public List<Wallet> saveAll(List<Wallet> wallets);

}
