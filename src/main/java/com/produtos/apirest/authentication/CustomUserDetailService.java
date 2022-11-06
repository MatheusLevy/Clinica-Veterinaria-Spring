package com.produtos.apirest.authentication;

import com.produtos.apirest.repository.UserRepo;
import com.produtos.apirest.service.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepo userRepo;

    public CustomUserDetailService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.produtos.apirest.models.User userFind = userRepo.findByUsername(username);
        UserService.verifyAllRules(userFind);
        UserService.hasId(userFind);
        return new User(userFind.getUsername(), userFind.getPassword(), true, true, true, true, userFind.getAuthorities());
    }
}
