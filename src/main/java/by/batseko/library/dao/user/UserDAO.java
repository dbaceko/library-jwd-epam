package by.batseko.library.dao.user;

import by.batseko.library.entity.user.User;
import by.batseko.library.exception.LibraryDAOException;

import java.util.List;

public interface UserDAO {
    void registerUser(User user) throws LibraryDAOException;
    void updateUserProfileData(User user) throws LibraryDAOException;
    void updateUserBanStatus(User user) throws LibraryDAOException;
    void setRememberUserToken(int userId, String userToken) throws LibraryDAOException;
    void setRememberUserToken(String userEmail, String userToken) throws LibraryDAOException;
    void deleteRememberUserToken(int userId) throws LibraryDAOException;
    User findUserByLogin(String userLogin) throws LibraryDAOException;
    User findUserByEmail(String userEmail) throws LibraryDAOException;
    User findUserByIdAndToken(int userId,String token) throws LibraryDAOException;
    User findUserById(int userId) throws LibraryDAOException;
    List<User> findAllUsers() throws LibraryDAOException;
    void deleteUserById(int userId) throws LibraryDAOException;
}
