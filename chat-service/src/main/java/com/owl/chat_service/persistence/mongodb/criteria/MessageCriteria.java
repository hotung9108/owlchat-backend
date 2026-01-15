package com.owl.chat_service.persistence.mongodb.criteria;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;

import com.owl.chat_service.infrastructure.utils.KeywordUtils;
import com.owl.chat_service.persistence.mongodb.document.Message.MessageType;

public class MessageCriteria {
    public static Criteria FindAllMessagesWithCriteria(
        String keywords, 
        Integer status,
        String type,
        LocalDateTime sentDateStart,
        LocalDateTime sentDateEnd,
        LocalDateTime removedDateStart,
        LocalDateTime removedDateEnd,
        LocalDateTime createdDateStart,
        LocalDateTime createdDateEnd
    )
    {
        List<Criteria> criteriaList = new ArrayList<>();

        // keyword search (name)
        if (keywords != null && !keywords.isBlank() && MessageType.valueOf(type.toUpperCase()).equals(MessageType.TEXT)) {
            List<Criteria> keywordsCriteriaList = new ArrayList<Criteria>();
            for (String keyword : KeywordUtils.parseKeywords(keywords)) {    
                keywordsCriteriaList.add(
                    Criteria.where("content")
                            .regex(keyword, "i")
                );
            }
            criteriaList.add(new Criteria().orOperator(keywordsCriteriaList));
        }

        // status
        if (status != null) {
            if (status == 1) {
                criteriaList.add(
                    Criteria.where("status").is(true)
                );
            }
            else if (status == -1) {
                criteriaList.add(
                    Criteria.where("status").is(false)
                );
            }
        }

        try {
            criteriaList.add(Criteria.where("type").is(MessageType.valueOf(type.toUpperCase())));
        }
        catch (Exception e) {
            
        }

        // created date range
        if (createdDateStart != null || createdDateEnd != null) {
            Criteria dateCriteria = Criteria.where("createdDate");

            if (createdDateStart != null) {
                dateCriteria.gte(createdDateStart.toInstant(ZoneOffset.UTC));
            }
            if (createdDateEnd != null) {
                dateCriteria.lte(createdDateEnd.toInstant(ZoneOffset.UTC));
            }

            criteriaList.add(dateCriteria);
        }

        // sent date range
        if (sentDateStart != null || sentDateEnd != null) {
            Criteria dateCriteria = Criteria.where("sentDate");

            if (sentDateStart != null) {
                dateCriteria.gte(sentDateStart.toInstant(ZoneOffset.UTC));
            }
            if (sentDateEnd != null) {
                dateCriteria.lte(sentDateEnd.toInstant(ZoneOffset.UTC));
            }

            criteriaList.add(dateCriteria);
        }

        // removed date range
        if (removedDateStart != null || removedDateEnd != null) {
            Criteria dateCriteria = Criteria.where("removedDate");

            if (removedDateStart != null) {
                dateCriteria.gte(removedDateStart.toInstant(ZoneOffset.UTC));
            }
            if (removedDateEnd != null) {
                dateCriteria.lte(removedDateEnd.toInstant(ZoneOffset.UTC));
            }

            criteriaList.add(dateCriteria);
        }

        return criteriaList.isEmpty() ? null : new Criteria().andOperator(criteriaList);
    }

    public static Criteria FindMessagesByChatIdWithCriteria(
        String chatId,
        String keywords, 
        Integer status,
        String type,
        LocalDateTime sentDateStart,
        LocalDateTime sentDateEnd,
        LocalDateTime removedDateStart,
        LocalDateTime removedDateEnd,
        LocalDateTime createdDateStart,
        LocalDateTime createdDateEnd
    )
    {
        List<Criteria> criteriaList = new ArrayList<>();

        //chat id
        if (chatId != null && !chatId.isEmpty()) {
            criteriaList.add(Criteria.where("chatId").regex(chatId, "i"));
        }

        // keyword search (name)
        if (keywords != null && !keywords.isBlank() && MessageType.valueOf(type.toUpperCase()).equals(MessageType.TEXT)) {
            List<Criteria> keywordsCriteriaList = new ArrayList<Criteria>();
            for (String keyword : KeywordUtils.parseKeywords(keywords)) {    
                keywordsCriteriaList.add(
                    Criteria.where("content")
                            .regex(keyword, "i")
                );
            }
            criteriaList.add(new Criteria().orOperator(keywordsCriteriaList));
        }

        // status
        if (status != null) {
            if (status == 1) {
                criteriaList.add(
                    Criteria.where("status").is(true)
                );
            }
            else if (status == -1) {
                criteriaList.add(
                    Criteria.where("status").is(false)
                );
            }
        }

        try {
            criteriaList.add(Criteria.where("type").is(MessageType.valueOf(type.toUpperCase())));
        }
        catch (Exception e) {
            
        }

        // created date range
        if (createdDateStart != null || createdDateEnd != null) {
            Criteria dateCriteria = Criteria.where("createdDate");

            if (createdDateStart != null) {
                dateCriteria.gte(createdDateStart.toInstant(ZoneOffset.UTC));
            }
            if (createdDateEnd != null) {
                dateCriteria.lte(createdDateEnd.toInstant(ZoneOffset.UTC));
            }

            criteriaList.add(dateCriteria);
        }

        // sent date range
        if (sentDateStart != null || sentDateEnd != null) {
            Criteria dateCriteria = Criteria.where("sentDate");

            if (sentDateStart != null) {
                dateCriteria.gte(sentDateStart.toInstant(ZoneOffset.UTC));
            }
            if (sentDateEnd != null) {
                dateCriteria.lte(sentDateEnd.toInstant(ZoneOffset.UTC));
            }

            criteriaList.add(dateCriteria);
        }

        // removed date range
        if (removedDateStart != null || removedDateEnd != null) {
            Criteria dateCriteria = Criteria.where("removedDate");

            if (removedDateStart != null) {
                dateCriteria.gte(removedDateStart.toInstant(ZoneOffset.UTC));
            }
            if (removedDateEnd != null) {
                dateCriteria.lte(removedDateEnd.toInstant(ZoneOffset.UTC));
            }

            criteriaList.add(dateCriteria);
        }

        return criteriaList.isEmpty() ? null : new Criteria().andOperator(criteriaList);
    }
}
