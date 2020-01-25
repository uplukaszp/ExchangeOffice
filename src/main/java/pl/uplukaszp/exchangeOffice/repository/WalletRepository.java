package pl.uplukaszp.exchangeOffice.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.uplukaszp.exchangeOffice.domain.Wallet;

@Repository
public interface WalletRepository extends CrudRepository<Wallet,Long> {
	List<Wallet> findAllByIdUserId(Long userId);
}
