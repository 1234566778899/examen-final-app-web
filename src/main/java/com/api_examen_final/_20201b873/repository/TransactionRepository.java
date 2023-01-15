package com.api_examen_final._20201b873.repository;

import com.api_examen_final._20201b873.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("select t from Transaction t where t.account.nameCustomer=?1")
    List<Transaction> findAllByNameCustomer(String nameCustomer);
    @Query("select t from Transaction t where t.createDate between ?1 and ?2")
    List<Transaction> finAllByCreateDateRange(LocalDate startDate,LocalDate endDate);
}