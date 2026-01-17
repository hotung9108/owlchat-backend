package com.owl.chat_service.domain.chat.validate;

import com.owl.chat_service.persistence.mongodb.document.Chat.ChatType;

public class ChatValidate {
    public static boolean validateType(String type) {
        if (type == null || type.isBlank()) {
            return false;
        }

        try {
            ChatType.valueOf(type.trim().toUpperCase());
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    public static boolean validateName(String name) {
        if (name == null || name.isBlank())
            return false;

        return true;
    }

    public static boolean validateChatId(String chatId) {
        if (chatId == null || chatId.isBlank())
            return false;

        return true;
    }
}
