package com.owl.user_service.presentation.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.owl.user_service.application.service.ControlAccountServices;
import com.owl.user_service.application.service.GetAccountServices;
import com.owl.user_service.presentation.dto.request.AccountRequest;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final GetAccountServices getAccountServices;
    private final ControlAccountServices controlAccountServices;

    public AccountController(GetAccountServices _getAccountServices, ControlAccountServices _controlAccountServices) {
        this.getAccountServices = _getAccountServices;
        this.controlAccountServices = _controlAccountServices;
    }

    @GetMapping("")
    public ResponseEntity getAccounts(@RequestParam(required = false, defaultValue = "") String keywords, @RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "10") int size, @RequestParam(required =  false, defaultValue = "0") int status) {
        try {
            return ResponseEntity.ok(getAccountServices.getAccounts(keywords, page, size, status));
        }
        catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity getAccountById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(getAccountServices.getAccountById(id));
        }
        catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    
    @PostMapping("")
    public ResponseEntity addAccount(@RequestBody AccountRequest newAccountRequest) {
        try {
            return ResponseEntity.ok(controlAccountServices.addAccount(newAccountRequest));
        }
        catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateAccount(@PathVariable String id, @RequestBody AccountRequest accountRequest) {
        try {
            return ResponseEntity.ok(controlAccountServices.updateAccount(id, accountRequest));
        }
        catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity updateAccountStatus(@PathVariable String id, @PathVariable boolean status) {
        try {
            return ResponseEntity.ok(controlAccountServices.updateAccountStatus(id, status));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable String id) {
        try {
            controlAccountServices.deleteAccount(id);
            return ResponseEntity.ok("Account with id " + id + " has been deleted successfully");
        }
        catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
