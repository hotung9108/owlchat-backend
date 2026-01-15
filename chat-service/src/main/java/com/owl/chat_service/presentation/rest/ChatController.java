package com.owl.chat_service.presentation.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.owl.chat_service.application.service.chat.ControlChatServices;
import com.owl.chat_service.application.service.chat.GetChatServices;
import com.owl.chat_service.presentation.dto.ChatAvatarData;
import com.owl.chat_service.presentation.dto.ChatCreateRequest;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final GetChatServices getChatServices;
    private final ControlChatServices controlChatServices;

    public ChatController(GetChatServices getChatServices, ControlChatServices controlChatServices) {
        this.getChatServices = getChatServices;
        this.controlChatServices = controlChatServices;
    }

    @GetMapping("")
    public ResponseEntity<?> getChats(
        @RequestParam(required = false, defaultValue = "") String keywords, 
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size,
        @RequestParam(required = false, defaultValue = "true") boolean ascSort,
        @RequestParam(required = false, defaultValue = "0") int status,
        @RequestParam(required = false, defaultValue = "") String type,
        @RequestParam(required = false, defaultValue = "") String initiatorId,
        @RequestParam(required = false, defaultValue = "") LocalDateTime createdDateStart,
        @RequestParam(required = false, defaultValue = "") LocalDateTime createdDateEnd
    ) 
    {
        try {
            return ResponseEntity.ok().body(getChatServices.getChats(keywords, page, size, ascSort, status, type, initiatorId, createdDateStart, createdDateEnd));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?>  getChatById(@RequestParam(required = false, defaultValue = "") String requesterId, @PathVariable String id) {
        try {
            return ResponseEntity.ok().body(getChatServices.getChatById(requesterId, id));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/member/{memberId}")
    public ResponseEntity<?> getChatsByMemberId(
        @RequestParam(required = false, defaultValue = "") String requesterId,
        @PathVariable String memberId,
        @RequestParam(required = false, defaultValue = "") String keywords, 
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size,
        @RequestParam(required = false, defaultValue = "true") boolean ascSort,
        @RequestParam(required = false, defaultValue = "0") int status,
        @RequestParam(required = false, defaultValue = "") String type,
        @RequestParam(required = false, defaultValue = "") String initiatorId,
        @RequestParam(required = false, defaultValue = "") LocalDateTime createdDateStart,
        @RequestParam(required = false, defaultValue = "") LocalDateTime createdDateEnd
    ) 
    {
        try {
            return ResponseEntity.ok().body(getChatServices.getChatsByMemberId(requesterId, memberId, keywords, page, size, ascSort, status, type, initiatorId, createdDateStart, createdDateEnd));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("")
    public ResponseEntity<?> addChat(@RequestParam(required = false, defaultValue = "") String requesterId, @RequestBody ChatCreateRequest chatCreateRequest) {
        try {
            return ResponseEntity.ok().body(controlChatServices.addChat(requesterId, chatCreateRequest));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PatchMapping("/{id}/name")
    public ResponseEntity<?> updateChatNameById(@RequestParam(required = false, defaultValue = "") String requesterId, @PathVariable String id, @RequestBody String name) {
        try {
            return ResponseEntity.ok().body(controlChatServices.updateChatNameById(requesterId, id, name));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateChatStatusById(@RequestParam(required = false, defaultValue = "") String requesterId, @PathVariable String id, @RequestBody boolean status) {
        try {
            return ResponseEntity.ok().body(controlChatServices.updateChatStatusById(requesterId, id, status));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/{id}/avatar/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateChatAvatarById(@RequestParam(required = false, defaultValue = "") String requesterId, @PathVariable String id, @RequestPart("file") MultipartFile avatarFile) {
        try {
            controlChatServices.updateChatAvatarById(requesterId, id, avatarFile);
            return ResponseEntity.ok("Upload avatar successfully");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxSize(MaxUploadSizeExceededException e) {
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File size exceeds 10MB limit");
    }

    @GetMapping("/{id}/avatar")
    public ResponseEntity<?> getChatAvatar(@PathVariable String id) {
        try {
            ChatAvatarData data = getChatServices.getChatAvatar(id);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(data.contentType /* avatar mediaType */))
                    .body(data.resource /* avatar resource */);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChat(@PathVariable String id) {
        try {
            controlChatServices.deleteChat(id);
            return ResponseEntity.ok("Chat deleted successfully");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
