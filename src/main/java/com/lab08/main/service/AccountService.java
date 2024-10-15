package com.lab08.main.service;

import java.util.List;

import com.lab08.main.Entity.Account;

public interface AccountService {
    Account findById(String username);

    List<Account> getAdministrators();

    public List<Account> findAll();

    Account create(Account account);
}
