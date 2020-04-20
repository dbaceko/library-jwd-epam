package by.batseko.library.dao.book;

import by.batseko.library.entity.book.Publisher;
import by.batseko.library.exception.LibraryDAOException;

import java.util.List;

public interface BookPublisherDAO {
    void addBookPublisher(Publisher publisher) throws LibraryDAOException;
    Publisher findBookPublisherByUUID(String bookPublisherUUID) throws LibraryDAOException;
    List<Publisher> findAllBookPublishers() throws LibraryDAOException;
}
