package com.api_examen_final._20201b873.controller;

import com.api_examen_final._20201b873.exeption.ResourceNotFoundException;
import com.api_examen_final._20201b873.exeption.ValidationException;
import com.api_examen_final._20201b873.model.Account;
import com.api_examen_final._20201b873.model.Transaction;
import com.api_examen_final._20201b873.repository.AccountRepository;
import com.api_examen_final._20201b873.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/api/bank/v1")
public class TransactionController {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;
    @PostMapping("/accounts/{id}/transactions")
    public ResponseEntity<Transaction> save(@PathVariable Long id, @RequestBody Transaction transaction){
        validateTransaction(transaction);
        transaction.setCreateDate(LocalDate.now());
        transaction.setBalance(calcularBalance(transaction));
        Account account=accountRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("No existe la cuenta con ese id no existe: "+id));
        transaction.setAccount(account);
        return new ResponseEntity<>(transactionRepository.save(transaction), HttpStatus.OK);
    }
    @GetMapping("/transactions/filterByNameCustomer")
    public ResponseEntity<List<Transaction>> findAllByNameClient(@RequestParam String nameCustomer){
        return new ResponseEntity<>(transactionRepository.findAllByNameCustomer(nameCustomer),HttpStatus.OK);
    }
    @GetMapping("/transactions/filterByCreateDateRange")
    public ResponseEntity<List<Transaction>> findAllByCreateDate(@RequestParam String startDate,@RequestParam String endDate){
        return new ResponseEntity<>(transactionRepository.finAllByCreateDateRange(LocalDate.parse(startDate),LocalDate.parse(endDate)),HttpStatus.OK);
    }
    private void validateTransaction(Transaction transaction){
        if(transaction.getType().trim().isEmpty()||transaction.getType()==null){
            throw new ValidationException("El tipo de transacción bancaria debe ser obligatorio");
        }
       /*if(!(transaction.getType()=="Retiro" || transaction.getType()=="Deposito")){
            throw new ValidationException("El tipo debe ser Retiro o Deposito: "+transaction.getType().toLowerCase());
        }*/
        if(transaction.getAmount()<=0){
            throw new ValidationException("El monto en una transacción bancaria debe ser mayor de S/.0.0");
        }
        if(transaction.getAmount()>transaction.getBalance() && transaction.getType()=="Retiro"){
            throw new ValidationException("En una transacción bancaria tipo retiro el monto no puede ser mayor al saldo");
        }

    }
    private Double calcularBalance(Transaction transaction){
        if(transaction.getType()=="Deposito"){
            return transaction.getBalance()+transaction.getAmount();
        }
        return transaction.getBalance()-transaction.getAmount();
    }
}
