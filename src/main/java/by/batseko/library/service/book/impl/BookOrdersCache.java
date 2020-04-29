package by.batseko.library.service.book.impl;

import by.batseko.library.entity.order.BookOrder;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.service.Cache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BookOrdersCache  implements Cache<String, BookOrdersCache.UserBookOrdersMap> {
    private static final Logger LOGGER = LogManager.getLogger(BookOrdersCache.class);

    private final Map<String, UserBookOrdersMap> userOrdersCache;
    private final UserBookOrdersMap userBookOrdersMap;

    private BookOrdersCache(){
        userOrdersCache = new ConcurrentHashMap<>();
        userBookOrdersMap = new UserBookOrdersMap();
    }

    private static class BookOrdersCacheSingletonHolder {
        static final BookOrdersCache INSTANCE = new BookOrdersCache();
    }

    static BookOrdersCache getInstance() {
        return BookOrdersCacheSingletonHolder.INSTANCE;
    }

    public void put(String login, List<BookOrder> orders) throws LibraryServiceException {
        if (login == null || orders == null) {
            LOGGER.warn("Can't put null value to cache");
            throw new LibraryServiceException("service.commonError");
        }
        userBookOrdersMap.putAllElements(orders);
        userOrdersCache.put(login, userBookOrdersMap);
    }

    @Override
    public void put(String key, UserBookOrdersMap value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UserBookOrdersMap get(String key) throws LibraryServiceException {
        if (key == null) {
            LOGGER.warn("Can't get null login from cache");
            throw new LibraryServiceException("service.commonError");
        }
        return userOrdersCache.get(key);
    }

    @Override
    public List<UserBookOrdersMap> getAllValues() {
        return new ArrayList<>(userOrdersCache.values());
    }

    public void remove(String login) {
        if (login == null) {
            LOGGER.warn("Can't remove null login from cache");
        } else {
            userOrdersCache.remove(login);
        }
    }

    public void removeAll() {
        userOrdersCache.clear();
    }

    public static class UserBookOrdersMap implements Cache<String, BookOrder>{
        Map<String, BookOrder> bookOrderMap = new ConcurrentHashMap<>();

        @Override
        public void put(String key, BookOrder value) throws LibraryServiceException {
            if (key == null || value == null) {
                LOGGER.warn("Can't put null value to cache");
                throw new LibraryServiceException("service.commonError");
            }
            bookOrderMap.put(key, value);
        }

        @Override
        public BookOrder get(String key) throws LibraryServiceException {
            if (key == null) {
                LOGGER.warn("Can't get key login from cache");
                throw new LibraryServiceException("service.commonError");
            }
            return bookOrderMap.get(key);
        }

        @Override
        public List<BookOrder> getAllValues() {
            return new ArrayList<>(bookOrderMap.values());
        }

        @Override
        public void remove(String key) {
            if (key == null) {
                LOGGER.warn("Can't remove null login from cache");
            } else {
                bookOrderMap.remove(key);
            }
        }

        @Override
        public void removeAll() {
            bookOrderMap.clear();
        }


        void putAllElements(Collection<BookOrder> collection) throws LibraryServiceException {
            if (collection != null) {
                for (BookOrder element: collection) {
                    put(element.getUuid(), element);
                }
            }
        }
    }
}
