package com.owl.user_service.persistence.jpa.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "user_profile")
public class UserProfile {

    protected UserProfile() {
    }

    @Id
    @Column(nullable = false)
    private String id;

    @OneToOne(optional = false)
    @MapsId
    @JoinColumn(name = "id")
    private Account account;

    @Column(nullable = false)
    private String name;

    @Column
    private Boolean gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String avatar;

    @Column
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private Instant createdDate;

    @LastModifiedDate
    @Column(name = "updated_date", nullable = true)
    private Instant updatedDate;

    public UserProfile(
            Account account,
            String name,
            Boolean gender,
            LocalDate dateOfBirth,
            String avatar,
            String email,
            String phoneNumber
    ) {
        this.account = account;
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.avatar = avatar;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public String getName() {
        return name;
    }

    public Boolean getGender() {
        return gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}

