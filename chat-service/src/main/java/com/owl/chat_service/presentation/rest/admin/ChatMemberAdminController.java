package com.owl.chat_service.presentation.rest.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owl.chat_service.presentation.dto.admin.ChatMemberAdminRequest;

import java.time.Instant;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/admin/member")
public class ChatMemberAdminController {
    public ChatMemberAdminController() {}

    // get chat member
        // keywords
        // page
        // size
        // ascSort
        // role
        // joinDateStart
        // joinDateEnd
    @GetMapping("")
    public ResponseEntity<?> getChatMember(
        @RequestParam String keywords,
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam boolean ascSort,
        @RequestParam String role,
        @RequestParam Instant joinDateStart,
        @RequestParam Instant joinDateEnd
    ) 
    {
        try {
            return ResponseEntity.ok().body(null);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // get chat member by chat id and member id
        // chat id
        // member id
    @GetMapping("/{memberId}/chat/{chatId}")
    public ResponseEntity<?> getMethodName(@PathVariable String memberId, @PathVariable String chatId) {
        try {
            return ResponseEntity.ok().body(null);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    

    // post chat member
        // chat member create request
            // chat id
            // member id
            // role
            // nickname
            // inviter id
            // join date
    @PostMapping("")
    public ResponseEntity<?> postChatMember(@RequestBody ChatMemberAdminRequest chatMemberCreateRequest) {
        try {
            return ResponseEntity.ok().body(null);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // put chat member
        // member id
        // chat id
        // chat member update request
            // chat id
            // member id
            // role
            // nickname
            // inviter id
            // join date
    @PutMapping("/{memberId}/chat/{chatId}")
    public ResponseEntity<?> putChatMember(@PathVariable String memberId, @PathVariable String chatId, @RequestBody ChatMemberAdminRequest chatMemberCreateRequest) {
        try {
            return ResponseEntity.ok().body(null);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // patch role
        // member id
        // chat id
        // role
    @PatchMapping("/{memberId}/chat/{chatId}/role")
    public ResponseEntity<?> patchChatMemberRole(@PathVariable String memberId, @PathVariable String chatId, @PathVariable String role) {
        try {
            return ResponseEntity.ok().body(null);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // patch nickname
        // member id
        // chat id
        // nickname
    @PatchMapping("/{memberId}/chat/{chatId}/nickname")
    public ResponseEntity<?> patchChatMemberNickname(@PathVariable String memberId, @PathVariable String chatId, @PathVariable String nickname) {
        try {
            return ResponseEntity.ok().body(null);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // delete chat member
        // member id
        // chat id 
    @DeleteMapping("/{memberId}/chat/{chatId}")
    public ResponseEntity<?> deleteChatMember(@PathVariable String memberId, @PathVariable String chatId) {
        try {
            return ResponseEntity.ok().body(null);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
