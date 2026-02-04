package com.owl.chat_service.presentation.dto.notification;

public class NotificationDto<T> {
    public enum NotificationType {
        CHAT,
        CHAT_MEMBER,
        MESSAGE
    }

    private NotificationType type;

    public enum NotificationAction {
        CREATED,
        UPDATED,
        DELETED
    }

    private NotificationAction action;
    private T data;

    public NotificationDto(NotificationType type, NotificationAction action, T data) {
        this.type = type;
        this.action = action;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public NotificationAction getAction() {
        return action;
    }

    public void setAction(NotificationAction action) {
        this.action = action;
    }

}
