package com.apress.security;

import com.apress.domain.User;
import com.apress.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 05-11-18
 *
 * @author Tom
 */

@Component
public class QuickPollUserDetailsService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);


        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with the username %s doesn't exist", username));
        }
        // create a granted authority based on user's role
        // Can't pass null authorities to user. Hence initialize with an empty arrayList
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user.isAdmin()) {
            authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN");

        }

        // Create a UserDetails object from the data. The o.s.s.c.u. User instance
        UserDetails userDetails = new org.springframework.security.core.userdetails.
                User(user.getUsername(), user.getPassword(), authorities);

//        logger.debug("username and password logging: " + user.getUsername() + ", "+ user.getPassword());

        return userDetails;

    }
}
