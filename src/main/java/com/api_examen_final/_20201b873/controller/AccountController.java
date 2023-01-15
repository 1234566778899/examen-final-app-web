package com.api_examen_final._20201b873.controller;

import com.api_examen_final._20201b873.exeption.ValidationException;
import com.api_examen_final._20201b873.model.Account;
import com.api_examen_final._20201b873.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/bank/v1")
public class AccountController {
    @Autowired
    private AccountRepository repo;
    @PostMapping("/accounts")
    public ResponseEntity<Account> save(@RequestBody Account account){
        validateAccount(account);
        validateExistsByNameOrNumber(account);
        return new ResponseEntity<>(repo.save(account),HttpStatus.CREATED);
    }
    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> findAll(){
        return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
    }
    private void validateAccount(Account account){
        if(account.getNameCustomer()==null||account.getNameCustomer().trim().isEmpty()){
            throw new ValidationException("El nombre del cliente debe ser obligatorio");
        }
        if(account.getNameCustomer().length()>30){
            throw new ValidationException("El nombre del cliente no debe exceder los 30 caracteres");
        }
        if(account.getNumberAccount().trim().isEmpty()||account.getNumberAccount()==null){
            throw new ValidationException("El número de cuenta debe ser obligatorio");
        }
        if(account.getNumberAccount().length()!=13){
            throw new ValidationException("El número de cuenta debe tener una longitud de 13 caracteres");
        }
    }
    private void validateExistsByNameOrNumber(Account account){
        if(repo.existsByNameCustomer(account.getNameCustomer())|| repo.existsByNumberAccount(account.getNumberAccount())){
            throw new ValidationException("No se puede registrar la cuenta porque ya existe uno con estos datos");
        }
    }
}
