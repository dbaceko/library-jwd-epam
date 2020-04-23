package by.batseko.library.factory;

import by.batseko.library.dao.book.*;
import by.batseko.library.dao.book.impl.*;
import by.batseko.library.dao.user.UserDAO;
import by.batseko.library.dao.user.impl.UserDAOImpl;

public class DAOFactory {
    private final UserDAO userDAO;
    private final BookAuthorDAO bookAuthorDAO;
    private final BookGenreDAO bookGenreDAO;
    private final BookLanguageDAO bookLanguageDAO;
    private final BookPublisherDAO bookPublisherDAO;
    private final BookInstanceDAO bookInstanceDAO;
    private final BookDAO bookDAO;

    private DAOFactory(){
        userDAO = new UserDAOImpl();
        bookAuthorDAO = new BookAuthorDAOImpl();
        bookLanguageDAO = new BookLanguageDAOImpl();
        bookPublisherDAO = new BookPublisherDAOImpl();
        bookGenreDAO = new BookGenreDAOImpl();
        bookInstanceDAO = new BookInstanceDAOImpl();
        bookDAO = new BookDAOImpl();
    }

    private static class DAOFactorySingletonHolder {
        static final DAOFactory INSTANCE = new DAOFactory();
    }

    public static DAOFactory getInstance() {
        return DAOFactorySingletonHolder.INSTANCE;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public BookAuthorDAO getBookAuthorDAO() {
        return bookAuthorDAO;
    }

    public BookGenreDAO getBookGenreDAO() {
        return bookGenreDAO;
    }

    public BookLanguageDAO getBookLanguageDAO() {
        return bookLanguageDAO;
    }

    public BookPublisherDAO getBookPublisherDAO() {
        return bookPublisherDAO;
    }

    public BookInstanceDAO getBookInstanceDAO() {
        return bookInstanceDAO;
    }

    public BookDAO getBookDAO() {
        return bookDAO;
    }
}
