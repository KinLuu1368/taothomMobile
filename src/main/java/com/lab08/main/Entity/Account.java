package com.lab08.main.Entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "Accounts")
public class Account implements Serializable {
    @Id
    String username;
    String password;
    String fullname;
    String email;
    String photo;

    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    List<Order> orders;

    @JsonIgnore
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    List<Authority> authorities;

}
