package com.owl.user_service.application.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.owl.user_service.infrastructure.utils.KeywordUtils;
import com.owl.user_service.persistence.jpa.entity.Account;
import com.owl.user_service.persistence.jpa.repository.AccountJpaRepository;
import com.owl.user_service.persistence.jpa.specification.AccountSpecifications;

@Service
public class GetAccountServices {
    private final AccountJpaRepository accountRepository;

    public GetAccountServices(AccountJpaRepository _accountRepository) 
    {
        this.accountRepository = _accountRepository;
    }

    public List<Account> getAccounts(String keywords, int page, int size) {
        if (page < -1 || size <= 0) {
            throw new IllegalArgumentException("Page must be -1 or greater, and size must be greater than 0");
        }

        List<Account> accounts = new ArrayList<>();

        Pageable pageable = Pageable.ofSize(size).withPage(page);

        if (keywords == null || keywords.isEmpty()) {
            if (page == -1) accounts = accountRepository.findAll();
            else accounts = accountRepository.findAll(pageable).toList();
        }
        else {
            List<String> keywordList = KeywordUtils.parseKeywords(keywords);
            if (page == -1) accounts = accountRepository.findAll(AccountSpecifications.containsKeywords(keywordList));
            else accounts = accountRepository.findAll(AccountSpecifications.containsKeywords(keywordList), pageable).toList();
        }

        return accounts;
    }

    public Account getAccountById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Id must not be null or empty");
        }
        else {
            return accountRepository.findById(id).orElse(null);
        }
    }
}
