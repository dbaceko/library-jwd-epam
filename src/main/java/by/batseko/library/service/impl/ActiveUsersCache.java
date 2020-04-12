package by.batseko.library.service.impl;
import by.batseko.library.entity.User;
import by.batseko.library.exception.LibraryServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.*;

public class ActiveUsersCache {
    private static final Logger LOGGER = LogManager.getLogger(ActiveUsersCache.class);

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

    public void put(String login, User user) throws LibraryServiceException {
        if (login == null || user == null) {
            LOGGER.warn("Can't put null value to cache");
            throw new LibraryServiceException("service.commonError");
        }
        userCache.put(login, user);
    }

    public User get(String login) throws LibraryServiceException {
        if (login == null) {
            LOGGER.warn("Can't get null login from cache");
            throw new LibraryServiceException("service.commonError");
        }
        return userCache.get(login);

    }

    public void remove(String login) {
        if (login == null) {
            LOGGER.warn("Can't remove null login from cache");
        }
        userCache.remove(login);
    }

    public void removeAll() {
        userCache.clear();
    }

}

