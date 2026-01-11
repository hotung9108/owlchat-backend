package com.owl.user_service.domain.service;

import com.owl.user_service.infrastructure.config.UserProfileServicesConfig;
import com.owl.user_service.persistence.jpa.entity.Account;
import com.owl.user_service.persistence.jpa.entity.UserProfile;
import com.owl.user_service.presentation.dto.request.UserProfileRequest;

public class UserProfileServices {
    public boolean ValidateName(String name) {
        if (name == null || name.isBlank()) {
            return false;
        }

        return true;
    }

    public boolean ValidateEmail(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }

        if (!UserProfileServicesConfig.EMAIL_PATTERN.matcher(email).matches()) {
            return false;
        }

        return true;
    }

    public boolean ValidatePhoneNumber(String phone) {
        if (phone == null || phone.isBlank()) {
            return false;
        }

        if (!UserProfileServicesConfig.PHONE_PATTERN.matcher(phone).matches()) {
            return false;
        }

        return true;
    }

    public UserProfile CreateNewUserProfile(Account account, UserProfileRequest userProfileRequest) {
        if (!ValidateName(userProfileRequest.getName())) {
            throw new IllegalArgumentException("Invalid name");
        }

        if (!ValidateEmail(userProfileRequest.getEmail())) {
            throw new IllegalArgumentException("Invalid email");
        }

        if (!ValidatePhoneNumber(userProfileRequest.getPhoneNumber())) {
            throw new IllegalArgumentException("Invalid phone number");
        }

        UserProfile newUserProfile = new UserProfile(
            account,
            userProfileRequest.getName(),
            userProfileRequest.getGender(),
            userProfileRequest.getDateOfBirth(),
            null,
            userProfileRequest.getEmail(),
            userProfileRequest.getPhoneNumber()
        );

        return newUserProfile;
    }
}
