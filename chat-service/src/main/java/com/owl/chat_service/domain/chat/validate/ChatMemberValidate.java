package com.owl.chat_service.domain.chat.validate;

import com.owl.chat_service.persistence.mongodb.document.ChatMember.ChatMemberRole;

public class ChatMemberValidate {
    public static void validateRequesterAndMemberAreSame(String requesterId, String memberId) {
        if (requesterId != memberId) {
            throw new SecurityException("Requester does not have permission to access this data");
        }
    }

    public static boolean validateRole(String role) {
        try {
            ChatMemberRole.valueOf(role);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
