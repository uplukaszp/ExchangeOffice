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

	public User findByLogin(String login) {
		return userRepository.findByLogin(login);
	}

	public User registerNewUser(UserDTO userDTO) {
		User user = new User();
		user.setLogin(userDTO.getLogin());
		user.setPassword(getUserEncodedPassword(userDTO));
		user = userRepository.save(user);
		user.setWallets(getNewUserWallets(userDTO, user.getId()));

		return user;
	}

	private String getUserEncodedPassword(UserDTO userDTO) {
		return encoder.encode(userDTO.getPassword());
	}

	private List<Wallet> getNewUserWallets(UserDTO userDTO, Long userId) {
		List<Wallet> userWalletsInitalValues = getWalletsInitalValues(userDTO, userId);
		List<Wallet> savedWallets = walletRepository.saveAll(userWalletsInitalValues);
		return savedWallets;
	}

	private List<Wallet> getWalletsInitalValues(UserDTO userDTO, Long id) {
		List<Wallet> wallets = new ArrayList<>();

		wallets.add(new Wallet(id, Currency.CZK, userDTO.getCzk()));
		wallets.add(new Wallet(id, Currency.EUR, userDTO.getEur()));
		wallets.add(new Wallet(id, Currency.GBP, userDTO.getGbp()));
		wallets.add(new Wallet(id, Currency.PLN, userDTO.getPln()));
		wallets.add(new Wallet(id, Currency.USD, userDTO.getUsd()));
		wallets.add(new Wallet(id, Currency.CHF, userDTO.getCfh()));
		wallets.add(new Wallet(id, Currency.RUB, userDTO.getRub()));

		return wallets;
	}
}
