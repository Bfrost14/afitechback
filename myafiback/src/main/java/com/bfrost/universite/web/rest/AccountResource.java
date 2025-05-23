package com.bfrost.universite.web.rest;

import com.bfrost.universite.domain.User;
import com.bfrost.universite.repository.UserRepository;
import com.bfrost.universite.security.SecurityUtils;
import com.bfrost.universite.service.MailService;
import com.bfrost.universite.service.UserService;
import com.bfrost.universite.service.dto.AdminUserDTO;
import com.bfrost.universite.service.dto.PasswordChangeDTO;
import com.bfrost.universite.service.mapper.UserMapper;
import com.bfrost.universite.web.rest.errors.*;
import com.bfrost.universite.web.rest.vm.KeyAndPasswordVM;
import com.bfrost.universite.web.rest.vm.ManagedUserVM;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountResource {


    private static class AccountResourceException extends RuntimeException {

        private AccountResourceException(String message) {
            super(message);
        }
    }

    private static final Logger LOG = LoggerFactory.getLogger(AccountResource.class);


    private final UserService userService;



    public AccountResource(UserService userService) {

        this.userService = userService;
    }


    /**
     * {@code GET  /activate} : activate the registered user.
     *
     * @param email the activation key.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be activated.
     */
    @GetMapping("/activate")
    @PreAuthorize("hasAnyAuthority('ENREGISTREMENT_USER')")
    public void activateAccount(@RequestParam(value = "email") String email) {
        Optional<User> user = userService.activateRegistration(email);
        if (!user.isPresent()) {
            throw new AccountResourceException("No user was found for this activation key");
        }
    }


    /**
     * {@code GET  /account/change-password} : changes the current user's password.
     *
     * @param email current and new password.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the new password is incorrect.
     */
    @GetMapping(path = "/change-password")
    @PreAuthorize("hasAnyAuthority('ENREGISTREMENT_USER')")
    public void changePassword(@RequestParam("email") String email) {
        userService.changePassword(email);
    }



}
