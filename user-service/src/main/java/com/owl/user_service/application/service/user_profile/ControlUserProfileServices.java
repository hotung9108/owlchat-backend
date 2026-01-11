package com.owl.user_service.application.service.user_profile;

import org.springframework.stereotype.Service;

import com.owl.user_service.application.service.account.ControlAccountServices;
import com.owl.user_service.domain.service.UserProfileServices;
import com.owl.user_service.persistence.jpa.entity.Account;
import com.owl.user_service.persistence.jpa.entity.UserProfile;
import com.owl.user_service.persistence.jpa.repository.UserProfileJpaRepository;
import com.owl.user_service.presentation.dto.request.UserProfileCreateRequest;
import com.owl.user_service.presentation.dto.request.UserProfileRequest;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ControlUserProfileServices {
    private final UserProfileJpaRepository userProfileJpaRepository;
    private final ControlAccountServices controlAccountServices;
    private final UserProfileServices userProfileServices;

    public ControlUserProfileServices(UserProfileJpaRepository userProfileJpaRepository, ControlAccountServices controlAccountServices) {
        this.userProfileJpaRepository = userProfileJpaRepository;
        this.controlAccountServices = controlAccountServices;
        this.userProfileServices = new UserProfileServices();
    }

    public UserProfile addUserProfile(UserProfileCreateRequest userProfileCreateRequest) {
        try 
        {
            Account newAccount = controlAccountServices.addAccount(userProfileCreateRequest.getAccount());

            return userProfileJpaRepository.save(userProfileServices.CreateNewUserProfile(newAccount, userProfileCreateRequest.getUserProfile()));
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public UserProfile updateUserProfile(String id, UserProfileRequest userProfileRequest) {
        UserProfile existingUserProfile = userProfileJpaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User profile not found"));

        if (!userProfileServices.ValidateName(userProfileRequest.getName())) {
            throw new IllegalArgumentException("Invalid name");
        }

        if (!userProfileServices.ValidateEmail(userProfileRequest.getEmail())) {
            throw new IllegalArgumentException("Invalid email");
        }

        if (!userProfileServices.ValidatePhoneNumber(userProfileRequest.getPhoneNumber())) {
            throw new IllegalArgumentException("Invalid phone number");
        }

        UserProfile updatedUserProfile = new UserProfile(
            existingUserProfile.getAccount(),
            userProfileRequest.getName(),
            userProfileRequest.getGender(),
            userProfileRequest.getDateOfBirth(),
            existingUserProfile.getAvatar(),
            userProfileRequest.getEmail(),
            userProfileRequest.getPhoneNumber()
        );

        return userProfileJpaRepository.save(updatedUserProfile);
    }

    public UserProfile updateAvataUserProfile(String id, String avatar) {
        UserProfile existingUserProfile = userProfileJpaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User profile not found"));


        UserProfile updatedUserProfile = new UserProfile(
            existingUserProfile.getAccount(),
            existingUserProfile.getName(),
            existingUserProfile.getGender(),
            existingUserProfile.getDateOfBirth(),
            avatar,
            existingUserProfile.getEmail(),
            existingUserProfile.getPhoneNumber()
        );
        
        return userProfileJpaRepository.save(updatedUserProfile);
    }

    public void deleteUserProfile(String id) {
        if (!userProfileJpaRepository.existsById(id)) {
            throw new IllegalArgumentException("User profile not found");
        }

        userProfileJpaRepository.deleteById(id);

        controlAccountServices.deleteAccount(id);
    }
}
