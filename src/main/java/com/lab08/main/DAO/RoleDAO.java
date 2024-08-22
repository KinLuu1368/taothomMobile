package com.lab08.main.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lab08.main.Entity.Role;

public interface RoleDAO extends JpaRepository<Role, String> {

}
