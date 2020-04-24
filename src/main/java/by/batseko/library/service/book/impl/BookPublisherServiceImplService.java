package by.batseko.library.service.book.impl;

import by.batseko.library.entity.book.bookcomponent.Publisher;
import by.batseko.library.exception.LibraryDAOException;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.exception.ValidatorException;

import by.batseko.library.service.book.BookComponentService;

public class BookPublisherServiceImplService extends BaseBookService implements BookComponentService<Publisher> {
    @Override
    public void add(Publisher publisher) throws LibraryServiceException {
        try {
            bookValidator.validatePublisher(publisher.getPublisherTitle());
            publisher.defineUUID();
            bookPublisherDAO.add(publisher);
            bookComponentsCache.getPublishers().put(publisher);
        } catch (LibraryDAOException | ValidatorException e) {
            throw new LibraryServiceException(e);
        }
    }

    @Override
    public Publisher findByUUID(String uuid) throws LibraryServiceException {
        try {
            return bookPublisherDAO.findByUUID(uuid);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e);
        }
    }
}
