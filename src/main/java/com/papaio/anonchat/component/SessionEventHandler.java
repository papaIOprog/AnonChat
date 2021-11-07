package com.papaio.anonchat.component;

import com.papaio.anonchat.model.ServerMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static com.papaio.anonchat.model.MessageTypes.*;

@Component
public class SessionEventHandler {

    @Value("${com.papaio.anonchat.user-prefix}")
    private String userPrefix;
    @Value("${com.papaio.anonchat.system-name}")
    private String systemName;
    private final SimpMessagingComponent simpMessagingComponent;
    private final LookupTableComponent lookupTableComponent;

    @Autowired
    public SessionEventHandler(SimpMessagingComponent simpMessagingComponent, LookupTableComponent lookupTableComponent) {
        this.simpMessagingComponent = simpMessagingComponent;
        this.lookupTableComponent = lookupTableComponent;
    }

    @EventListener
    public void handleSessionDisconnectEvent(SessionDisconnectEvent event) {
        String userName = Objects.requireNonNull(event.getUser()).getName();
        Set<String> channels = lookupTableComponent.get(userName);
        ServerMessage notificationMessage = new ServerMessage(systemName, DISCONNECTED, userName);
        for (String channel : channels) {
            simpMessagingComponent.sendMessage(notificationMessage, channel);
        }
        lookupTableComponent.remove(userName);
    }
}