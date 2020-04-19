package by.batseko.library.dao;

import by.batseko.library.entity.user.User;
import by.batseko.library.exception.LibraryDAOException;

import java.util.List;

public interface UserDAO {
    void registerUser(User user) throws LibraryDAOException;
    void updateUserProfileData(User user) throws LibraryDAOException;
    void updateUserBanStatus(User user) throws LibraryDAOException;
    User findUserByLogin(String userLogin) throws LibraryDAOException;
    User findUserByID(int userID) throws LibraryDAOException;
    List<User> findAllUsers() throws LibraryDAOException;
    void deleteUserByID(int userID) throws LibraryDAOException;

}
