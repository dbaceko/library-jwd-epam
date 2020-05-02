package by.batseko.library.service.user.impl;

import by.batseko.library.command.JSPAttributeStorage;
import by.batseko.library.dao.user.UserDAO;
import by.batseko.library.entity.user.User;
import by.batseko.library.exception.EncryptionException;
import by.batseko.library.exception.LibraryDAOException;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.exception.ValidatorException;
import by.batseko.library.factory.DAOFactory;
import by.batseko.library.factory.ServiceFactory;
import by.batseko.library.factory.UtilFactory;
import by.batseko.library.factory.ValidatorFactory;
import by.batseko.library.service.book.BookOrderService;
import by.batseko.library.service.user.UserService;
import by.batseko.library.util.Encryption;
import by.batseko.library.validatior.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private static final int USER_ID_COOKIE_INDEX = 0;
    private static final int TOKEN_VALUE_COOKIE_INDEX = 1;

    private final UserValidator validator;
    private final Encryption encryption;
    private final OnlineUsersCache activeUserCache;
    private final UserDAO userDAO;

    public UserServiceImpl(){
        validator = ValidatorFactory.getInstance().getUserValidator();
        encryption = UtilFactory.getInstance().getEncryption();
        activeUserCache = OnlineUsersCache.getInstance();
        userDAO = DAOFactory.getInstance().getUserDAO();

    }

    @Override
    public User logInByPassword(String login, String password) throws LibraryServiceException {
        try {
            User user = userDAO.findUserByLogin(login);
            if (encryption.validatePassword(password, user.getPassword())) {
                LOGGER.info(String.format("Add user %s to cache", user));
                initCacheAfterLogIn(user);
                return user;
            } else {
                LOGGER.info("Password dont match");
                throw new LibraryServiceException("validation.user.login.incorrect");
            }
        } catch (EncryptionException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new LibraryServiceException("service.commonError", e);
        } catch (LibraryDAOException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public User logInByToken(String token) throws LibraryServiceException {
        try {
            LOGGER.warn(token);
            String[] tokenComponents = token.split(JSPAttributeStorage.COOKIE_REMEMBER_USER_TOKEN_DIVIDER);
            int userId = Integer.parseInt(tokenComponents[USER_ID_COOKIE_INDEX]);
            String tokenValue = tokenComponents[TOKEN_VALUE_COOKIE_INDEX];
            User user = userDAO.findUserByIdAndToken(userId, tokenValue);
            if (user != null) {
                initCacheAfterLogIn(user);
                return user;
            }
            LOGGER.warn(String.format("Cant use token %s for log in", token));
            throw new LibraryServiceException(String.format("Cant use token %s for log in", token));
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void logOut(String user) {
        activeUserCache.remove(user);
    }

    @Override
    public User findUserByLogin(String login) throws LibraryServiceException {
        User user = null;
        try {
            user = activeUserCache.get(login);
        } catch (LibraryServiceException e) {
            LOGGER.warn(e.getMessage(), e);
        }
        if (user == null) {
            LOGGER.info("User is not in cache");
            try {
                user = userDAO.findUserByLogin(login);
            } catch (LibraryDAOException e) {
                LOGGER.warn(e.getMessage(), e);
                throw new LibraryServiceException(e.getMessage(), e);
            }
        }
        return user;
    }

    @Override
    public User findUserById(int id) throws LibraryServiceException {
        try {
            return userDAO.findUserById(id);
        } catch (LibraryDAOException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<User> findAllUsers() throws LibraryServiceException {
        try {
            List<User> userList = userDAO.findAllUsers();
            if (userList.isEmpty()) {
                throw new LibraryServiceException("query.user.getUsers.usersNotFound");
            } else {
                return userList;
            }
        } catch (LibraryDAOException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public String generateRememberUserToken(int userId) throws LibraryServiceException {
        String token = UUID.randomUUID().toString();
        try {
            userDAO.setRememberUserToken(userId, token);
            return userId + JSPAttributeStorage.COOKIE_REMEMBER_USER_TOKEN_DIVIDER + token;
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteRememberUserToken(int userId) throws LibraryServiceException {
        try {
            userDAO.deleteRememberUserToken(userId);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void registerUser(User user) throws LibraryServiceException {
        try {
            validator.validateNewUser(user);
        } catch (ValidatorException e) {
            LOGGER.info(String.format("Invalid %s %n registration data %s", user, e.getMessage()));
            throw new LibraryServiceException(e.getMessage(), e);
        }
        try {
            user.setPassword(encryption.generateHash(user.getPassword()));
            userDAO.registerUser(user);
        } catch (EncryptionException e) {
            LOGGER.warn(e);
            throw new LibraryServiceException("service.commonError", e);
        } catch (LibraryDAOException e) {
            LOGGER.warn(e);
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void updateUserProfileData(User user) throws LibraryServiceException {
        try {
            validator.validateUpdatedUser(user);
        } catch (ValidatorException e) {
            LOGGER.info(String.format("invalid %s %n update data %s", user.toString(), e.getMessage()));
            throw new LibraryServiceException(e.getMessage(), e);
        }
        try {
            user.setPassword(encryption.generateHash(user.getPassword()));
            userDAO.updateUserProfileData(user);
            if(activeUserCache.get(user.getLogin()) != null) {
                activeUserCache.put(user.getLogin(), user);
            }
        } catch (EncryptionException | LibraryDAOException e) {
            LOGGER.warn(e);
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void updateUserBanStatus(User user) throws LibraryServiceException {
        try {
            userDAO.updateUserBanStatus(user);
            if(activeUserCache.get(user.getLogin()) != null) {
                activeUserCache.put(user.getLogin(), user);
            }
        } catch (LibraryDAOException e) {
            LOGGER.warn(e);
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteUserById(int userID) throws LibraryServiceException {
        try {
            userDAO.findUserById(userID);
        } catch (LibraryDAOException e) {
            LOGGER.info(e);
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public OnlineUsersCache getOnlineUsersCache() {
        return activeUserCache;
    }

    private void initCacheAfterLogIn(User user){
        BookOrderService bookOrderService = ServiceFactory.getInstance().getBookOrderService();
        try {
            activeUserCache.put(user.getLogin(), user);
        } catch (LibraryServiceException e) {
            LOGGER.warn(String.format("Can't put user %s in cache", user), e);
        }
        try {
            bookOrderService.getBookOrdersCache().put(user.getLogin(), bookOrderService.findAllOrdersByUserId(user.getId()));
        } catch (LibraryServiceException e) {
            LOGGER.warn(String.format("Can't put user's %s book orders in cache", user), e);
        }

    }
}
