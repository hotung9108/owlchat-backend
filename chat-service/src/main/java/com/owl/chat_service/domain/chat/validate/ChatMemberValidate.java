package com.owl.chat_service.domain.chat.validate;

public class ChatMemberValidate {
    public static void validateRequesterAndMemberAreSame(String requesterId, String memberId) {
        if (requesterId != memberId) {
            throw new SecurityException("Requester does not have permission to access this data");
        }
    }
}
