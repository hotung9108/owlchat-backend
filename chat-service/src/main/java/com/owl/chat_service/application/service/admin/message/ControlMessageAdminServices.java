package com.owl.chat_service.application.service.admin.message;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owl.chat_service.application.service.admin.chat.GetChatAdminServices;
import com.owl.chat_service.domain.chat.validate.MessageValidate;
import com.owl.chat_service.persistence.mongodb.document.Message;
import com.owl.chat_service.persistence.mongodb.document.Message.MessageState;
import com.owl.chat_service.persistence.mongodb.document.Message.MessageType;
import com.owl.chat_service.persistence.mongodb.repository.MessageRepository;
import com.owl.chat_service.presentation.dto.TextMessageUserRequest;

@Service
@Transactional
public class ControlMessageAdminServices {
    private final MessageRepository messageRepository;
    private final GetChatAdminServices getChatAdminServices;
    private final GetMessageAdminServices getMessageAdminServices;

    public ControlMessageAdminServices(MessageRepository messageRepository, GetChatAdminServices getChatAdminServices, GetMessageAdminServices getMessageAdminServices) {
        this.messageRepository = messageRepository;
        this.getChatAdminServices = getChatAdminServices;
        this.getMessageAdminServices = getMessageAdminServices;
    }

    public Message addNewTextMessage(String senderId, TextMessageUserRequest textMessageRequest) {
        if (!MessageValidate.validateSenderId(senderId)) {
            throw new IllegalArgumentException("Invalid sender id");
        }

        if (!MessageValidate.validateChatId(textMessageRequest.chatId)) {
            throw new IllegalArgumentException("Invalid chat id");
        }

        if (getChatAdminServices.getChatById(textMessageRequest.chatId) == null) {
            throw new IllegalArgumentException("Chat does not exists");
        }

        if (!MessageValidate.validateContent(textMessageRequest.content)) {
            throw new IllegalArgumentException("Invalid content");
        }
        
        Message newMessage = new Message();
        newMessage.setId(UUID.randomUUID().toString());
        newMessage.setChatId(textMessageRequest.chatId);
        newMessage.setStatus(true);
        newMessage.setState(MessageState.ORIGIN);
        newMessage.setType(MessageType.TEXT);
        newMessage.setContent(textMessageRequest.content);
        newMessage.setSenderId(senderId);
        newMessage.setSentDate(Instant.now());
        newMessage.setCreatedDate(Instant.now());

        return messageRepository.save(newMessage);
    }

    public Message editTextMessage(String messageId, String content) {
        Message existingMessage = getMessageAdminServices.getMessageById(messageId);

        if (existingMessage == null) {
            throw new IllegalArgumentException("Message not found");
        }

        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Content cannot be null");
        }

        existingMessage.setState(MessageState.EDITED);
        messageRepository.save(existingMessage);

        Message newMessage = new Message();
        newMessage.setId(UUID.randomUUID().toString());
        newMessage.setChatId(existingMessage.getChatId());
        newMessage.setStatus(true);
        newMessage.setState(MessageState.ORIGIN);
        newMessage.setType(MessageType.TEXT);
        newMessage.setContent(content);
        newMessage.setSenderId(existingMessage.getSenderId());
        newMessage.setPredecessorId(existingMessage.getId());
        newMessage.setSentDate(existingMessage.getSentDate());
        newMessage.setCreatedDate(Instant.now());

        return messageRepository.save(newMessage);
    }

    public Message activateMessage(String messageId) {
        Message existingMessage = getMessageAdminServices.getMessageById(messageId);

        if (existingMessage == null) {
            throw new IllegalArgumentException("Message not found");
        }

        existingMessage.setStatus(true);
        existingMessage.setState(MessageState.ORIGIN);
        existingMessage.setRemovedDate(null);
        

        return messageRepository.save(existingMessage);
    }

    public void softDeleteMessage(String messageId) {
        Message existingMessage = getMessageAdminServices.getMessageById(messageId);

        if (existingMessage == null) {
            throw new IllegalArgumentException("Message not found");
        }

        switch (existingMessage.getState()) {
            case MessageState.EDITED:
                throw new IllegalArgumentException("Cannot remove edited message");
            case MessageState.REMOVED:
                throw new IllegalArgumentException("Message already removed");
            default:
                break;
        }

        existingMessage.setStatus(false);
        existingMessage.setState(MessageState.REMOVED);
        existingMessage.setRemovedDate(Instant.now());
        
        messageRepository.save(existingMessage);
    }

    public void hardDeleteMessage(String messageId) {
        Message existingMessage = getMessageAdminServices.getMessageById(messageId);

        if (existingMessage == null) {
            throw new IllegalArgumentException("Message not found");
        }

        messageRepository.deleteById(Objects.requireNonNull(messageId, "Message id cannot be null"));
    }
}
