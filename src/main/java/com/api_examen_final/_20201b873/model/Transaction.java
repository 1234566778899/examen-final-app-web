package com.api_examen_final._20201b873.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
@Data
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private LocalDate createDate;
    @Column(nullable = false)
    private Double amount;
    @Column(nullable = false)
    private Double balance;
    @ManyToOne
    @JoinColumn(name = "account_id",nullable = false)
    private Account account;

}
