package de.knuff0r.bsb.service;

import de.knuff0r.bsb.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.security.SecureRandom;

@Component("userService")
@Transactional
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    public User createUser(User u) {
        String password = u.getPassword();
        if (password == null)
            password = new BigInteger(130, new SecureRandom()).toString(32);
        u.setPassword(encoder.encode(password));
        return u;
    }

    @Override
    public Iterable<User> getNonAcceptedUsers() {
        return userRepository.findUserByAcceptedAndActive(false,true);
    }

    @Override
    public Iterable<User> getNonActiveUsers() {
        return userRepository.findUserByActive(false);
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User getUserByKey(String key) {
        return userRepository.findUserByActivateKey(key);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }

    @Override
    public Long getClosest(Long id) {

        Long i = 0L;
        int count = 0;
        while (id - i > 0 && count <= userRepository.count()) {
            if (userRepository.findOne(id + i) != null) {
                return id + i;
            } else if (userRepository.findOne(id - i) != null) {
                return id - i;
            }
            i++;
            count++;
        }
        return 0L;
    }


}
