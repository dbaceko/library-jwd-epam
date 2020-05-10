package by.batseko.library.dao.book;

import by.batseko.library.entity.book.Book;
import by.batseko.library.entity.book.BookInstance;
import by.batseko.library.exception.LibraryDAOException;

import java.util.List;

    /**
     * Interface describes the opportunity that data source provide to store and
     * restore {@link BookInstance} entity
     */
public interface BookInstanceDAO {

    /**
     * Searches and returns {@link List<String>} of {@link BookInstance}'s uuid if
     * {@link BookInstance} isAvailable param is true
     *
     * @param bookUUID the id of the {@link Book} that is looking for
     * @return List of {@link String} which is {@link BookInstance}'s uuid
     *
     * @throws LibraryDAOException if an error occurs while working with a data source
     */
    List<String> findAllAvailableBookInstanceUUIDsByBookUUID(String bookUUID) throws LibraryDAOException;
}
