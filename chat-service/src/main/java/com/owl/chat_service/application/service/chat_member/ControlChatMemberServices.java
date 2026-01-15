package com.owl.chat_service.application.service.chat_member;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owl.chat_service.application.service.chat.GetChatServices;
import com.owl.chat_service.domain.chat.validate.ChatMemberValidate;
import com.owl.chat_service.persistence.mongodb.document.Chat;
import com.owl.chat_service.persistence.mongodb.document.ChatMember;
import com.owl.chat_service.persistence.mongodb.document.ChatMember.ChatMemberRole;
import com.owl.chat_service.persistence.mongodb.repository.ChatMemberRepository;
import com.owl.chat_service.presentation.dto.ChatMemberCreateRequest;

@Service
@Transactional
public class ControlChatMemberServices {
    private final GetChatMemberServices getChatMemberServices;
    private final GetChatServices getChatServices;
    private final ChatMemberRepository chatMemberRepository;

    public ControlChatMemberServices(GetChatMemberServices getChatMemberServices, GetChatServices getChatServices, ChatMemberRepository chatMemberRepository) {
        this.getChatMemberServices = getChatMemberServices;
        this.getChatServices = getChatServices;
        this.chatMemberRepository = chatMemberRepository;
    }

    public ChatMember addChatMember(String requesterId, ChatMemberCreateRequest chatMemberCreateRequest) {
        Optional<Chat> chat = getChatServices.getChatById(null, chatMemberCreateRequest.chatId);

        if (!chat.isPresent() || !chat.get().getStatus()) {
            throw new IllegalArgumentException("Chat cannot be found");
        }

        if (!ChatMemberValidate.validateRole(chatMemberCreateRequest.role)) 
            throw new IllegalArgumentException("Role is invalid");

        if (requesterId != null && !requesterId.isEmpty()) {
            getChatMemberServices.validateChatMemberExistence(requesterId, chatMemberCreateRequest.chatId);
        }

        ChatMember newChatMember = new ChatMember();
        newChatMember.setId(UUID.randomUUID().toString());
        newChatMember.setMemberId(chatMemberCreateRequest.memberId);
        newChatMember.setChatId(chatMemberCreateRequest.chatId);
        newChatMember.setRole(ChatMemberRole.valueOf(chatMemberCreateRequest.role));
        newChatMember.setInviterId(requesterId);
        newChatMember.setJoinDate(Instant.now());

        return chatMemberRepository.save(newChatMember);
    }

    public ChatMember updateChatMemberRole(String requesterId, String memberId, String chatId, String role) {
        Optional<Chat> chat = getChatServices.getChatById(null, chatId);

        if (!chat.isPresent() || !chat.get().getStatus()) {
            throw new IllegalArgumentException("Chat cannot be found");
        }

        getChatMemberServices.validateChatMemberExistence(memberId, chatId);

        if (!ChatMemberValidate.validateRole(role)) 
            throw new IllegalArgumentException("Role is invalid");

        if (requesterId != null && !requesterId.isEmpty()) {
            getChatMemberServices.validateChatMemberExistence(requesterId, chatId);
        }

        Optional<ChatMember> requesterMember = getChatMemberServices.getChatMemberByMemberIdAndChatId(null, requesterId, chatId);

        Optional<ChatMember> updateChatMember = getChatMemberServices.getChatMemberByMemberIdAndChatId(null, memberId, chatId);

        if (requesterMember.get().getRole() == ChatMemberRole.OWNER) {
            ChatMemberRole r = ChatMemberRole.valueOf(role);

            if (r == ChatMemberRole.OWNER) {
                requesterMember.get().setRole(ChatMemberRole.MEMBER);
                chatMemberRepository.save(requesterMember.get());
            }

            updateChatMember.get().setRole(r);
        }
        else if (requesterMember.get().getRole() == ChatMemberRole.ADMIN) {
            ChatMemberRole r = ChatMemberRole.valueOf(role);

            if (r == ChatMemberRole.OWNER) {
                throw new SecurityException("Requester do not have permission to update member role to " + role);
            }

            updateChatMember.get().setRole(r);
        }
        else {
            throw new SecurityException("Requester do not have permission to update member role to " + role);
        }
        
        return chatMemberRepository.save(updateChatMember.get());
    }

    public ChatMember updateChatMemberNickname(String requesterId, String memberId, String chatId, String nickname) {
        Optional<Chat> chat = getChatServices.getChatById(null, chatId);

        if (!chat.isPresent() || !chat.get().getStatus()) {
            throw new IllegalArgumentException("Chat cannot be found");
        }

        getChatMemberServices.validateChatMemberExistence(memberId, chatId);

        if (nickname == null || nickname.isBlank()) 
            throw new IllegalArgumentException("Nickname is invalid");

        if (requesterId != null && !requesterId.isEmpty()) {
            getChatMemberServices.validateChatMemberExistence(requesterId, chatId);
        }

        Optional<ChatMember> updateChatMember = getChatMemberServices.getChatMemberByMemberIdAndChatId(null, memberId, chatId);
        updateChatMember.get().setNickname(nickname);
        
        return chatMemberRepository.save(updateChatMember.get());
    }

    public void deleteChatMember(String requesterId, String memberId, String chatId) {
        Optional<Chat> chat = getChatServices.getChatById(null, chatId);

        if (!chat.isPresent() || !chat.get().getStatus()) {
            throw new IllegalArgumentException("Chat cannot be found");
        }

        getChatMemberServices.validateChatMemberExistence(memberId, chatId);

        if (requesterId != null && !requesterId.isEmpty()) {
            getChatMemberServices.validateChatMemberExistence(requesterId, chatId);
        }

        Optional<ChatMember> requesterMember = getChatMemberServices.getChatMemberByMemberIdAndChatId(null, requesterId, chatId);

        Optional<ChatMember> deleteChatMember = getChatMemberServices.getChatMemberByMemberIdAndChatId(null, memberId, chatId);

        ChatMemberRole role = deleteChatMember.get().getRole();

        if (requesterMember.get().getRole() == ChatMemberRole.OWNER) {
            
        }
        else if (requesterMember.get().getRole() == ChatMemberRole.ADMIN) {

            if (role == ChatMemberRole.OWNER) {
                throw new SecurityException("Requester do not have permission to delete member role to " + role);
            }
        }
        else {
            throw new SecurityException("Requester do not have permission to delete member role to " + role);
        }
        
        chatMemberRepository.delete(deleteChatMember.get());
    }
}
