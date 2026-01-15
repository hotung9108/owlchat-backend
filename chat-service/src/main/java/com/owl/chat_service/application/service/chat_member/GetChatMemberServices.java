package com.owl.chat_service.application.service.chat_member;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.owl.chat_service.domain.chat.validate.ChatMemberValidate;
import com.owl.chat_service.persistence.mongodb.criteria.ChatMemberCriteria;
import com.owl.chat_service.persistence.mongodb.criteria.PagintaionCriteria;
import com.owl.chat_service.persistence.mongodb.document.ChatMember;
import com.owl.chat_service.persistence.mongodb.repository.ChatMemberRepository;
import com.owl.chat_service.persistence.mongodb.repository.ChatMemberWithCriteriaRepository;

@Service
public class GetChatMemberServices {
    private final ChatMemberRepository chatMemberRepository;
    private final ChatMemberWithCriteriaRepository chatMemberWithCriteriaRepository;

    public GetChatMemberServices(ChatMemberRepository chatMemberRepository, ChatMemberWithCriteriaRepository chatMemberWithCriteriaRepository) {
        this.chatMemberRepository = chatMemberRepository;
        this.chatMemberWithCriteriaRepository = chatMemberWithCriteriaRepository;
    }

    public Optional<ChatMember> getChatMemberByMemberIdAndChatId(String requesterId, String memberId, String chatId) {
        if (requesterId != null && !requesterId.isEmpty()) {
            validateChatMemberExistence(memberId, chatId);
        }

        return chatMemberRepository.findByChatIdAndMemberId(chatId, memberId);
    }

    public void validateChatMemberExistence(String memberId, String chatId) {
        if (memberId == null || memberId.isEmpty())
            throw new SecurityException("Member ID cannot be null");

        if (chatId == null || chatId.isEmpty())
            throw new SecurityException("Chat ID cannot be null");

        if (!getChatMemberByMemberIdAndChatId(null, memberId, chatId).isPresent()) {
            throw new SecurityException("Chat member of chat is not found");
        }
    }

    public List<ChatMember> getChatMembersByMemberId(
        String requesterId, 
        String memberId,
        String keywords, 
        int page,
        int size,
        boolean ascSort,
        LocalDateTime joindDateStart,
        LocalDateTime joinDateEnd
    ) 
    {
        ChatMemberValidate.validateRequesterAndMemberAreSame(requesterId, memberId);

        Criteria criteria = ChatMemberCriteria.FindChatMembersByMemberIdWithCriteria(memberId, keywords, joindDateStart, joinDateEnd);

        if (page == -1) {
            return chatMemberWithCriteriaRepository.findAll(criteria, Sort.by(ascSort ? Sort.Direction.ASC : Sort.Direction.DESC));
        }

        Pageable pageable = PagintaionCriteria.PagableCriteria(page, size, ascSort);

        return chatMemberWithCriteriaRepository.findAll(criteria, pageable);
    }

    public List<ChatMember> getChatMembersByMemberId(String requesterId, String memberId) 
    {
        ChatMemberValidate.validateRequesterAndMemberAreSame(requesterId, memberId);

        return chatMemberRepository.findByMemberId(memberId);
    }

    public List<ChatMember> getChatMembersByChatId(
        String requesterId, 
        String chatId,
        String keywords, 
        int page,
        int size,
        boolean ascSort,
        LocalDateTime joindDateStart,
        LocalDateTime joinDateEnd
    ) 
    {
        validateChatMemberExistence(requesterId, chatId);

        Criteria criteria = ChatMemberCriteria.FindChatMembersByChatIdWithCriteria(chatId, keywords, joindDateStart, joinDateEnd);

        if (page == -1) {
            return chatMemberWithCriteriaRepository.findAll(criteria, Sort.by(ascSort ? Sort.Direction.ASC : Sort.Direction.DESC));
        }

        Pageable pageable = PagintaionCriteria.PagableCriteria(page, size, ascSort);

        return chatMemberWithCriteriaRepository.findAll(criteria, pageable);
    }

    public List<ChatMember> getChatMembersByChatId(String requesterId, String chatId) {
        validateChatMemberExistence(requesterId, chatId);

        return chatMemberRepository.findByChatId(chatId);
    }

    public List<ChatMember> getChatMembers(
        String keywords, 
        int page,
        int size,
        boolean ascSort,
        LocalDateTime joindDateStart,
        LocalDateTime joinDateEnd
    ) 
    {
        Criteria criteria = ChatMemberCriteria.FindAllChatMembersWithCriteria(keywords, joindDateStart, joinDateEnd);

        if (page == -1) {
            return chatMemberWithCriteriaRepository.findAll(criteria, Sort.by(ascSort ? Sort.Direction.ASC : Sort.Direction.DESC));
        }

        Pageable pageable = PagintaionCriteria.PagableCriteria(page, size, ascSort);

        return chatMemberWithCriteriaRepository.findAll(criteria, pageable);
    }
}
