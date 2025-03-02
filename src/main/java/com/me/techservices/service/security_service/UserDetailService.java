package com.me.techservices.service.security_service;

import com.me.techservices.entity.UserAccount;
import com.me.techservices.exception.AccountException;
import com.me.techservices.exception.ServiceException;
import com.me.techservices.repository.UserAccountRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserDetailService implements UserDetailsService {
    private final UserAccountRepository userAccountRepository;

    @Autowired
    public UserDetailService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        GrantedAuthority authority = new SimpleGrantedAuthority(userAccount.getRole().getRoleType().name());
        System.out.println(authority.getAuthority());

        return new User(username, userAccount.getPassword(), Set.of(authority));
    }
}
