package com.owl.user_service.presentation.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.owl.user_service.application.service.account.ControlAccountServices;
import com.owl.user_service.application.service.account.GetAccountServices;
import com.owl.user_service.application.service.user_profile.ControlUserProfileServices;
import com.owl.user_service.presentation.dto.request.AccountRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final GetAccountServices getAccountServices;
    private final ControlAccountServices controlAccountServices;
    private final ControlUserProfileServices controlUserProfileServices;

    public AccountController(GetAccountServices _getAccountServices, ControlAccountServices _controlAccountServices, ControlUserProfileServices _controlUserProfileServices) {
        this.getAccountServices = _getAccountServices;
        this.controlAccountServices = _controlAccountServices;
        this.controlUserProfileServices = _controlUserProfileServices;
    }
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("")
    public ResponseEntity<?> getAccounts(
        @RequestParam(required = false, defaultValue = "") String keywords, 
        @RequestParam(required = false, defaultValue = "0") int page, 
        @RequestParam(required = false, defaultValue = "10") int size, 
        @RequestParam(required =  false, defaultValue = "0") int status,
        @RequestParam(required = false, defaultValue = "true") boolean ascSort
    ) 
    {
        try {
            return ResponseEntity.ok(getAccountServices.getAccounts(keywords, page, size, status, ascSort));
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(getAccountServices.getAccountById(id));
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    
    @PostMapping("")
    public ResponseEntity<?> addAccount(@RequestBody AccountRequest newAccountRequest) {
        try {
            return ResponseEntity.ok(controlAccountServices.addAccount(newAccountRequest));
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable String id, @RequestBody AccountRequest accountRequest) {
        try {
            return ResponseEntity.ok(controlAccountServices.updateAccount(id, accountRequest));
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<?> updateAccountStatus(@PathVariable String id, @PathVariable boolean status) {
        try {
            return ResponseEntity.ok(controlAccountServices.updateAccountStatus(id, status));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable String id) {
        try {
            controlUserProfileServices.deleteUserProfile(id);
            controlAccountServices.deleteAccount(id);
            return ResponseEntity.ok("Account and User Profile with id " + id + " has been deleted successfully");
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
