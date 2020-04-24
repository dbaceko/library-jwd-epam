package by.batseko.library.factory;

import by.batseko.library.entity.book.bookcomponent.Author;
import by.batseko.library.entity.book.bookcomponent.BookLanguage;
import by.batseko.library.entity.book.bookcomponent.Genre;
import by.batseko.library.entity.book.bookcomponent.Publisher;
import by.batseko.library.service.book.*;
import by.batseko.library.service.book.impl.*;
import by.batseko.library.service.user.UserService;
import by.batseko.library.service.user.impl.UserServiceImpl;

public class ServiceFactory {
    private final UserService userService;
    private final BookService bookService;
    private final BookComponentService<Author> bookAuthorService;
    private final BookComponentService<Genre> bookGenreService;
    private final BookComponentService<BookLanguage> bookLanguageService;
    private final BookComponentService<Publisher> bookPublisherService;

    private ServiceFactory() {
        userService = new UserServiceImpl();
        bookService = new BookServiceImpl();
        bookAuthorService = new BookAuthorServiceImplService();
        bookGenreService = new BookGenreServiceImplService();
        bookLanguageService = new BookLanguageServiceImplService();
        bookPublisherService = new BookPublisherServiceImplService();
    }

    private static class ServiceFactorySingletonHolder {
        static final ServiceFactory INSTANCE = new ServiceFactory();
    }

    public static ServiceFactory getInstance() {
        return ServiceFactorySingletonHolder.INSTANCE;
    }

    public UserService getUserService() {
        return userService;
    }

    public BookService getBookService() {
        return bookService;
    }

    public BookComponentService<Author> getBookAuthorService() {
        return bookAuthorService;
    }

    public BookComponentService<Genre> getBookGenreService() {
        return bookGenreService;
    }

    public BookComponentService<BookLanguage> getBookLanguageService() {
        return bookLanguageService;
    }

    public BookComponentService<Publisher> getBookPublisherService() {
        return bookPublisherService;
    }
}
