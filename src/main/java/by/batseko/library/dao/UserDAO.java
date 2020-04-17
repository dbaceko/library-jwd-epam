package by.batseko.library.dao;

import by.batseko.library.entity.User;
import by.batseko.library.exception.LibraryDAOException;

public interface UserDAO {
    void registerUser(User user) throws LibraryDAOException;
    void updateUserProfileData(User user) throws LibraryDAOException;
    void updateUserBanStatus(User user) throws LibraryDAOException;
    User findUserByLogin(String userLogin) throws LibraryDAOException;
    User findUserByID(int userID) throws LibraryDAOException;
    void deleteUserByID(int userID) throws LibraryDAOException;

}
