package com.owl.user_service.persistence.jpa.specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.owl.user_service.persistence.jpa.entity.UserProfile;

import jakarta.persistence.criteria.Predicate;

public class UserProfileSpecification {
    public static Specification<UserProfile> findUserProfileSpecification(java.util.List<String> keywords, int gender,  LocalDateTime dateOfBirthStart, LocalDateTime dateOfBirthEnd, boolean ascSort) {
        return (root, query, cb) -> {
            query.distinct(true); // remove duplicates

            List<Predicate> predicates = new ArrayList<>();

            if (keywords == null || keywords.isEmpty()) {
                
            }
            else {

                for (String kw : keywords) {
                    String pattern = "%" + kw.toLowerCase() + "%";
                    predicates.add(cb.like(cb.lower(root.get("name")), pattern));
                    predicates.add(cb.like(cb.lower(root.get("email")), pattern));
                    predicates.add(cb.like(cb.lower(root.get("phoneNumber")), pattern));
                }
            }

            if (gender == 1) {
                predicates.add(cb.isTrue(root.get("gender")));
            }
            else if (gender == 2) {
                predicates.add(cb.isFalse(root.get("gender")));
            }

            if (dateOfBirthStart != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dateOfBirth"), dateOfBirthStart.toLocalDate()));
            }
            
            if (dateOfBirthEnd != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dateOfBirth"), dateOfBirthEnd.toLocalDate()));
            }

            if (ascSort) {
                query.orderBy(cb.asc(root.get("id")));
            }
            else {
                query.orderBy(cb.desc(root.get("id")));
            }

            // combine all predicates with OR
            return cb.or(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };
    }
}
