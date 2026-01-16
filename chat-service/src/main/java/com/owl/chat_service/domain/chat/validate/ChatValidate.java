package com.owl.chat_service.domain.chat.validate;

import com.owl.chat_service.persistence.mongodb.document.Chat.ChatType;

public class ChatValidate {
    public static boolean ValidateType(String type) {
        try {
            ChatType.valueOf(type);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
