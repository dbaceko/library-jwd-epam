package by.batseko.library.factory;

import by.batseko.library.dao.UserDAO;
import by.batseko.library.dao.impl.UserDAOImpl;

public class DAOFactory {
    private final UserDAO userDAO;

    private DAOFactory(){
        userDAO = new UserDAOImpl();
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
}
