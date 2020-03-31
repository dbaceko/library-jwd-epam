package by.batseko.library.service;

import by.batseko.library.dao.UserDAO;
import by.batseko.library.entity.User;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.factory.DAOFactory;

public interface UserService {


    User logIn(String login, String password) throws LibraryServiceException;
    void logOut(String login);
    User getUserByLogin(String login) throws LibraryServiceException;

    void registerUser(User user) throws LibraryServiceException;
    void updateUser(User user) throws LibraryServiceException;
    void deleteUser(User user) throws LibraryServiceException;
}
