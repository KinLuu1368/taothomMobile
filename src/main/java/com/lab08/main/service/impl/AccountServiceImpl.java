package com.lab08.main.service.impl;

import java.util.List;
import java.util.Optional;

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

    public boolean isUsernameExists(String username) {
        return adao.findById(username).isPresent();
    }

    public Optional<Account> findByEmail(String email) {
        return adao.findByEmail(email);
    }
    public void update(Account account) {
            adao.save(account);
    }

    @Override
    public void changePassword(String email, String newPassword) {
        Optional<Account> accountOpt = adao.findByEmail(email);
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            account.setPassword(newPassword);  // Cập nhật mật khẩu mới
            adao.save(account);   // Lưu lại tài khoản với mật khẩu đã thay đổi
        }
    }
}
