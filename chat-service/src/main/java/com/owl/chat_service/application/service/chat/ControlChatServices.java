package com.owl.chat_service.application.service.chat;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.owl.chat_service.application.service.chat_member.GetChatMemberServices;
import com.owl.chat_service.persistence.mongodb.document.Chat;
import com.owl.chat_service.presentation.dto.ChatCreateRequest;

@Service
public class ControlChatServices {
    private final GetChatServices getChatServices;
    private final GetChatMemberServices getChatMemberServices;

    public ControlChatServices(GetChatServices getChatServices, GetChatMemberServices getChatMemberServices) {
        this.getChatServices = getChatServices;
        this.getChatMemberServices = getChatMemberServices;
    }

    public Chat addChat(String requesterId, ChatCreateRequest chatCreateRequest) {
        // Validate requester existence

        // Validate type

        // Validate name

        

        return null;
    }

    public Chat updateChatNameById(String requesterId, String id, String name) {
        return null;
    }

    public Chat updateChatStatusById(String requesterId, String id, boolean status) {
        return null;
    }

    public void updateChatAvatarById(String requesterId, String id, MultipartFile avatarFile) {

    }

    public void deleteChat(String id) {

    }
}
