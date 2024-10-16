package com.lab08.main.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lab08.main.Entity.Account;

public interface AccountDAO extends JpaRepository<Account, String> {
    @Query("SELECT DISTINCT ar.account FROM Authority ar WHERE ar.role.id IN ('DIRE','STAF')")
    List<Account> getAdministrators();
    Optional<Account> findByEmail(String email);
}
