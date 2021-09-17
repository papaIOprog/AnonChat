package com.papaio.anonchat.component;

import com.papaio.anonchat.model.ServerMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

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

    public void sendMessageForListed(ServerMessage message, String channel, Stream<String> users) {
        users.forEach((user) -> {
            message.setToUser(user);
            sendMessage(message, channel);
        });
    }
}
