package com.api_examen_final._20201b873.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 30)
    private String nameCustomer;
    @Column(nullable = false,length = 13)
    private String numberAccount;
}
