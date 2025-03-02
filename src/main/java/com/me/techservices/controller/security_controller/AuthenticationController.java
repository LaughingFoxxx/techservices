package com.me.techservices.controller.security_controller;

import com.me.techservices.entity.Token;
import com.me.techservices.entity.UserAccount;
import com.me.techservices.service.security_service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.security.auth.login.AccountException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserAuthenticationService userAuthenticationService;

    @Autowired
    public AuthenticationController(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createAccount(UserAccount userAccount) {
        try {
            userAuthenticationService.register(userAccount);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AccountException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    public Token loginAccount(@RequestParam String username,
                              @RequestParam String password) {
        try {
            return userAuthenticationService.loginUser(username, password);
        } catch (AccountException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
