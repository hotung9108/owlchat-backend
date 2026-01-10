package com.owl.user_service.presentation.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.owl.user_service.application.service.GetAccountServices;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final GetAccountServices getAccountServices;

    public AccountController(GetAccountServices _getAccountServices) {
        this.getAccountServices = _getAccountServices;
    }

    @GetMapping("/account")
    public ResponseEntity getAccounts(@RequestParam String keywords, @RequestParam int page, @RequestParam int size) {
        try {
            return ResponseEntity.ok(getAccountServices.getAccounts(keywords, page, size));
        }
        catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    
    @GetMapping("/account/{id}")
    public ResponseEntity getAccountById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(getAccountServices.getAccountById(id));
        }
        catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    
    @PostMapping("/account")
    public String addAccount(@RequestBody String entity) {
        return entity;
    }

    @PutMapping("account/{id}")
    public String updateAccount(@PathVariable String id, @RequestBody String entity) {
        //TODO: process PUT request
        
        return entity;
    }

    @PatchMapping("account/{id}")
    public String updateAccountStatus(@PathVariable String id, @RequestBody String entity) {
        return new String();
    }

    @DeleteMapping("account/{id}")
    public void deleteAccount(@PathVariable String id) {

    }
}
