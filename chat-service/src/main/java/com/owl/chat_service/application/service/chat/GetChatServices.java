package com.owl.chat_service.application.service.chat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.owl.chat_service.application.service.chat_member.GetChatMemberServices;
import com.owl.chat_service.persistence.mongodb.criteria.ChatCriteria;
import com.owl.chat_service.persistence.mongodb.criteria.PagintaionCriteria;
import com.owl.chat_service.persistence.mongodb.document.Chat;
import com.owl.chat_service.persistence.mongodb.repository.ChatRepository;
import com.owl.chat_service.persistence.mongodb.repository.ChatWithCriteriaRepository;
import com.owl.chat_service.presentation.dto.ChatAvatarData;

@Service
public class GetChatServices {
    private final ChatRepository chatRepository;
    private final ChatWithCriteriaRepository chatWithCriteriaRepository;
    private final GetChatMemberServices getChatMemberServices;

    public GetChatServices(ChatRepository chatRepository, ChatWithCriteriaRepository chatWithCriteriaRepository, GetChatMemberServices getChatMemberServices) {
        this.chatRepository = chatRepository;
        this.chatWithCriteriaRepository = chatWithCriteriaRepository;
        this.getChatMemberServices = getChatMemberServices;
    }

    public List<Chat> getChats(
        String keywords, 
        int page,
        int size,
        boolean ascSort,
        int status,
        String type,
        String initiatorId,
        LocalDateTime createdDateStart,
        LocalDateTime createdDateEnd
    ) 
    {
        Criteria criteria = ChatCriteria.FindAllChatsWithCriteria(keywords, Integer.valueOf(status), type, initiatorId, createdDateStart, createdDateEnd);

        if (page != -1) {
            return chatWithCriteriaRepository.findAll(criteria, Sort.by(ascSort ? Sort.Direction.ASC : Sort.Direction.DESC));
        }

        Pageable pageable = PagintaionCriteria.PagableCriteria(page, size, ascSort);

        return chatWithCriteriaRepository.findAll(criteria, pageable);
    }

    public List<Chat> getAllChats(
        int page,
        int size,
        boolean ascSort
    ) 
    {
        if (page != -1) {
            return chatWithCriteriaRepository.findAll(Sort.by(ascSort ? Sort.Direction.ASC : Sort.Direction.DESC));
        }

        Pageable pageable = PagintaionCriteria.PagableCriteria(page, size, ascSort);

        return chatWithCriteriaRepository.findAll(pageable);
    }

    public Optional<Chat> getChatById(
        String requesterId,
        String id
    ) 
    {
        if (requesterId != null && !requesterId.isEmpty()) {
            getChatMemberServices.validateChatMemberExistence(requesterId, id);
        }

        return chatRepository.findById(id == null ? "" : id);
    }

    public List<Chat> getChatsByMemberId(
        String requesterId,
        String memberId,
        String keywords, 
        int page,
        int size,
        boolean ascSort,
        int status,
        String type,
        String initiatorId,
        LocalDateTime createdDateStart,
        LocalDateTime createdDateEnd
    ) 
    {
        if (requesterId != null && !requesterId.isEmpty()) {
            if (requesterId != memberId)
                throw new SecurityException("Requester does not have permission to access this data");
        }

        List<String> chatIds = new ArrayList<>();

        getChatMemberServices.getChatMembersByMemberId(null, memberId).forEach(e -> {chatIds.add(e.getChatId());});

        Criteria criteria = ChatCriteria.FindChatsInIdsListWithCriteria(chatIds, keywords, Integer.valueOf(status), type, initiatorId, createdDateStart, createdDateEnd);

        if (page != -1) {
            return chatWithCriteriaRepository.findAll(criteria, Sort.by(ascSort ? Sort.Direction.ASC : Sort.Direction.DESC));
        }

        Pageable pageable = PagintaionCriteria.PagableCriteria(page, size, ascSort);

        return chatWithCriteriaRepository.findAll(criteria, pageable);
    }

    public List<Chat> getChatsByMemberId(
        String requesterId,
        String memberId,
        String keywords, 
        int page,
        int size,
        boolean ascSort
    ) 
    {
        if (requesterId != null && !requesterId.isEmpty()) {
            if (requesterId != memberId)
                throw new SecurityException("Requester does not have permission to access this data");
        }

        List<String> chatIds = new ArrayList<>();

        getChatMemberServices.getChatMembersByMemberId(null, memberId).forEach(e -> {chatIds.add(e.getChatId());});

        Criteria criteria = ChatCriteria.FindChatsInIdsListWithCriteria(chatIds, keywords, null, null, null, null, null);

        if (page != -1) {
            return chatWithCriteriaRepository.findAll(criteria, Sort.by(ascSort ? Sort.Direction.ASC : Sort.Direction.DESC));
        }

        Pageable pageable = PagintaionCriteria.PagableCriteria(page, size, ascSort);

        return chatWithCriteriaRepository.findAll(criteria, pageable);
    }

    public ChatAvatarData getChatAvatar(String id) {
        return null;
    }
}
