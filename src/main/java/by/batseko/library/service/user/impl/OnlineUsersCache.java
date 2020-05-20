package by.batseko.library.service.user.impl;

import by.batseko.library.entity.user.User;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.service.Cache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class OnlineUsersCache implements Cache<String, User> {
    private static final Logger LOGGER = LogManager.getLogger(OnlineUsersCache.class);

    private final Map<String, User> userCache;

    private OnlineUsersCache(){
        userCache = new ConcurrentHashMap<>();
    }

    private static class ActiveUsersCacheSingletonHolder {
        static final OnlineUsersCache INSTANCE = new OnlineUsersCache();
    }

    static OnlineUsersCache getInstance() {
        return ActiveUsersCacheSingletonHolder.INSTANCE;
    }

    public void put(User user) throws LibraryServiceException {
        if (user == null) {
            LOGGER.warn("Can't put null value to cache");
            throw new LibraryServiceException("service.commonError");
        }
        userCache.put(user.getLogin(), user);
    }

    public User get(String login) throws LibraryServiceException {
        if (login == null) {
            LOGGER.warn("Can't get null login from cache");
            throw new LibraryServiceException("service.commonError");
        }
        return userCache.get(login);

    }

    public List<User> getAllSortedValues() {
        List<User> list = getAllValues();
        Collections.sort(list);
        return list;
    }

    public List<User> getAllValues() {
        return new ArrayList<>(userCache.values());
    }

    public void remove(String login) {
        if (login == null) {
            LOGGER.warn("Can't remove null login from cache");
        } else {
            userCache.remove(login);
        }
    }

    public void removeAll() {
        userCache.clear();
    }
}

