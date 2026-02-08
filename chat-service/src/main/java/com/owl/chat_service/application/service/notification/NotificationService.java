package com.owl.chat_service.application.service.notification;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.owl.chat_service.presentation.dto.notification.NotificationDto;

@Service
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // public <T> void send(String userId, NotificationDto<T> event) {
    //     messagingTemplate.convertAndSendToUser(
    //             userId,
    //             "/queue/notifications",
    //             event);
    // }
    public <T> void sendToChat(String chatId, NotificationDto<T> notification){
        messagingTemplate.convertAndSend(
            "/topic/chat." + chatId,
            notification
        );
    }
    public <T> void sendMessageToChat(String chatId, NotificationDto<T> message){
        messagingTemplate.convertAndSend(
            "/topic/chat." + chatId + "/messages",
            message
        );
    }
    // public <T> void broadcast(NotificationDto<T> notification){
    //     messagingTemplate.convertAndSend(
    //         "/topic/broadcast",
    //         notification
    //     );
    // }
}
