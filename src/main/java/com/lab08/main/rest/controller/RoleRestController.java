package com.lab08.main.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lab08.main.Entity.Role;
import com.lab08.main.service.RoleService;

@RestController
@CrossOrigin("*")
@RequestMapping("/rest/roles")
public class RoleRestController {
    @Autowired
    RoleService roleService;

    @GetMapping()
    public List<Role> getAll() {
        return roleService.findAll();
    }
}
