package by.batseko.library.service.book;

import by.batseko.library.dto.BookDTO;
import by.batseko.library.entity.book.Book;
import by.batseko.library.entity.book.BookInstance;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.service.book.impl.CommonBookComponentsCache;

import java.util.List;

/**
 * Interface describes the behavior of {@link Book} entity
 */
public interface BookService {

    /**
     * Saves {@link Book}, add <tt>quantity</tt> of {@link BookInstance}s.
     *
     * @param book Book instance
     * @param quantity is <tt>quantity</tt> of {@link BookInstance}s.
     * @throws LibraryServiceException if <tt>book</tt>'s fields not accords to specify pattern
     *                          {@see by.batseko.library.validator.BookValidator}
     *                          or if Book with this fields has already exist
     *                          or if an error occurs while writing new {@link Book} into
     *                          data source
     */
    void add(Book book, int quantity) throws LibraryServiceException;

    /**
     * Find book {@link Book} instance by <tt>uuid</tt>
     *
     * @param uuid {@link Book}'s uuid
     * @return {@link Book} instance
     * @throws LibraryServiceException if {@link Book} with <tt>uuid</tt> do not present into
     *                          data source or if an error occurs while searching {@link Book}
     *                          into the data source
     */
    Book findByUUID(String uuid) throws LibraryServiceException;

    /**
     * Find book list of {@link List<Book>} which follow the book fields pattern
     *
     * @param book is the {@link Book} which contains non-empty fields
     *        which using to construct search query into DAO layer
     * @param currentPage is the current page parameter for pagination
     * @param recordsPerPage is the records per page parameter for pagination
     * @return book list {@link List<Book>} which follow the book fields pattern
     * @throws LibraryServiceException if {@link List<Book>} in empty or if book is null
     *                          or occurs after searching {@link Book} into the data source
     */
    List<BookDTO> findByFields(Book book, int currentPage, int recordsPerPage) throws LibraryServiceException;

    /**
     * Find quantity of {@link List<BookDTO>} which follow the book fields pattern
     *
     * @param book is the {@link Book} which contains non-empty fields
     *        which using to construct search query into DAO layer
     * @return quantity of {@link List<BookDTO>}  which follow the book fields pattern
     * @throws LibraryServiceException if {@link List<BookDTO>} in empty or if book is null
     *                          or occurs after searching {@link Book} into the data source
     */
    int findBookQuantityByFields(Book book) throws LibraryServiceException;

    /**
     * Find all book list of {@link List<BookDTO>}
     *
     * @param currentPage is the current page parameter for pagination
     * @param recordsPerPage is the records per page parameter for pagination
     * @return all book list {@link List<BookDTO>}
     * @throws LibraryServiceException occurs after searching {@link Book} into the data source
     */
    List<BookDTO> findAllBookDTO(int currentPage, int recordsPerPage) throws LibraryServiceException;

    /**
     * Returns {@link CommonBookComponentsCache} which contains book components collections
     *
     * @return {@link CommonBookComponentsCache} which contains book components collections
     */
    CommonBookComponentsCache getBookComponentsCache();
}
