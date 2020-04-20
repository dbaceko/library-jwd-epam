package by.batseko.library.dao.book;

import by.batseko.library.entity.book.Genre;
import by.batseko.library.exception.LibraryDAOException;

import java.util.List;

public interface BookGenreDAO {
    void addBookGenre(Genre genre) throws LibraryDAOException;
    Genre findBookGenreByUUID(String bookGenreUUID) throws LibraryDAOException;
    List<Genre> findAllBookGenres() throws LibraryDAOException;
}
