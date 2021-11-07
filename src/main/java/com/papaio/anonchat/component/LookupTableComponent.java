package com.papaio.anonchat.component;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Stream;

@Component
public class LookupTableComponent {
    private final Map<String, ConcurrentSkipListSet<String>> destinationLookupTable;

    public LookupTableComponent(){
        destinationLookupTable = new ConcurrentHashMap<>();
    }

    public ConcurrentSkipListSet<String> add(String userName){
        return destinationLookupTable.putIfAbsent(userName, new ConcurrentSkipListSet<>());
    }

    public ConcurrentSkipListSet<String> get(String userName){
        return destinationLookupTable.get(userName);
    }

    public ConcurrentSkipListSet<String> remove(String userName) {
        return destinationLookupTable.remove(userName);
    }

    public Set<String> getUsers() {
        return destinationLookupTable.keySet();
    }

    public Stream<String> getUsersExceptUser(String userName, String destination) {
        return this.getUsers()
                .stream()
                .filter((user) -> !user.equals(userName) && destinationLookupTable.get(user).contains(destination));
    }

    public long countExceptUser(String userName, String destination) {
        return getUsersExceptUser(userName, destination).count();
    }
}
