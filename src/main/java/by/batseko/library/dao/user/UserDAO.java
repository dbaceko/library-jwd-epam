package by.batseko.library.dao.user;

import by.batseko.library.entity.user.User;
import by.batseko.library.exception.LibraryDAOException;

import java.util.List;

public interface UserDAO {
    /**
     * Saves the <tt>user</tt> into data source. Throws LibraryDAOException
     * if an error occurs while writing a <tt>user</tt>
     *
     * @param user the {@link User} that should be added to data source
     * @throws LibraryDAOException if an error occurs while writing a <tt>user</tt>
     */
    void registerUser(User user) throws LibraryDAOException;

    /**
     * Updates common information about {@link User} in a data source.
     * Throws LibraryDAOException if an error occurs while writing a <tt>user</tt>
     *
     * @param user {@link User} that information is updating
     * @throws LibraryDAOException if an error occurs while writing a <tt>user</tt>
     */
    void updateUserProfileData(User user) throws LibraryDAOException;

    /**
     * Updates ban status of {@link User} in a data source.
     * Throws LibraryDAOException if an error occurs while writing a <tt>user</tt>
     *
     * @param user {@link User} that information is updating
     * @throws LibraryDAOException if an error occurs while writing a <tt>user</tt>
     */
    void updateUserBanStatus(User user) throws LibraryDAOException;

    /**
     * Updates access token of {@link User} in a data source.
     * Throws LibraryDAOException if an error occurs while writing a <tt>user</tt>
     *
     * @param userId <tt>user</tt>'s id which help find a <tt>user</tt>
     * @param userToken information is updating
     * @throws LibraryDAOException if an error occurs while writing a <tt>user</tt>
     */
    void updateRememberUserToken(int userId, String userToken) throws LibraryDAOException;

    /**
     * Delete access token of {@link User} in a data source.
     * Throws LibraryDAOException if an error occurs while writing a <tt>user</tt>
     *
     * @param userId <tt>user's</tt> id which help find a <tt>user</tt>
     * @throws LibraryDAOException if an error occurs while writing a <tt>user</tt>
     */
    void deleteRememberUserToken(int userId) throws LibraryDAOException;

    /**
     * Retrieves and returns {@link User}.
     * If no such user contains into data source throws LibraryDAOException
     *
     * @param userLogin {@link User}'s userLogin
     * @return an {@link User}
     * @throws LibraryDAOException if an error occurs while getting a <tt>user</tt>
     */
    User findUserByLogin(String userLogin) throws LibraryDAOException;

    /**
     * Retrieves and returns {@link User}.
     * If no such user contains into data source throws LibraryDAOException
     *
     * @param userEmail {@link User}'s userEmail
     * @return an {@link User}
     * @throws LibraryDAOException if an error occurs while getting a <tt>user</tt>
     */
    User findUserByEmail(String userEmail) throws LibraryDAOException;

    /**
     * Retrieves and returns {@link User}.
     * If no such user contains into data source throws LibraryDAOException
     *
     * @param userId {@link User}'s userId
     * @param token {@link User}'s userToken
     * @return an {@link User}
     * @throws LibraryDAOException if an error occurs while getting a <tt>user</tt>
     */
    User findUserByIdAndToken(int userId,String token) throws LibraryDAOException;

    /**
     * Retrieves and returns {@link User}.
     * If no such user contains into data source throws LibraryDAOException
     *
     * @param userId {@link User}'s userId
     * @return an {@link User}
     * @throws LibraryDAOException if an error occurs while getting a <tt>user</tt>
     */
    User findUserById(int userId) throws LibraryDAOException;

    /**
     * Retrieves and returns {@link User}.
     * If no such users contains into data source returns emptyList collection
     *
     * @return List of {@link User}
     * @throws LibraryDAOException if an error occurs while getting a <tt>user</tt>
     */
    List<User> findAllUsers() throws LibraryDAOException;
}
