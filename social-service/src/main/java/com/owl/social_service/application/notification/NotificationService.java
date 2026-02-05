package com.owl.social_service.application.notification;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.owl.social_service.application.notification.dto.NotificationDto;
import com.owl.social_service.application.notification.dto.NotificationDto.NotificationAction;
import com.owl.social_service.application.notification.dto.NotificationDto.NotificationType;
import com.owl.social_service.persistence.mongodb.document.Block;
import com.owl.social_service.persistence.mongodb.document.FriendRequest;
import com.owl.social_service.persistence.mongodb.document.Friendship;

@Service
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendFriendshipToUser(String userId, NotificationAction action, Friendship data) {
        messagingTemplate.convertAndSendToUser(userId, "/queue/friendship", new NotificationDto<Friendship>(NotificationType.FRIENDSHIP, action, data));
    }

    public void sendFriendRequestToUser(String userId, NotificationAction action, FriendRequest data) {
        messagingTemplate.convertAndSendToUser(userId, "/queue/friend-request", new NotificationDto<FriendRequest>(NotificationType.FRIEND_REQUEST, action, data));
    }

    public void sendBlockToUser(String userId, NotificationAction action, Block data) {
        messagingTemplate.convertAndSendToUser(userId, "/queue/block", new NotificationDto<Block>(NotificationType.BLOCK, action, data));
    }
}
