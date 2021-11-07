package com.papaio.anonchat.controller;

import com.papaio.anonchat.component.LookupTableComponent;
import com.papaio.anonchat.component.SimpMessagingComponent;
import com.papaio.anonchat.model.Message;
import com.papaio.anonchat.model.ServerMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Stream;

import static com.papaio.anonchat.model.MessageTypes.*;

@Controller
public class MessageController {

    @Value("${com.papaio.anonchat.system-name}")
    private String systemName;
    @Value("${com.papaio.anonchat.app-prefix}")
    private String appPrefix;
    @Value("${com.papaio.anonchat.queue-prefix}")
    private String queuePrefix;
    @Value("${com.papaio.anonchat.endpoint}")
    private String endpoint;
    private final SimpMessagingComponent simpMessagingComponent;
    private final LookupTableComponent lookupTableComponent;

    @Autowired
    public MessageController(SimpMessagingComponent simpComponent, LookupTableComponent lookupTableComponent) {
        this.simpMessagingComponent = simpComponent;
        this.lookupTableComponent = lookupTableComponent;
    }

    @MessageMapping("/chat/{room}")
    public void handleUserMessages(@DestinationVariable String room, @Payload Message message, Principal user) {
        String fromUser = user.getName();
        ServerMessage convertedMessage = new ServerMessage(fromUser, COMMON, message.getBody());
        if (message.getToUser() != null) {
            convertedMessage.setType(PRIVATE);
            convertedMessage.setToUser(message.getToUser());
        }

        simpMessagingComponent.sendMessage(convertedMessage, appPrefix + queuePrefix + "/" + room);
    }

    @SubscribeMapping("/queue/room/{room}")
    public void initialReply(@DestinationVariable String room, Principal user) {
        String userName = user.getName();
        String destination = appPrefix + queuePrefix + "/" + room;

        lookupTableComponent.add(userName);
        Set<String> channels = lookupTableComponent.get(userName);
        channels.add(destination);

        ServerMessage notificationMessage = new ServerMessage(systemName, CONNECTED, userName);
        Stream<String> channelUsers = lookupTableComponent.getUsersExceptUser(userName, destination);
        simpMessagingComponent.sendMessageForListed(notificationMessage, destination, channelUsers);

        ServerMessage infoMessage = new ServerMessage(systemName, userName, WELCOME, "Users in channel: " + lookupTableComponent.countExceptUser(userName, destination));
        simpMessagingComponent.sendMessage(infoMessage, destination);
    }
}