package pl.uplukaszp.exchangeOffice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pl.uplukaszp.exchangeOffice.domain.Wallet;
import pl.uplukaszp.exchangeOffice.repository.UserRepository;
import pl.uplukaszp.exchangeOffice.repository.WalletRepository;

@Service
@AllArgsConstructor
public class WalletService {
	UserRepository userRepo;
	WalletRepository walletRepo;
	public List<Wallet> getUserWallets(String username) {
		System.out.println(username);
		Long userId=userRepo.findByLogin(username).getId();
		return walletRepo.findAllByIdUserId(userId);
	}
}
