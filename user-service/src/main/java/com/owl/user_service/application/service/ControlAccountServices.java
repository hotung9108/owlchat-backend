package com.owl.user_service.application.service;

import org.springframework.stereotype.Service;

import com.owl.user_service.domain.service.AccountServices;
import com.owl.user_service.persistence.jpa.entity.Account;
import com.owl.user_service.persistence.jpa.repository.AccountJpaRepository;
import com.owl.user_service.presentation.dto.request.AccountRequest;

@Service
public class ControlAccountServices {
    private final AccountServices accountServices;
    private final AccountJpaRepository accountRepository;

    public ControlAccountServices(AccountJpaRepository _accountRepository) {
        this.accountRepository = _accountRepository;
        accountServices = new AccountServices();
    }

    public Account addAccount(AccountRequest account) {
        try {
            Account lastAccount = accountRepository.findFirstByOrderByIdDesc();
            return accountRepository.save(accountServices.CreateNewAccount(lastAccount != null ? lastAccount.getId() : null, account));
        }
        catch (IllegalArgumentException ex) {
            throw ex;
        }
    }

    public Account updateAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account updateAccountStatus(String id, String status) {
        return null;
    }

    public void deleteAccount(String id) {
        accountRepository.deleteById(id);
    }
}
