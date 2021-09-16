package com.papaio.anonchat.components;

import com.papaio.anonchat.models.ServerMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class SimpMessagingComponent {

    private final SimpMessagingTemplate template;

    public SimpMessagingComponent(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void sendMessage(ServerMessage message, String channel) {
        if (message.getToUser() != null) template.convertAndSendToUser(message.getToUser(), channel, message);
        else template.convertAndSend(channel, message);
    }
}