package com.ws.Controller;

import com.ws.Entity.ChatMessageEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessageEntity sendMessage(@Payload ChatMessageEntity chatMessageEntity){
        return chatMessageEntity;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")

    public ChatMessageEntity addUser(@Payload ChatMessageEntity chatMessageEntity,
                                     SimpMessageHeaderAccessor headerAccessor){
        headerAccessor.getSessionAttributes().put("username", chatMessageEntity.getSender());
        return chatMessageEntity;
    }
    //  WebSocketConfig에서 "/app"로 시작하는 대상이 있는 클라이언트에섭 보낸 모든 메시지는 @MessageMapping 어노테이션이 달린 메서드로 라우딩됩니다.
    //  "/app/chat.sendMessage" 인 메세지는 sendMessage()로 라우팅되며
    //  "/app/chat.addUser" 인 메세지는 addUser()로 라우팅됩니다.
}
