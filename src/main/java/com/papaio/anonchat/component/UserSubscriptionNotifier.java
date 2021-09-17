package com.papaio.anonchat.component;

import com.papaio.anonchat.model.ServerMessage;
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
import java.util.stream.Stream;

import static com.papaio.anonchat.model.MessageTypes.NOTIFICATION;

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

            if (!simpDestination.startsWith(userPrefix)) {

                ServerMessage notificationMessage = new ServerMessage(systemName, NOTIFICATION, userName + " connected");
                Stream<String> channelUsers = destinationLookupTable.keySet().stream()
                        .filter((user) -> !user.equals(userName) && destinationLookupTable.get(user).contains(simpDestination));
                simpMessagingComponent.sendMessageForListed(notificationMessage, simpDestination, channelUsers);

                long usersCount = destinationLookupTable.keySet()
                        .stream()
                        .filter((user) -> !user.equals(userName) && destinationLookupTable.get(user).contains(simpDestination))
                        .count();

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