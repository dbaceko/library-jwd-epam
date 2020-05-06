package by.batseko.library.dao.book;

import by.batseko.library.entity.book.bookcomponent.BaseBookComponent;
import by.batseko.library.exception.LibraryDAOException;

import java.util.List;

/**
 * Interface describes the opportunity that data source provide to store and
 * restore T extends BaseBookComponent entity
 */
 public interface BookComponentDAO <T extends BaseBookComponent> {

    /**
     * Add a <tt>T extends BaseBookComponent</tt>  into data source.
     * Throws LibraryDAOException if an error occurs while adding a <tt>T extends BaseBookComponent</tt>
     *
     * @param element the T extends BaseBookComponent object that must be save into date source
     * @throws LibraryDAOException if an error occurs while adding a <tt>T extends BaseBookComponent</tt>
     */
    void add(T element) throws LibraryDAOException;

    /**
     * Find and returns <tt>T extends BaseBookComponent</tt> from the data source.
     * Throws LibraryDAOException if an error occurs while adding a <tt>T extends BaseBookComponent</tt>
     *
     * @param bookComponentUUID is the UUID of <tt>T extends BaseBookComponent</tt>
     * @return T extends BaseBookComponent
     * @throws LibraryDAOException if an error occurs while adding a <tt>T extends BaseBookComponent</tt>
     */
    T findByUUID(String bookComponentUUID) throws LibraryDAOException;

    /**
     * Find and returns all <tt>T extends BaseBookComponent</tt> from the data source.
     * Throws LibraryDAOException if an error occurs while adding a <tt>T extends BaseBookComponent</tt>
     *
     * @return all T extends BaseBookComponent
     * @throws LibraryDAOException if an error occurs while adding a <tt>T extends BaseBookComponent</tt>
     */
    List<T> findAll() throws LibraryDAOException;
}
