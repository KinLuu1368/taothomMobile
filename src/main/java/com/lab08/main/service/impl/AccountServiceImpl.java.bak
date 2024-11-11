package com.lab08.main.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lab08.main.DAO.AccountDAO;
import com.lab08.main.Entity.Account;
import com.lab08.main.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountDAO adao;

    public Account findById(String username) {
        return adao.findById(username).get();
    }

    public List<Account> getAdministrators() {
        return adao.getAdministrators();
    }

    @Override
    public Account create(Account account) {
        // Kiem tra trong username
        if (adao.findById(account.getUsername()).isPresent()) {
            throw new RuntimeException("Username đã ton tai!");
        }
        // Kiem tra trong email
        if (adao.findByEmail(account.getEmail()).isPresent()) {
            throw new RuntimeException("Email da ton tai!");
        }
        return adao.save(account);
    }

    @Override
    public List<Account> findAll() {
        return adao.findAll();
    }
}
