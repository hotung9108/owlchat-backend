package com.owl.chat_service.presentation.dto.admin;

import java.time.Instant;

public class ChatMemberAdminRequest {
    public String chatId;
    public String memberId;
    public String role;
    public String nickname;
    public String inviterId;
    public Instant joinDate;
}
