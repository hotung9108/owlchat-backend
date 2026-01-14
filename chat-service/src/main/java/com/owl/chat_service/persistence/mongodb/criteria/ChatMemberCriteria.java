package com.owl.chat_service.persistence.mongodb.criteria;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;

import com.owl.chat_service.infrastructure.utils.KeywordUtils;

public class ChatMemberCriteria {
    public static Criteria FindAllChatMembersWithCriteria(
        String keywords, 
        LocalDateTime joindDateStart,
        LocalDateTime joinDateEnd
    ) 
    {
        List<Criteria> criteriaList = new ArrayList<>();

        // keyword search (name)
        if (keywords != null && !keywords.isBlank()) {
            List<Criteria> keywordsCriteriaList = new ArrayList<Criteria>();
            for (String keyword : KeywordUtils.parseKeywords(keywords)) {    
                keywordsCriteriaList.add(
                    Criteria.where("nickname")
                            .regex(keyword, "i")
                );
            }
            criteriaList.add(new Criteria().orOperator(keywordsCriteriaList));
        }

        // created date range
        if (joindDateStart != null || joinDateEnd != null) {
            Criteria dateCriteria = Criteria.where("joinDate");

            if (joindDateStart != null) {
                dateCriteria.gte(joindDateStart.toInstant(ZoneOffset.UTC));
            }
            if (joinDateEnd != null) {
                dateCriteria.lte(joinDateEnd.toInstant(ZoneOffset.UTC));
            }

            criteriaList.add(dateCriteria);
        }

        return criteriaList.isEmpty() ? null : new Criteria().andOperator(criteriaList);
    }  
    
    public static Criteria FindChatMembersByMemberIdWithCriteria(
        String memberId,
        String keywords, 
        LocalDateTime joindDateStart,
        LocalDateTime joinDateEnd
    ) 
    {
        List<Criteria> criteriaList = new ArrayList<>();

        // member id 
        if (memberId != null && !memberId.isEmpty()) {
            criteriaList.add(Criteria.where("memberId").regex(memberId, "i"));
        }

        // keyword search (name)
        if (keywords != null && !keywords.isBlank()) {
            List<Criteria> keywordsCriteriaList = new ArrayList<Criteria>();
            for (String keyword : KeywordUtils.parseKeywords(keywords)) {    
                keywordsCriteriaList.add(
                    Criteria.where("nickname")
                            .regex(keyword, "i")
                );
            }
            criteriaList.add(new Criteria().orOperator(keywordsCriteriaList));
        }

        // created date range
        if (joindDateStart != null || joinDateEnd != null) {
            Criteria dateCriteria = Criteria.where("joinDate");

            if (joindDateStart != null) {
                dateCriteria.gte(joindDateStart.toInstant(ZoneOffset.UTC));
            }
            if (joinDateEnd != null) {
                dateCriteria.lte(joinDateEnd.toInstant(ZoneOffset.UTC));
            }

            criteriaList.add(dateCriteria);
        }

        return criteriaList.isEmpty() ? null : new Criteria().andOperator(criteriaList);
    }  

    public static Criteria FindChatMembersByChatIdWithCriteria(
        String chatId,
        String keywords, 
        LocalDateTime joindDateStart,
        LocalDateTime joinDateEnd
    ) 
    {
        List<Criteria> criteriaList = new ArrayList<>();

        // member id 
        if (chatId != null && !chatId.isEmpty()) {
            criteriaList.add(Criteria.where("chatId").regex(chatId, "i"));
        }

        // keyword search (name)
        if (keywords != null && !keywords.isBlank()) {
            List<Criteria> keywordsCriteriaList = new ArrayList<Criteria>();
            for (String keyword : KeywordUtils.parseKeywords(keywords)) {    
                keywordsCriteriaList.add(
                    Criteria.where("nickname")
                            .regex(keyword, "i")
                );
            }
            criteriaList.add(new Criteria().orOperator(keywordsCriteriaList));
        }

        // created date range
        if (joindDateStart != null || joinDateEnd != null) {
            Criteria dateCriteria = Criteria.where("joinDate");

            if (joindDateStart != null) {
                dateCriteria.gte(joindDateStart.toInstant(ZoneOffset.UTC));
            }
            if (joinDateEnd != null) {
                dateCriteria.lte(joinDateEnd.toInstant(ZoneOffset.UTC));
            }

            criteriaList.add(dateCriteria);
        }

        return criteriaList.isEmpty() ? null : new Criteria().andOperator(criteriaList);
    }  
}
