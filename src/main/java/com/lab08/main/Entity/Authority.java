package com.lab08.main.Entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "Authorities")
public class Authority implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "Username")
    Account account;

    @ManyToOne
    @JoinColumn(name = "Roleid")
    Role role;
}
