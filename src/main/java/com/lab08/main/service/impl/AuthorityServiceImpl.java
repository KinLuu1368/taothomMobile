package com.lab08.main.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lab08.main.DAO.AccountDAO;
import com.lab08.main.DAO.AuthorityDAO;
import com.lab08.main.Entity.Account;
import com.lab08.main.Entity.Authority;
import com.lab08.main.service.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService {
    @Autowired
    AuthorityDAO dao;
    @Autowired
    AccountDAO acDao;

    public Authority create(Authority auth) {
        return dao.save(auth);
    }

    public List<Authority> findAll() {
        return dao.findAll();
    }

    public void delete(Integer id) {
        dao.deleteById(id);
    }

    public List<Authority> findAuthoritiesOfAdministrators() {
        List<Account> accounts = acDao.getAdministrators();
        return dao.authoritiesOf(accounts);
    }
}
