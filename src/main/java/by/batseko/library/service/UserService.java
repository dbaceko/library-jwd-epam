package by.batseko.library.service;

import by.batseko.library.entity.User;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.service.impl.ActiveUsersCache;

public interface UserService {


    User logIn(String login, String password) throws LibraryServiceException;
    void logOut(String login);
    User getUserByLogin(String login) throws LibraryServiceException;
    User getUserByID(int id) throws LibraryServiceException;

    void registerUser(User user) throws LibraryServiceException;
    void updateUser(User user) throws LibraryServiceException;
    void deleteUserByID(int userID) throws LibraryServiceException;

    ActiveUsersCache getActiveUsersCache();
}
