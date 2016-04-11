package de.knuff0r.bsb.app;

import de.knuff0r.bsb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component("userDetailsService")
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) {
        de.knuff0r.bsb.domain.User user = userService.getUserByUsername(usernameOrEmail);
        if (user == null)
            user = userService.getUserByEmail(usernameOrEmail);
        if (user == null)
            throw new UsernameNotFoundException("No such user: " + usernameOrEmail);

        return new User(user.getUsername(), user.getPassword(), true, user.getAccepted(), true,
                user.getActive(), AuthorityUtils.createAuthorityList(user.getRole().toString()));

    }
}
