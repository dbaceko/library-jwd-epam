package by.batseko.library.service.book;

import by.batseko.library.exception.LibraryServiceException;

public interface BookComponentService<T> {
    void add(T element) throws LibraryServiceException;
    T findByUUID(String uuid) throws LibraryServiceException;
}
