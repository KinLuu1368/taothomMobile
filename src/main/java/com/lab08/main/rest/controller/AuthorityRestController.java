package com.lab08.main.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lab08.main.Entity.Authority;
import com.lab08.main.service.AuthorityService;

@RestController
@CrossOrigin("*")
@RequestMapping("/rest/authorities")
public class AuthorityRestController {
    @Autowired
    AuthorityService authorityService;

    @GetMapping("/rest/authorities")
    public List<Authority> findAll(@RequestParam("admin") Optional<Boolean> admin) {
        if (admin.orElse(false)) {
            return authorityService.findAuthoritiesOfAdministrators();
        }
        return authorityService.findAll();
    }

    @PostMapping("/rest/authorities")
    public Authority post(@RequestBody Authority auth) {
        return authorityService.create(auth);
    }

    @DeleteMapping("/rest/authorities/{id}")
    public void delete(@PathVariable("id") Integer id) {
        authorityService.delete(id);
    }
}
