package by.batseko.library.service.impl;
import by.batseko.library.entity.User;
import by.batseko.library.exception.LibraryServiceException;

import java.util.Map;
import java.util.concurrent.*;

public class ActiveUsersCache {
    private final Map<String, User> userCache;

    private ActiveUsersCache(){
        userCache = new ConcurrentHashMap<>();
    }

    private static class ActiveUsersCacheSingletonHolder {
        static final ActiveUsersCache INSTANCE = new ActiveUsersCache();
    }

    static ActiveUsersCache getInstance() {
        return ActiveUsersCacheSingletonHolder.INSTANCE;
    }

    public void put(String login, User user) {
        if (login != null && user != null) {
            userCache.put(login, user);
        }
    }

    public User get(String login) throws LibraryServiceException {
        if (login != null) {
            return userCache.get(login);
        }
        throw new LibraryServiceException("null input value");
    }

    public void remove(String login) {
        if (login != null) {
            userCache.remove(login);
        }
    }

    public void removeAll() {
        userCache.clear();
    }

}

