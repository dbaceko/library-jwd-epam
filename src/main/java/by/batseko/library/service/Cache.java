package by.batseko.library.service;

import by.batseko.library.exception.LibraryServiceException;

import java.util.List;

public interface Cache<K, V> {
    public void put(K key, V value) throws LibraryServiceException;

    public V get(K key) throws LibraryServiceException;

    public List<V> getAllValues();

    public void remove(K key);

    public void removeAll();
}
