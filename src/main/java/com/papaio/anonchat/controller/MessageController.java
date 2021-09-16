package com.papaio.anonchat.controller;

import com.papaio.anonchat.components.SimpMessagingComponent;
import com.papaio.anonchat.models.Message;
import com.papaio.anonchat.models.ServerMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.security.Principal;

import static com.papaio.anonchat.models.MessageTypes.COMMON;
import static com.papaio.anonchat.models.MessageTypes.PRIVATE;

@Controller
public class MessageController {

    @Value("${com.papaio.anonchat.system-name}")
    private String systemName;
    private final SimpMessagingComponent simpComponent;

    @Autowired
    public MessageController(SimpMessagingComponent simpComponent) {
        this.simpComponent = simpComponent;
    }

    @MessageMapping("/chat/{room}")
    public void handleUserMessages(@DestinationVariable String room, @Payload Message message, Principal user) {
        String fromUser = user.getName();
        ServerMessage convertedMessage = new ServerMessage(fromUser, COMMON, message.getBody());
        if (message.getToUser() != null) {
            convertedMessage.setType(PRIVATE);
            convertedMessage.setToUser(message.getToUser());
        }

        simpComponent.sendMessage(convertedMessage, "/queue/room/" + room);
    }
}