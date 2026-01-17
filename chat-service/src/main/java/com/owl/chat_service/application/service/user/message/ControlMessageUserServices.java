package com.owl.chat_service.application.service.user.message;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.owl.chat_service.application.service.admin.message.ControlMessageAdminServices;
import com.owl.chat_service.application.service.user.chat.GetChatUserServices;
import com.owl.chat_service.persistence.mongodb.document.Message;
import com.owl.chat_service.persistence.mongodb.repository.MessageRepository;
import com.owl.chat_service.presentation.dto.TextMessageUserRequest;

@Service
@Transactional
public class ControlMessageUserServices {
    private final GetChatUserServices getChatUserServices;
    private final GetMessageUserServices getMessageUserServices;
    private final ControlMessageAdminServices controlMessageAdminServices;

    public ControlMessageUserServices(GetChatUserServices getChatUserServices, MessageRepository messageRepository, GetMessageUserServices getMessageUserServices, ControlMessageAdminServices controlMessageAdminServices) {
        this.getChatUserServices = getChatUserServices;
        this.getMessageUserServices = getMessageUserServices;
        this.controlMessageAdminServices = controlMessageAdminServices;}

    public Message addNewTextMessage(String requesterId, TextMessageUserRequest textMessageRequest) {
        if (getChatUserServices.getChatById(requesterId, textMessageRequest.chatId) == null)
            throw new IllegalArgumentException("Chat not found");

        return controlMessageAdminServices.addNewTextMessage(requesterId, textMessageRequest);
    }

    public Message editTextMessage(String requesterId, String messageId, String content) {
        Message existingMessage = getMessageUserServices.getMessageById(requesterId, messageId);

        if (existingMessage == null)
            throw new IllegalArgumentException("Message not found");

        if (existingMessage.getSenderId() != requesterId) 
            throw new SecurityException("Requester does not have permission to edit this message");

        return controlMessageAdminServices.editTextMessage(messageId, content);
    }

    public void softDeleteMessage(String requesterId, String messageId) {
        Message existingMessage = getMessageUserServices.getMessageById(requesterId, messageId);

        if (existingMessage == null)
            throw new IllegalArgumentException("Message not found");

        if (existingMessage.getSenderId() != requesterId) 
            throw new SecurityException("Requester does not have permission to edit this message");

        controlMessageAdminServices.softDeleteMessage(messageId);
    }
}
