package by.batseko.library.service.book.impl;

import by.batseko.library.entity.book.bookcomponent.BaseBookComponent;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

class BookComponentStorage<T extends BaseBookComponent> {
    private final ConcurrentHashMap<String, T> component;

    BookComponentStorage() {
        component = new ConcurrentHashMap<>();
    }

    void put(T element) {
        component.put(element.getUuid(), element);
    }

    void remove(String key) {
        component.remove(key);
    }

    void remove(T element) {
        remove(element.getUuid());
    }

    T get(String key) {
        return component.get(key);
    }

    public Collection<T> getAllValues() {
        return component.values();
    }

    void putAllElements(Collection<T> collection) {
        if (collection != null) {
            for (T element: collection) {
                put(element);
            }
        }
    }
}
