package com.owl.chat_service.presentation.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owl.chat_service.presentation.dto.MessageCreateRequest;
import com.owl.chat_service.presentation.dto.MessageEditRequest;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/message")
public class MessageController {
    public MessageController() {}

    @GetMapping("")
    public ResponseEntity<?> getMessages (
        @RequestParam(required = false, defaultValue = "") String keywords, 
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size,
        @RequestParam(required = false, defaultValue = "true") boolean ascSort,
        @RequestParam(required = false, defaultValue = "0") int status,
        @RequestParam(required = false, defaultValue = "") String type,
        @RequestParam(required = false, defaultValue = "") LocalDateTime sentDateStart,
        @RequestParam(required = false, defaultValue = "") LocalDateTime sentDateEnd,
        @RequestParam(required = false, defaultValue = "") LocalDateTime removedDateStart,
        @RequestParam(required = false, defaultValue = "") LocalDateTime removedDateEnd,
        @RequestParam(required = false, defaultValue = "") LocalDateTime createdDateStart,
        @RequestParam(required = false, defaultValue = "") LocalDateTime createdDateEnd
    ) 
    {
        try {
            return ResponseEntity.ok().body(null /* List<Message> */);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<?> getMessagesByChatId (
        @RequestParam(required = false, defaultValue = "") String requesterId,
        @PathVariable String chatId,
        @RequestParam(required = false, defaultValue = "") String keywords, 
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size,
        @RequestParam(required = false, defaultValue = "true") boolean ascSort,
        @RequestParam(required = false, defaultValue = "0") int status,
        @RequestParam(required = false, defaultValue = "") String type,
        @RequestParam(required = false, defaultValue = "") LocalDateTime sentDateStart,
        @RequestParam(required = false, defaultValue = "") LocalDateTime sentDateEnd,
        @RequestParam(required = false, defaultValue = "") LocalDateTime removedDateStart,
        @RequestParam(required = false, defaultValue = "") LocalDateTime removedDateEnd,
        @RequestParam(required = false, defaultValue = "") LocalDateTime createdDateStart,
        @RequestParam(required = false, defaultValue = "") LocalDateTime createdDateEnd
    ) 
    {
        try {
            return ResponseEntity.ok().body(null /* List<Message> */);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMessageById (
        @RequestParam(required = false, defaultValue = "") String requesterId,
        @PathVariable String id
    ) 
    {
        try {
            return ResponseEntity.ok().body(null /* Message */);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> addMessage(
        @RequestParam(required = false, defaultValue = "") String requesterId,
        @RequestBody MessageCreateRequest messageCreateRequest
    ) 
    {
        try {
            return ResponseEntity.ok().body(null /* Message */);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> editMessage(
        @RequestParam(required = false, defaultValue = "") String requesterId,
        @PathVariable String id,
        @RequestBody MessageEditRequest messageEditRequest
    ) 
    {
        try {
            return ResponseEntity.ok().body(null /* Message */);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // @PutMapping("/{id}")
    // public String updateMessage(@PathVariable String id, @RequestBody String entity) {
    //     //TODO: process PUT request
        
    //     return entity;
    // }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateMessageStatus(
        @RequestParam(required = false, defaultValue = "") String requesterId,
        @PathVariable String id,
        @RequestBody boolean status
    )
    {
        try {
            return ResponseEntity.ok().body(null /* Message */);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/remove/{id}")
    public ResponseEntity<?> removeMessage(
        @RequestParam(required = false, defaultValue = "") String requesterId,
        @PathVariable String id
    )
    {
        try {
            return ResponseEntity.ok().body(updateMessageStatus(requesterId, id, false));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMessage(
        @PathVariable String id
    )
    {
        try {
            return ResponseEntity.ok().body("Chat message deleted successfully");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
