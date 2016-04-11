package de.knuff0r.bsb.service;

import de.knuff0r.bsb.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UserRepository extends CrudRepository<User,Long> {
	
	User findUserByEmail(String email);

	User findUserByUsername(String username);

	User findUserByActivateKey(String key);

	Iterable<User> findUserByActive(boolean accepted);

	Iterable<User> findUserByAcceptedAndActive(boolean accepted, boolean active);

}
