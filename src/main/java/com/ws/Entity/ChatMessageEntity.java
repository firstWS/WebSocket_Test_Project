package com.ws.Entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatMessageEntity {
    private MessageType type;
    private String content;
    private String sender;
}
