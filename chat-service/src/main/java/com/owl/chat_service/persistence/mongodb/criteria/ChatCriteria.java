package com.owl.chat_service.persistence.mongodb.criteria;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;

import com.owl.chat_service.infrastructure.utils.KeywordUtils;
import com.owl.chat_service.persistence.mongodb.document.Chat.ChatType;

public class ChatCriteria {

    public static Criteria FindAllChatsWithCriteria(
        String keywords,
        Integer status,
        String type,
        String initiatorId,
        LocalDateTime createdDateStart,
        LocalDateTime createdDateEnd
    ) 
    {
        List<Criteria> criteriaList = new ArrayList<>();

        // keyword search (name)
        if (keywords != null && !keywords.isBlank()) {
            List<Criteria> keywordsCriteriaList = new ArrayList<Criteria>();
            for (String keyword : KeywordUtils.parseKeywords(keywords)) {    
                keywordsCriteriaList.add(
                    Criteria.where("name")
                            .regex(keyword, "i")
                );
            }
            criteriaList.add(new Criteria().orOperator(keywordsCriteriaList));
        }

        // status
        if (status != null) {
            criteriaList.add(
                Criteria.where("status").is(status == 1)
            );
        }

        // type
        if (type != null && !type.isBlank()) {
            criteriaList.add(
                Criteria.where("type").is(ChatType.valueOf(type))
            );
        }

        // initiator
        if (initiatorId != null && !initiatorId.isBlank()) {
            criteriaList.add(
                Criteria.where("initiatorId").is(initiatorId)
            );
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

        return criteriaList.isEmpty() ? null : new Criteria().andOperator(criteriaList);
    }

    public static Criteria FindChatsInIdsListWithCriteria(
        List<String> idsList,
        String keywords,
        Integer status,
        String type,
        String initiatorId,
        LocalDateTime createdDateStart,
        LocalDateTime createdDateEnd
    ) 
    {
        List<Criteria> criteriaList = new ArrayList<>();

        // id in list
        if (!idsList.isEmpty())
            criteriaList.add(Criteria.where("_id").in(idsList));

        // keyword search (name)
        if (keywords != null && !keywords.isBlank()) {
            List<Criteria> keywordsCriteriaList = new ArrayList<Criteria>();
            for (String keyword : KeywordUtils.parseKeywords(keywords)) {    
                keywordsCriteriaList.add(
                    Criteria.where("name")
                            .regex(keyword, "i")
                );
            }
            criteriaList.add(new Criteria().orOperator(keywordsCriteriaList));
        }

        // status
        if (status != null) {
            criteriaList.add(
                Criteria.where("status").is(status == 1)
            );
        }

        // type
        if (type != null && !type.isBlank()) {
            criteriaList.add(
                Criteria.where("type").is(ChatType.valueOf(type))
            );
        }

        // initiator
        if (initiatorId != null && !initiatorId.isBlank()) {
            criteriaList.add(
                Criteria.where("initiatorId").is(initiatorId)
            );
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

        return criteriaList.isEmpty() ? null : new Criteria().andOperator(criteriaList);
    }
}
