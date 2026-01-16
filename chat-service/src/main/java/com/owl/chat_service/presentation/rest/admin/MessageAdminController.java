package com.owl.chat_service.presentation.rest.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.owl.chat_service.presentation.dto.ChatAvatarData;
import com.owl.chat_service.presentation.dto.FileMessageUserRequest;
import com.owl.chat_service.presentation.dto.TextMessageUserRequest;

import java.time.Instant;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/admin/message")
public class MessageAdminController {
    public MessageAdminController() {}
    
    // get messages
        // keywords
        // page
        // size
        // ascSort
        // status
        // state
        // type
        // sentDateStart
        // sentDateEnd
        // removedDateStart
        // removedDateEnd
        // createdDateStart
        // createdDateEnd
    @GetMapping("")
    public ResponseEntity<?> getMessages(
        @RequestParam(required = false, defaultValue = "") String keywords,
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size,
        @RequestParam(required = false, defaultValue = "true") boolean ascSort,
        @RequestParam(required = false, defaultValue = "0") int status,
        @RequestParam(required = false, defaultValue = "ALL") String state,
        @RequestParam(required = false, defaultValue = "") String type,
        @RequestParam(required = false) Instant sentDateStart,
        @RequestParam(required = false) Instant sentDateEnd,
        @RequestParam(required = false) Instant removedDateStart,
        @RequestParam(required = false) Instant removedDateEnd,
        @RequestParam(required = false) Instant createdDateStart,
        @RequestParam(required = false) Instant createdDateEnd
    ) 
    {
        try {
            return ResponseEntity.ok().body(null);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // get messages by chat id
        // chatId
        // keywords
        // page
        // size
        // ascSort
        // status
        // state
        // type
        // sentDateStart
        // sentDateEnd
        // removedDateStart
        // removedDateEnd
        // createdDateStart
        // createdDateEnd
    @GetMapping("/chat/{chatId}")
    public ResponseEntity<?> getMessagesByChatId(
        @PathVariable String chatId,
        @RequestParam(required = false, defaultValue = "") String keywords,
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size,
        @RequestParam(required = false, defaultValue = "true") boolean ascSort,
        @RequestParam(required = false, defaultValue = "0") int status,
        @RequestParam(required = false, defaultValue = "ALL") String state,
        @RequestParam(required = false, defaultValue = "") String type,
        @RequestParam(required = false) Instant sentDateStart,
        @RequestParam(required = false) Instant sentDateEnd,
        @RequestParam(required = false) Instant removedDateStart,
        @RequestParam(required = false) Instant removedDateEnd,
        @RequestParam(required = false) Instant createdDateStart,
        @RequestParam(required = false) Instant createdDateEnd
    ) 
    {
        try {
            return ResponseEntity.ok().body(null);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // get message by id
        // messageId
    @GetMapping("/{messageId}")
    public ResponseEntity<?> getMessageById(@PathVariable String messageId) {
        try {
            return ResponseEntity.ok().body(null);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // get message resource by id
        // messageId
    @GetMapping("/{messageId}/resource")
    public ResponseEntity<?> getMessageFile(@PathVariable String messageId) {
        try {
            ChatAvatarData data = new ChatAvatarData();

            String mediaType = data.contentType /* avatar mediaType */;
            
            Objects.requireNonNull(mediaType, "mediaType must not be null");

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mediaType))
                    .body(data.resource /* avatar resource */);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // post new text message
        // message create request
            // chat id
            // content
            // senderId
    @PostMapping("")
    public ResponseEntity<?> postNewTextMessage(@RequestBody TextMessageUserRequest textMessageRequest) {
        try {
            return ResponseEntity.ok().body(null);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // post new file message
        // chat id
        // type
        // file
    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> postNewFileMessage(@RequestBody FileMessageUserRequest fileMessageRequest) {
        try {
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

    // put edit text message
        // message id
        // content
    @PutMapping("/{messageId}/edit")
    public ResponseEntity<?> putTextMessage(@PathVariable String messageId, @RequestBody String content) {
        try {
            return ResponseEntity.ok("Upload avatar successfully");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // soft delete message
        // message id
    @DeleteMapping("/{messageId}/remove")
    public ResponseEntity<?> softDeleteMessage(@PathVariable String messageId) {
        try {
            return ResponseEntity.ok().body(null);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // hard delete message
        // message id
    @DeleteMapping("/{messageId}")
    public ResponseEntity<?> hardDeleteMessage(@PathVariable String messageId) {
        try {
            return ResponseEntity.ok().body(null);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
