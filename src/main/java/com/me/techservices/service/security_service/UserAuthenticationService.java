package com.me.techservices.service.security_service;

import com.me.techservices.entity.Token;
import com.me.techservices.entity.UserAccount;
import com.me.techservices.entity.UserRole;
import com.me.techservices.repository.UserAccountRepository;
import com.me.techservices.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountException;

@Service
@RequiredArgsConstructor
public class UserAuthenticationService {
    private final AuthenticationManager authManager;
    private final JWTUtils jwtGenerationService;
    private final UserAccountRepository userAccountRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public Token loginUser(String username, String password) throws AccountException {
        Authentication authentication = authenticateUser(username, password);
        return generateUserTokens(authentication);
    }

    public void register(UserAccount userAccount) throws AccountException {
        if (userAccountRepository.existsByUsername(userAccount.getUsername())) {
            throw new AccountException("Пользователь уже зарегистрирован");
        }
        userRoleRepository.findByRoleType(UserRole.RoleType.ROLE_USER)
                .ifPresentOrElse(userAccount::setRole,
                        () -> {
                            UserRole userRole = new UserRole();
                            userRole.setRoleType(UserRole.RoleType.ROLE_USER);
                            userAccount.setRole(userRole);
                            userRoleRepository.save(userRole);
                        }
                        );
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        userAccountRepository.save(userAccount);
    }

    private Authentication authenticateUser(String username, String password) {
        return authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private Token generateUserTokens(Authentication authentication) throws AccountException {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        try {
            return createTokens(userDetails);
        } catch (Exception e) {
            throw new AccountException();
        }
    }

    private Token createTokens(UserDetails userDetails) throws Exception {
        Token token = new Token();
        token.setToken(jwtGenerationService.generateToken(userDetails));
        token.setRefresh(jwtGenerationService.generateRefreshToken());
        return token;
    }
}
