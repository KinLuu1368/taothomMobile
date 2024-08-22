package com.lab08.main.service;

import java.util.List;

import com.lab08.main.Entity.Authority;

public interface AuthorityService {
    public List<Authority> findAuthoritiesOfAdministrators();

    public Authority create(Authority auth);

    public List<Authority> findAll();

    public void delete(Integer id);
}
