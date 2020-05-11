package by.batseko.library.service.user;

import by.batseko.library.entity.user.User;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.service.Cache;
import by.batseko.library.service.user.impl.OnlineUsersCache;

import java.util.List;
/**
 * Describes the behavior of {@link User} entity.
 */
public interface UserService {

    /**
     * Log in and return {@link User} instance
     *
     * @param login {@link User}'s login
     * @param password {@link User}'s password
     * @return {@link User} instance
     * @throws LibraryServiceException if <tt>login</tt> or <tt>password</tt> is null or
     *                          empty or if <tt>login</tt> or <tt>password</tt> do
     *                          not accords to specify pattern
     *                          {@see by.batseko.library.validator.UserValidator}
     *                          or if {@link User} with <tt>login</tt> and <tt>password</tt>
     *                          do not present into data source or if an error occurs
     *                          while searching {@link User} into the data source
     */
    User logInByPassword(String login, String password) throws LibraryServiceException;

    /**
     * Log in and return {@link User} instance
     *
     * @param token {@link User}'s token access
     * @return {@link User} instance
     * @throws LibraryServiceException if <tt>token</tt> or <tt>password</tt> is null
     *                          or if {@link User} with <tt>token</tt>
     *                          do not present into data source or if an error occurs
     *                          while searching {@link User} into the data source
     */
    User logInByToken(String token) throws LibraryServiceException;

    /**
     * Log out {@link User} remove user from cache
     *
     * @param login {@link User}'s login
     */
    void logOut(String login);

    /**
     * Find user {@link User} instance by <tt>login</tt>
     *
     * @param login {@link User}'s login
     * @return {@link User} instance
     * @throws LibraryServiceException if <tt>login</tt> is null or empty or if <tt>login</tt>
     *                          or not accords to specify pattern
     *                          {@see by.batseko.library.validator.UserValidator}
     *                          or if {@link User} with <tt>login</tt> do not present into
     *                          data source or if an error occurs while searching {@link User}
     *                          into the data source
     */
    User findUserByLogin(String login) throws LibraryServiceException;

    /**
     * Find user {@link User} instance by <tt>id</tt>
     *
     * @param id {@link User}'s id
     * @return {@link User} instance
     * @throws LibraryServiceException if {@link User} with <tt>login</tt> do not present into
     *                          data source or if an error occurs while searching {@link User}
     *                          into the data source
     */
    User findUserById(int id) throws LibraryServiceException;

    /**
     * Find all users list {@link List<User>}
     *
     * @return all users list {@link List<User>}
     * @throws LibraryServiceException if {@link List<User>} in empty
     *                          occurs after searching {@link User} into the data source
     */
    List<User> findAllUsers() throws LibraryServiceException;

    /**
     * Generate user's access token by <tt>id</tt> which uses for forget password functional
     *
     * @param id {@link User}'s id
     * @return {@link String} user's access token
     * @throws LibraryServiceException if {@link User} with <tt>id</tt> do not present into
     *                          data source or if an error occurs while searching {@link User}
     *                          into the data source
     */
    String generateRememberUserToken(int id) throws LibraryServiceException;

    /**
     * Sending access token with link to <tt>email</tt>
     *
     * @param email {@link User}'s email
     * @param pageRootUrl is page URL
     * @throws LibraryServiceException if {@link User} with <tt>email</tt> do not present into
     *                          data source or if an error occurs while searching {@link User}
     *                          into the data source or if something goes wrong while sending email
     */
    void sendLogInTokenIfForgetPassword(String email, String pageRootUrl) throws LibraryServiceException;

    /**
     * Deleting user's access token by <tt>id</tt>
     *
     * @param id {@link User}'s id
     * @throws LibraryServiceException if {@link User} with <tt>id</tt> do not present into
     *                          data source or if an error occurs while searching {@link User}
     *                          into the data source
     */
    void deleteRememberUserToken(int id) throws LibraryServiceException;

    /**
     * Registers {@link User} with filled fields
     *
     * @param user {@link User} is filled user instance
     * @throws LibraryServiceException if <tt>user</tt>'s fields not accords to specify pattern
     *                          {@see by.batseko.library.validator.UserValidator}
     *                          or if user with <tt>email</tt> or <tt>login</tt> has already
     *                          or if an error occurs while writing new {@link User} into
     *                          data source
     */
    void registerUser(User user) throws LibraryServiceException;

    /**
     * Update {@link User} with filled fields
     *
     * @param user {@link User} is filled user instance
     * @throws LibraryServiceException if <tt>user</tt>'s fields not accords to specify pattern
     *                          {@see by.batseko.library.validator.UserValidator}
     *                          or if user with <tt>email</tt>  has already
     *                          or if an error occurs while writing new {@link User} into
     *                          data source
     */
    void updateUserProfileData(User user) throws LibraryServiceException;

    /**
     * Update {@link User}'s ban status
     *
     * @param user {@link User} is filled user instance
     * @throws LibraryServiceException if an error occurs while writing new
     *                          {@link User} into data source
     */
    void updateUserBanStatus(User user) throws LibraryServiceException;

    /**
     * Returns {@link OnlineUsersCache} which contains online users
     *
     * @return {@link OnlineUsersCache} online users cache
     */
    Cache<String, User> getOnlineUsersCache();
}
