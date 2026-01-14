package com.owl.chat_service.presentation.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owl.chat_service.presentation.dto.ChatMemberCreateRequest;
import com.owl.chat_service.presentation.dto.ChatMemberUpdateRequest;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/member")
public class ChatMemberController {
    public ChatMemberController() {}

    @GetMapping("")
    public ResponseEntity<?> getChatMembers (
        @RequestParam(required = false, defaultValue = "") String keywords, 
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size,
        @RequestParam(required = false, defaultValue = "true") boolean ascSort,
        @RequestParam(required = false, defaultValue = "") LocalDateTime joindDateStart,
        @RequestParam(required = false, defaultValue = "") LocalDateTime joinDateEnd
    )
    {
        try {
            return ResponseEntity.ok().body(null /* List<ChatMember> */);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{memberId}")
    public ResponseEntity<?> getChatMembersByMemberId (
        @RequestParam(required = false, defaultValue = "") String requesterId,
        @PathVariable String memberId,
        @RequestParam(required = false, defaultValue = "") String keywords, 
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size,
        @RequestParam(required = false, defaultValue = "true") boolean ascSort,
        @RequestParam(required = false, defaultValue = "") LocalDateTime joindDateStart,
        @RequestParam(required = false, defaultValue = "") LocalDateTime joinDateEnd
    )
    {
        try {
            return ResponseEntity.ok().body(null /* List<ChatMember> */);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<?> getChatMembersByChatId (
        @RequestParam(required = false, defaultValue = "") String requesterId,
        @PathVariable String chatId,
        @RequestParam(required = false, defaultValue = "") String keywords, 
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size,
        @RequestParam(required = false, defaultValue = "true") boolean ascSort,
        @RequestParam(required = false, defaultValue = "") LocalDateTime joindDateStart,
        @RequestParam(required = false, defaultValue = "") LocalDateTime joinDateEnd
    )
    {
        try {
            return ResponseEntity.ok().body(null /* List<ChatMember> */);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{memberId}/chat/{chatId}")
    public ResponseEntity<?> getChatMemberByChatIdAndMemberId (
        @RequestParam(required = false, defaultValue = "") String requesterId,
        @PathVariable String memberId,
        @PathVariable String chatId
    )
    {
        try {
            return ResponseEntity.ok().body(null /* ChatMember */);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> addChatMember(
        @RequestParam(required = false, defaultValue = "") String requesterId, 
        @RequestBody ChatMemberCreateRequest chatMemberCreateRequest
    ) 
    {
        try {
            return ResponseEntity.ok().body(null /* ChatMember */);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{memberId}/chat/{chatId}")
    public ResponseEntity<?> updateChatMember(
        @RequestParam(required = false, defaultValue = "") String requesterId,
        @PathVariable String memberId,
        @PathVariable String chatId,
        @RequestBody ChatMemberUpdateRequest chatMemberUpdateRequest
    )
    {
        try {
            return ResponseEntity.ok().body(null /* ChatMember */);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{memberId}/chat/{chatId}")
    public ResponseEntity<String> deleteChatMember(
        @RequestParam(required = false, defaultValue = "") String requesterId,
        @PathVariable String memberId,
        @PathVariable String chatId
    )
    {
        try {
            return ResponseEntity.ok().body("Chat member deleted successfully");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
