package by.batseko.library.service;

import by.batseko.library.exception.LibraryServiceException;

import java.util.List;

/**
 * Interface describes the behavior of Cache
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of mapped values
 */
public interface Cache<K, V> {

    /**
     * Saves {@link V} value into cache
     *
     * @param value {@link V}  the type of mapped values
     * @throws LibraryServiceException if {@link K} key  or {@link V} value is null
     */
    void put(V value) throws LibraryServiceException;

    /**
     * Getting {@link V} {@link V} value from cache
     *
     * @param key {@link K} the type of keys maintained by this cache
     * @return value {@link V}  founded by key
     * @throws LibraryServiceException if {@link K} key  is null
     */
    V get(K key) throws LibraryServiceException;

    /**
     * Getting list of all sorted values from cache
     *
     * @return {@link List<V>} list of values into cache
     */
    List<V> getAllSortedValues();

    /**
     * Getting list of all values from cache
     *
     * @return {@link List<V>} list of values into cache
     */
    List<V> getAllValues();

    /**
     * Removing {@link V} value from cache by key
     *
     * @param key {@link K} the type of keys maintained by this cache
     */
    void remove(K key);

    /**
     * Removing all values from cache
     */
    void removeAll();
}
