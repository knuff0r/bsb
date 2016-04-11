package de.knuff0r.bsb.service;

import de.knuff0r.bsb.domain.User;

public interface UserService {

    User save(User user);

    void delete(User user);

    void delete(Long id);

    long count();

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    User getUserByKey(String key);

    User getUser(Long id);

    Iterable<User> getAll();

    User createUser(User u);

    Iterable<User> getNonAcceptedUsers();

    Iterable<User> getNonActiveUsers();

    Long getClosest(Long id);


}
