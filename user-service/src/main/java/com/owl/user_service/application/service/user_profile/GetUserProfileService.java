package com.owl.user_service.application.service.user_profile;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.owl.user_service.domain.service.AccountServices;
import com.owl.user_service.infrastructure.utils.KeywordUtils;
import com.owl.user_service.persistence.jpa.entity.UserProfile;
import com.owl.user_service.persistence.jpa.repository.UserProfileJpaRepository;
import com.owl.user_service.persistence.jpa.specification.UserProfileSpecification;

@Service
public class GetUserProfileService {
    private final UserProfileJpaRepository userProfileRepository;
    private final AccountServices accountServices;

    public GetUserProfileService(UserProfileJpaRepository _userProfileRepository) {
        this.userProfileRepository = _userProfileRepository;
        accountServices = new AccountServices();
    }

    public List<UserProfile> getUserProfiles(String keywords, int page, int size, int gender, LocalDateTime dateOfBirthStart, LocalDateTime dateOfBirthEnd, boolean ascSort) {
        if (page < -1 || size <= 0) {
            throw new IllegalArgumentException(
                "Page must be -1 or greater, and size must be greater than 0"
            );
        }
        
        // no pagination
        if (page == -1) {
            if (keywords == null || keywords.isBlank()) {
                return userProfileRepository.findAll();
            }

            List<String> keywordList = KeywordUtils.parseKeywords(keywords);
            return userProfileRepository.findAll(UserProfileSpecification.findUserProfileSpecification(keywordList, gender, dateOfBirthStart, dateOfBirthEnd, ascSort));
        }

        // pagination
        Pageable pageable = PageRequest.of(page, size);

        if (keywords == null || keywords.isBlank()) {
            return userProfileRepository.findAll(pageable).getContent();
        }

        List<String> keywordList = KeywordUtils.parseKeywords(keywords);
        return userProfileRepository.findAll(UserProfileSpecification.findUserProfileSpecification(keywordList, gender, dateOfBirthStart, dateOfBirthEnd, ascSort), pageable).getContent();
    }

    public UserProfile getUserProfileById(String id) {
        if (accountServices.ValidateID(id) == false) {
            throw new IllegalArgumentException("Id is invalid: " + id);
        }   

        return userProfileRepository.findById(id).orElseThrow(() -> 
            new IllegalArgumentException("UserProfile with id " + id + " does not exist")
        );
    }
}
