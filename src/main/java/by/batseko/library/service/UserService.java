package by.batseko.library.service;

import by.batseko.library.entity.user.User;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.service.impl.OnlineUsersCache;

import java.util.List;

public interface UserService {


    User logIn(String login, String password) throws LibraryServiceException;
    void logOut(String login);
    User findUserByLogin(String login) throws LibraryServiceException;
    User findUserById(int id) throws LibraryServiceException;
    List<User> findAllUsers() throws LibraryServiceException;

    void registerUser(User user) throws LibraryServiceException;
    void updateUserProfileData(User user) throws LibraryServiceException;
    void updateUserBanStatus(User user) throws LibraryServiceException;
    void deleteUserById(int userID) throws LibraryServiceException;

    OnlineUsersCache getOnlineUsersCache();
}
