package com.owl.chat_service.config.decorater;

import java.util.Optional;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

/// Source - https://stackoverflow.com/a/49689153
// Posted by sebge2
// Retrieved 2026-02-04, License - CC BY-SA 3.0

/**
 * Extension of the {@link WebSocketHandlerDecorator websocket handler
 * decorator} that allows to manually test the
 * STOMP protocol.
 *
 * @author Sebastien Gerard
 */
public class EmaWebSocketHandlerDecorator extends WebSocketHandlerDecorator {

    private static final Logger logger = LoggerFactory.getLogger(EmaWebSocketHandlerDecorator.class);
    private static final Pattern LEADING_JUNK = Pattern.compile("^[\\x00-\\x1F\\x7F0\\s]+");

    public EmaWebSocketHandlerDecorator(WebSocketHandler webSocketHandler) {
        super(webSocketHandler);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // super.handleMessage(session, updateBodyIfNeeded(message));
        super.handleMessage(session, cleanupIfNeeded(message));
    }

    private WebSocketMessage<?> cleanupIfNeeded(WebSocketMessage<?> message) {
        if (!(message instanceof TextMessage)) {
            return message;
        }

        String originalPayload = ((TextMessage) message).getPayload();
        if (originalPayload.isEmpty()) {
            return message;
        }

        // Bước 1: Loại bỏ leading junk (rất quan trọng cho trường hợp Postman prepend
        // '0')
        String payload = LEADING_JUNK.matcher(originalPayload).replaceFirst("");

        // Bước 2: Trim thừa (an toàn)
        payload = payload.trim();

        if (payload.isEmpty()) {
            logger.warn("Payload trở thành rỗng sau khi clean leading junk → từ chối");
            return message; // hoặc throw để close session nếu muốn strict
        }

        // Log để debug (có thể comment sau khi test xong)
        if (!payload.equals(originalPayload)) {
            logger.info("Cleaned leading junk from payload. Original length: {}, After: {}",
                    originalPayload.length(), payload.length());
            logger.debug("Original payload start: [{}]",
                    originalPayload.substring(0, Math.min(20, originalPayload.length())));
            logger.debug("Cleaned payload start: [{}]", payload.substring(0, Math.min(20, payload.length())));
        }

        Optional<StompCommand> commandOpt = getStompCommand(payload);

        if (commandOpt.isEmpty()) {
            logger.warn("Không parse được STOMP command sau clean → có thể vẫn là frame lỗi");
            return message;
        }

        StompCommand command = commandOpt.get();

        // Bước 3: Fix frame thiếu \n\n cho command không có body (CONNECT, SUBSCRIBE,
        // ...)
        if (!command.isBodyAllowed() && !payload.contains("\n\n")) {
            // Nếu kết thúc bằng \n thì thêm một \n nữa, còn không thì thêm \n\n
            if (payload.endsWith("\n")) {
                payload += "\n";
            } else {
                payload += "\n\n";
            }
        }

        // Bước 4: Đảm bảo kết thúc bằng NULL byte (\0) — STOMP yêu cầu
        if (!payload.endsWith("\0")) {
            payload += "\0";
        }

        return new TextMessage(payload);
    }

    /**
     * Updates the content of the specified message. The message is updated only if
     * it is
     * a {@link TextMessage text message} and if does not contain the <tt>null</tt>
     * character at the end. If
     * carriage returns are missing (when the command does not need a body) there
     * are also added.
     */
    private WebSocketMessage<?> updateBodyIfNeeded(WebSocketMessage<?> message) {
        if (!(message instanceof TextMessage) || ((TextMessage) message).getPayload().endsWith("\u0000")) {
            return message;
        }

        String payload = ((TextMessage) message).getPayload();

        final Optional<StompCommand> stompCommand = getStompCommand(payload);

        if (!stompCommand.isPresent()) {
            return message;
        }

        if (!stompCommand.get().isBodyAllowed() && !payload.endsWith("\n\n")) {
            if (payload.endsWith("\n")) {
                payload += "\n";
            } else {
                payload += "\n\n";
            }
        }

        payload += "\u0000";

        return new TextMessage(payload);
    }

    /**
     * Returns the {@link StompCommand STOMP command} associated to the specified
     * payload.
     */
    // private Optional<StompCommand> getStompCommand(String payload) {
    // final int firstCarriageReturn = payload.indexOf('\n');

    // if (firstCarriageReturn < 0) {
    // return Optional.empty();
    // }

    // try {
    // return Optional.of(
    // StompCommand.valueOf(payload.substring(0, firstCarriageReturn)));
    // } catch (IllegalArgumentException e) {
    // logger.trace("Error while parsing STOMP command.", e);

    // return Optional.empty();
    // }
    // }
    private Optional<StompCommand> getStompCommand(String payload) {
        int firstNewline = payload.indexOf('\n');
        if (firstNewline <= 0) {
            return Optional.empty();
        }

        String commandStr = payload.substring(0, firstNewline).trim();

        try {
            return Optional.of(StompCommand.valueOf(commandStr));
        } catch (IllegalArgumentException e) {
            logger.trace("Không phải command STOMP hợp lệ: {}", commandStr, e);
            return Optional.empty();
        }
    }
}
