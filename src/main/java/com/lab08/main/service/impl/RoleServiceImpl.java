package com.lab08.main.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lab08.main.DAO.RoleDAO;
import com.lab08.main.Entity.Role;
import com.lab08.main.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleDAO rdao;

    @Override
    public List<Role> findAll() {
        return rdao.findAll();
    }

}
