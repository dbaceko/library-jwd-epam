package by.batseko.library.factory;

import by.batseko.library.entity.book.bookcomponent.Author;
import by.batseko.library.entity.book.bookcomponent.BookLanguage;
import by.batseko.library.entity.book.bookcomponent.Genre;
import by.batseko.library.entity.book.bookcomponent.Publisher;
import by.batseko.library.service.BookComponentService;
import by.batseko.library.service.BookOrderService;
import by.batseko.library.service.BookService;
import by.batseko.library.service.impl.*;
import by.batseko.library.service.UserService;
import by.batseko.library.service.impl.UserServiceImpl;

public class ServiceFactory {
    private final UserService userService;
    private final BookService bookService;
    private final BookComponentService<Author> bookAuthorService;
    private final BookComponentService<Genre> bookGenreService;
    private final BookComponentService<BookLanguage> bookLanguageService;
    private final BookComponentService<Publisher> bookPublisherService;
    private final BookOrderService bookOrderService;

    private ServiceFactory() {
        userService = new UserServiceImpl();
        bookService = new BookServiceImpl();
        bookAuthorService = new BookAuthorServiceImpl();
        bookGenreService = new BookGenreServiceImpl();
        bookLanguageService = new BookLanguageServiceImpl();
        bookPublisherService = new BookPublisherServiceImpl();
        bookOrderService = new BookOrderServiceImpl();
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

    public BookOrderService getBookOrderService() {
        return bookOrderService;
    }
}
