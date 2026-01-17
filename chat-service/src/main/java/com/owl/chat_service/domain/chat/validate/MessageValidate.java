package com.owl.chat_service.domain.chat.validate;

import com.owl.chat_service.persistence.mongodb.document.Message.MessageState;
import com.owl.chat_service.persistence.mongodb.document.Message.MessageType;

public class MessageValidate {
    public static boolean ValidateType(String type) {
        try {
            MessageType.valueOf(type);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static boolean ValidateState(String state) {
        try {
            MessageState.valueOf(state);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public static boolean validateSenderId(String senderId) {
        if (senderId == null || senderId.isBlank()) {
            return false;
        }
        return true;
    }

    public static boolean validateChatId(String chatId) {
        if (chatId == null || chatId.isBlank()) {
            return false;
        }
        return true;
    }

    public static boolean validateContent(String content) {
        if (content == null || content.isBlank()) {
            return false;
        }
        return true;
    }
}
