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
    public List<Account> findAll() {
        return adao.findAll();
    }
}
