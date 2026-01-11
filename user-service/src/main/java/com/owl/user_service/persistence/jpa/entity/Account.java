package com.owl.user_service.persistence.jpa.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.*;

@Entity
@Table(name = "account")
public class Account {

    protected Account() {
    }

    @Id
    @Column(nullable = false)
    private String id;

    @Column
    private Boolean status;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @CreatedDate
    @Column(name = "created_date", nullable = true, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "updated_date", nullable = true)
    private LocalDateTime updatedDate;

    public Account(String id, Boolean status, String username, String password) {
        this.id = id;
        this.status = status;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

