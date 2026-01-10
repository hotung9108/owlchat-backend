package com.owl.user_service.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.owl.user_service.persistence.jpa.entity.Account;

public interface AccountRepository extends JpaRepository<Account, String> 
{
    // save(account)
    // findById(id)
    // findAll()
    // deleteById(id)
    // existsById(id)
    // count()

    //findByUsername
    public Account findByUsername(String username);

    //updateStatusById
    @Modifying
    @Query("UPDATE Account a SET a.status = :status WHERE a.id = :id") 
    public void updateStatusById(String id, Boolean status);

    //updateUpdatedDateById
    @Modifying
    @Query("UPDATE Account a SET a.updatedDate = CURRENT_TIMESTAMP WHERE a.id = :id")
    public void updateUpdatedDateById(String id);
}
