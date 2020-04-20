package by.batseko.library.dao.book;

import by.batseko.library.entity.book.BookLanguage;
import by.batseko.library.exception.LibraryDAOException;

import java.util.List;

public interface BookLanguageDAO {
    void addBookLanguage(BookLanguage bookLanguage) throws LibraryDAOException;
    BookLanguage findBookLanguageByUUID(String bookLanguageUUID) throws LibraryDAOException;
    List<BookLanguage> findAllBookLanguages() throws LibraryDAOException;
}
