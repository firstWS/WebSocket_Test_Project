package com.ws.Config;

import com.ws.Entity.ChatMessageEntity;
import com.ws.Entity.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener  // 해당 어노테이션은 Bean으로 등록된 Class의 메서드에서 사용할 수 있습니다.
                    // 해당 어노테이션이 적용되어 있는 메서드의 인수로 현재 SessionConnectEvent/DisconnectEvent가 있습니다.
    public void handleWebSocketConnectListener(SessionConnectedEvent event){
        logger.info("Received a new web socket connection.");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");

        if(username != null){
            logger.info("User Disconnected : " + username);

            ChatMessageEntity chatMessageEntity = new ChatMessageEntity();
            chatMessageEntity.setType(MessageType.LEAVE);
            chatMessageEntity.setSender(username);

            messagingTemplate.convertAndSend("/topic/public", chatMessageEntity);
        }
    }
}
