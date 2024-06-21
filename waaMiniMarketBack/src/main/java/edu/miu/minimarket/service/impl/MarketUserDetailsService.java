package edu.miu.minimarket.service.impl;

import edu.miu.minimarket.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
@Transactional
public class MarketUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MarketUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(username);
        var userDetails = new MarketUserDetails(user.orElse(null));
        return userDetails;
    }

}
