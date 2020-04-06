package by.batseko.library.dao;

import by.batseko.library.entity.User;
import by.batseko.library.exception.LibraryDAOException;

public interface UserDAO {
    void registerUser(User user) throws LibraryDAOException;
    void updateUser(User user);
    User getUserByLogin(String userLogin) throws LibraryDAOException;
    User getUserByID(String userID);


}
