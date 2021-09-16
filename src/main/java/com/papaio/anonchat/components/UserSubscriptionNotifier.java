package com.papaio.anonchat.components;

import com.papaio.anonchat.models.ServerMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

import static com.papaio.anonchat.models.MessageTypes.NOTIFICATION;

@Component
public class UserSubscriptionNotifier {

    @Value("${com.papaio.anonchat.user-prefix}")
    private String userPrefix;
    @Value("${com.papaio.anonchat.system-name}")
    private String systemName;
    private final Map<String, HashSet<String>> destinationLookupTable;
    private final SimpMessagingComponent simpMessagingComponent;

    @Autowired
    public UserSubscriptionNotifier(SimpMessagingComponent simpMessagingComponent) {
        this.simpMessagingComponent = simpMessagingComponent;
        destinationLookupTable = new HashMap<>();
    }

    @EventListener
    public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
        GenericMessage<byte[]> message = (GenericMessage<byte[]>) event.getMessage();
        String simpDestination = (String) message.getHeaders().get("simpDestination");
        String userName = Objects.requireNonNull(event.getUser()).getName();

        if (simpDestination != null) {
            destinationLookupTable.putIfAbsent(userName, new HashSet<>());
            HashSet<String> channels = destinationLookupTable.get(userName);
            channels.add(simpDestination);
            ServerMessage notificationMessage = new ServerMessage(systemName, NOTIFICATION, userName + " connected");
            simpMessagingComponent.sendMessage(notificationMessage, simpDestination);

            if (!simpDestination.startsWith(userPrefix)) {
                int usersCount = 0;
                for (String key : destinationLookupTable.keySet()) {
                    if (destinationLookupTable.get(key).contains(simpDestination)) usersCount++;
                }
                ServerMessage infoMessage = new ServerMessage(systemName, userName, NOTIFICATION, "Users in channel: " + usersCount);
                simpMessagingComponent.sendMessage(infoMessage, simpDestination);
            }
        }
    }

    @EventListener
    public void handleSessionDisconnectEvent(SessionDisconnectEvent event) {
        String userName = Objects.requireNonNull(event.getUser()).getName();
        HashSet<String> channels = destinationLookupTable.get(userName);
        ServerMessage notificationMessage = new ServerMessage(systemName, NOTIFICATION, userName + " disconnected");
        for (String channel : channels) {
            simpMessagingComponent.sendMessage(notificationMessage, channel);
        }
        destinationLookupTable.remove(userName);
    }
}