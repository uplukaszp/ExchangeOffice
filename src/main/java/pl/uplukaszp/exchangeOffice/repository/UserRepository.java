package pl.uplukaszp.exchangeOffice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.uplukaszp.exchangeOffice.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

	public User findByLogin(String login);
}
