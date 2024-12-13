package com.lab08.main.service;

import java.util.List;
import java.util.Optional;

import com.lab08.main.Entity.Account;

public interface AccountService {
    Account findById(String username);

    List<Account> getAdministrators();

    public List<Account> findAll();

    Account create(Account account);

    boolean isUsernameExists(String username);
    
    Optional<Account> findByEmail(String email);
    void update(Account account);
    void changePassword(String email, String newPassword);
    
}
