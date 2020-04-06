package by.batseko.library.service;
import by.batseko.library.entity.User;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.factory.ValidatorFactory;
import by.batseko.library.validatior.CacheValidator;

import java.util.Map;
import java.util.concurrent.*;

public class ActiveUsersCache {
    private static final int DEFAULT_SESSION_TIMEOUT_MINUTES = 15;
    private static final long DEFAULT_TIME_TO_KEEP_ALIVE = TimeUnit.MINUTES.toMillis(DEFAULT_SESSION_TIMEOUT_MINUTES);
    private static final long CACHE_FLUSHER_PERIOD = DEFAULT_TIME_TO_KEEP_ALIVE / 2;

    private final Map<String, UserData> userCache;
    private final CacheValidator cacheValidator;

    private ActiveUsersCache(){
        userCache = new ConcurrentHashMap<>();
        cacheValidator = ValidatorFactory.getInstance().getCacheValidator();

        Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                Thread singleDaemonThread = new Thread(runnable);
                singleDaemonThread.setDaemon(true);
                return singleDaemonThread;
            }
        }).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                removeInactiveUsersFromCache();
            }
        }, DEFAULT_TIME_TO_KEEP_ALIVE, CACHE_FLUSHER_PERIOD, TimeUnit.MILLISECONDS);
    }

    private static class ActiveUsersCacheSingletonHolder {
        static final ActiveUsersCache INSTANCE = new ActiveUsersCache();
    }

    public static ActiveUsersCache getInstance() {
        return ActiveUsersCacheSingletonHolder.INSTANCE;
    }

    private void removeInactiveUsersFromCache() {
        long current = System.currentTimeMillis();
        for (UserData userData : userCache.values()) {
            if (!userData.isLive(current)) {
                userCache.remove(userData.user.getLogin());
            }
        }
    }

    public void addKeepAliveTimeToUser(String login, long additionAliveTime) {
        if (cacheValidator.isLessThanMinTimeout(additionAliveTime)) {
            additionAliveTime = DEFAULT_TIME_TO_KEEP_ALIVE;
        }
        if (login != null) {
            for (UserData userData : userCache.values()) {
                if (login.equals(userData.user.getLogin())) {
                    userData.addAliveTime(additionAliveTime);
                    return;
                }
            }
        }
    }

    public void put(String login, User user) {
        put(login, user, DEFAULT_TIME_TO_KEEP_ALIVE);
    }

    public void put(String login, User user, long timeout) {
        if (cacheValidator.isLessThanMinTimeout(timeout)) {
            timeout = DEFAULT_TIME_TO_KEEP_ALIVE;
        }
        if (login != null && user != null) {
            userCache.put(login, new UserData(user, timeout));
        }
    }

    public User get(String login) throws LibraryServiceException {
        if (login != null) {
            return userCache.get(login).user;
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


    private static class UserData {
        private final User user;
        private long keepAliveTime;

        private UserData(User user, long timeout) {
            this.user = user;
            this.keepAliveTime = System.currentTimeMillis() + timeout;
        }

        private void addAliveTime(long additionTimeout) {
            keepAliveTime = System.currentTimeMillis() + additionTimeout;
        }

        private boolean isLive(long currentTimeMillis) {
            return currentTimeMillis < keepAliveTime;
        }
    }
}

