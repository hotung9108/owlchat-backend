package com.owl.user_service.domain.service;

import com.owl.user_service.infrastructure.config.AccountServicesConfig;
import com.owl.user_service.persistence.jpa.entity.Account;
import com.owl.user_service.presentation.dto.request.AccountRequest;

public class AccountServices {
    private String GenerateNewID(String lastID) {
        // If no last ID, start from 1
        if (lastID == null || lastID.isBlank()) {
            return AccountServicesConfig.PREFIX + String.format("%0" + AccountServicesConfig.NUM_LENGTH + "d", 1);
        }

        // Validate format
        if (!ValidateID(lastID)) {
            throw new IllegalArgumentException("Invalid lastID format: " + lastID);
        }

        // Extract numeric part
        String numericPart = lastID.substring(AccountServicesConfig.PREFIX.length());
        long number = Long.parseLong(numericPart);

        // Increment
        number++;

        // Ensure number does not exceed max length
        if (String.valueOf(number).length() > AccountServicesConfig.NUM_LENGTH) {
            throw new IllegalStateException("ID overflow: cannot generate next ID");
        }

        // Build next ID with leading zeros
        return AccountServicesConfig.PREFIX + String.format("%0" + AccountServicesConfig.NUM_LENGTH + "d", number);
    }

    private boolean ValidateID(String id) {
        if (id == null || id.isBlank()) {
            return false;
        }

        if (!id.startsWith(AccountServicesConfig.PREFIX)) {
            return false;
        }

        String numericPart = id.substring(AccountServicesConfig.PREFIX.length());
        if (numericPart.length() != AccountServicesConfig.NUM_LENGTH) {
            return false;
        }

        if (!id.matches(AccountServicesConfig.PREFIX + "\\d{" + AccountServicesConfig.NUM_LENGTH + "}")) {
            return false;
        }

        try {
            Long.parseLong(numericPart);
        } catch (NumberFormatException ex) {
            return false;
        }

        return true;
    }

    private boolean ValidatePassword(String password) {
        if (password == null || password.isBlank()) {
            return false;
        }

        if (!password.matches(AccountServicesConfig.PASSWORD_REGEX)) {
            return false;
        }
        else {
            return true;
        }
    }

    private boolean ValidateNewAccount(Account account) {
        if (account == null) {
            return false;
        }

        if (account.getId() == null || account.getId().isBlank()) {
            return false;
        }

        if (!ValidateID(account.getId())) {
            return false;
        }

        if (account.getUsername() == null || account.getUsername().isBlank()) {
            return false;
        }

        if (account.getPassword() == null || account.getPassword().isBlank()) {
            return false;
        }

        if (!ValidatePassword(account.getPassword())) {
            return false;
        }

        if (account.getUsername() == account.getPassword()) {
            return false;
        }

        return true;
    }

    public Account CreateNewAccount(String lastID, AccountRequest accountRequest) {
        Account newAccount = new Account(GenerateNewID(lastID), true, accountRequest.getUsername(), accountRequest.getPassword());
        
        if (!ValidateNewAccount(newAccount)) 
            throw new IllegalArgumentException("Invalid account data");

        return newAccount;
    }
}
