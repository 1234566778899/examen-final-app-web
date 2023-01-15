package com.api_examen_final._20201b873.repository;

import com.api_examen_final._20201b873.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByNameCustomer(String nameCustomer);
    boolean existsByNumberAccount(String numberAccount);
}