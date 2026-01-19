package com.owl.social_service.persistence.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.owl.social_service.persistence.mongodb.document.FriendRequest;

public interface FriendRequestRepository extends MongoRepository<FriendRequest, String> {
    
} 
