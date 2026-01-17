package com.owl.chat_service.application.service.user.chat;

import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.owl.chat_service.application.service.admin.chat.ControlChatAdminServices;
import com.owl.chat_service.application.service.admin.chat.GetChatAdminServices;
import com.owl.chat_service.application.service.admin.chat_member.ControlChatMemberAdminSerivces;
import com.owl.chat_service.application.service.user.chat_member.GetChatMemberUserServices;
import com.owl.chat_service.domain.chat.validate.ChatValidate;
import com.owl.chat_service.persistence.mongodb.document.Chat;
import com.owl.chat_service.persistence.mongodb.repository.ChatRepository;
import com.owl.chat_service.persistence.mongodb.document.ChatMember;
import com.owl.chat_service.presentation.dto.admin.ChatAdminRequest;
import com.owl.chat_service.presentation.dto.admin.ChatMemberAdminRequest;
import com.owl.chat_service.presentation.dto.user.ChatUserRequest;

@Service
@Transactional
public class ControlChatUserServices {
    private final ControlChatAdminServices controlChatAdminServices;
    private final ControlChatMemberAdminSerivces controlChatMemberAdminSerivces;
    private final GetChatMemberUserServices getChatMemberUserServices;
    private final GetChatAdminServices getChatAdminServices;
    private final ChatRepository chatRepository;

    public ControlChatUserServices(ControlChatAdminServices controlChatAdminServices, ControlChatMemberAdminSerivces controlChatMemberAdminSerivces, GetChatMemberUserServices getChatMemberUserServices, GetChatAdminServices getChatAdminServices, ChatRepository chatRepository) {
        this.controlChatAdminServices = controlChatAdminServices;
        this.controlChatMemberAdminSerivces = controlChatMemberAdminSerivces;
        this.getChatMemberUserServices = getChatMemberUserServices;
        this.getChatAdminServices = getChatAdminServices;
        this.chatRepository = chatRepository;
    }

    public Chat addNewChat(String requesterId, ChatUserRequest chatRequest) {
        ChatAdminRequest request = new ChatAdminRequest();
        
        if (!chatRequest.chatMembersId.contains(requesterId)) 
            chatRequest.chatMembersId.add(requesterId);
        
        if (chatRequest.chatMembersId.size() == 0) 
            throw new IllegalArgumentException("Chat member list is empty");
        else if (chatRequest.chatMembersId.size() == 2) 
            request.type = "PRIVATE";
        else 
            if (chatRequest.chatMembersId.size() > 100)
                throw new IllegalArgumentException("Group chat limit is 100");
            else
                request.type = "GROUP";
        
        request.name = chatRequest.name;
        request.initiatorId = requesterId;

        Chat newChat = controlChatAdminServices.addNewChat(request);

        for (String memberId : chatRequest.chatMembersId) {
            ChatMemberAdminRequest chatMemberRequest = new ChatMemberAdminRequest();
            chatMemberRequest.memberId = memberId;
            chatMemberRequest.chatId = newChat.getId();
            if (memberId == requesterId)
                chatMemberRequest.role = "OWNER";
            else 
                chatMemberRequest.role = "MEMBER";
            chatMemberRequest.inviterId = requesterId;
            controlChatMemberAdminSerivces.addNewChatMember(chatMemberRequest);
        }

        return newChat;
    }

    public Chat updateChatName(String requesterId, String chatId, String name) {
        ChatMember chatMember = getChatMemberUserServices.getChatMemberByChatIdAndMemberId(requesterId, chatId);

        if (chatMember == null)
            throw new SecurityException("Requester does not have permission to access this chat");

        if (!ChatValidate.validateName(name)) 
            throw new IllegalArgumentException("Invalid name");

        Chat chat = getChatAdminServices.getChatById(chatId);

        chat.setName(name);

        return chatRepository.save(chat);
    }

    public void deleteChat(String requesterId, String chatId) {
        ChatMember chatMember = getChatMemberUserServices.getChatMemberByChatIdAndMemberId(requesterId, chatId);

        if (chatMember == null)
            throw new SecurityException("Requester does not have permission to access this chat");

        chatRepository.deleteById(Objects.requireNonNull(chatId, "Chat id is null"));
    }
}
