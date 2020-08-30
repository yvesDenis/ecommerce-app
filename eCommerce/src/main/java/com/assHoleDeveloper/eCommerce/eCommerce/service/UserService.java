package com.assHoleDeveloper.eCommerce.eCommerce.service;

import com.assHoleDeveloper.eCommerce.eCommerce.configuration.LabelsConfiguration;
import com.assHoleDeveloper.eCommerce.eCommerce.model.User;
import com.assHoleDeveloper.eCommerce.eCommerce.model.UserSecurity;
import com.assHoleDeveloper.eCommerce.eCommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LabelsConfiguration labelsConfiguration;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(labelsConfiguration.getUserNotFoundForAutentication()));

        return UserSecurity.build(user);
    }
}
