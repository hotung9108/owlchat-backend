package com.owl.chat_service.presentation.dto;

import java.util.List;

public class ChatCreateRequest {
    public String type;
    public String name;
    public List<ChatMemberCreateRequest> chatMembers;
}
