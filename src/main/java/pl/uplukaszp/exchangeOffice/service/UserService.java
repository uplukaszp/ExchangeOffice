package pl.uplukaszp.exchangeOffice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pl.uplukaszp.exchangeOffice.domain.Currency;
import pl.uplukaszp.exchangeOffice.domain.User;
import pl.uplukaszp.exchangeOffice.domain.Wallet;
import pl.uplukaszp.exchangeOffice.dto.UserDTO;
import pl.uplukaszp.exchangeOffice.repository.UserRepository;
import pl.uplukaszp.exchangeOffice.repository.WalletRepository;;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

	private UserRepository userRepository;
	private WalletRepository walletRepository;
	private PasswordEncoder encoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByLogin(username);
		if (user != null) {
			return user;
		}
		throw new UsernameNotFoundException("User " + username + " not found");
	}

	public User registerUser(UserDTO userDTO) {
		User user = new User();
		user.setLogin(userDTO.getLogin());
		user.setPassword(encoder.encode(userDTO.getPassword()));
		user = userRepository.save(user);

		user.setWallets(getWallets(userDTO, user.getId()));
		user.setWallets((List<Wallet>) walletRepository.saveAll(user.getWallets()));

		return user;
	}

	private List<Wallet> getWallets(UserDTO userDTO, Long id) {
		List<Wallet> wallets = new ArrayList<>();

		wallets.add(new Wallet(id, Currency.CZK, getNonNullValue(userDTO.getCzk())));
		wallets.add(new Wallet(id, Currency.EUR, getNonNullValue(userDTO.getEur())));
		wallets.add(new Wallet(id, Currency.GBP, getNonNullValue(userDTO.getGbp())));
		wallets.add(new Wallet(id, Currency.PLN, getNonNullValue(userDTO.getPln())));
		wallets.add(new Wallet(id, Currency.USD, getNonNullValue(userDTO.getUsd())));
		wallets.add(new Wallet(id, Currency.CHF, getNonNullValue(userDTO.getUsd())));
		wallets.add(new Wallet(id, Currency.RUB, getNonNullValue(userDTO.getUsd())));

		return wallets;
	}

	private Float getNonNullValue(Float value) {
		return (value != null) ? value : 0.0f;
	}
}
