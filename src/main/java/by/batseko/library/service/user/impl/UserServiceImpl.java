package by.batseko.library.service.user.impl;

import by.batseko.library.dao.user.UserDAO;
import by.batseko.library.entity.user.User;
import by.batseko.library.exception.EncryptionException;
import by.batseko.library.exception.LibraryDAOException;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.exception.ValidatorException;
import by.batseko.library.factory.DAOFactory;
import by.batseko.library.factory.UtilFactory;
import by.batseko.library.factory.ValidatorFactory;
import by.batseko.library.service.user.UserService;
import by.batseko.library.util.Encryption;
import by.batseko.library.validatior.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private final UserValidator validator;
    private final Encryption encryption;
    private final by.batseko.library.service.user.impl.OnlineUsersCache activeUserCache;
    private final UserDAO userDAO;

    public UserServiceImpl(){
        validator = ValidatorFactory.getInstance().getUserValidator();
        encryption = UtilFactory.getInstance().getEncryption();
        activeUserCache = by.batseko.library.service.user.impl.OnlineUsersCache.getInstance();
        userDAO = DAOFactory.getInstance().getUserDAO();
    }

    @Override
    public User logIn(String login, String password) throws LibraryServiceException {
        try {
            User user = userDAO.findUserByLogin(login);
            if (encryption.validatePassword(password, user.getPassword())) {
                activeUserCache.put(user.getLogin(), user);
                LOGGER.info(String.format("Add user %s to cache", user));
                return user;
            } else {
                LOGGER.info("Password dont match");
                throw new LibraryServiceException("validation.user.login.incorrect");
            }
        } catch (EncryptionException e) {
            LOGGER.warn(e);
            throw new LibraryServiceException("service.commonError", e);
        } catch (LibraryDAOException e) {
            LOGGER.warn(e);
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
            LOGGER.warn(e);
        }
        if (user == null) {
            LOGGER.info("User is not in cache");
            try {
                user = userDAO.findUserByLogin(login);
            } catch (LibraryDAOException e) {
                LOGGER.warn(e);
                throw new LibraryServiceException(e.getMessage(), e);
            }
        }
        return user;
    }

    @Override
    public User findUserById(int id) throws LibraryServiceException {
        try {
            return userDAO.findUserByID(id);
        } catch (LibraryDAOException e) {
            LOGGER.warn(e);
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
            LOGGER.warn(e);
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
            userDAO.findUserByID(userID);
        } catch (LibraryDAOException e) {
            LOGGER.info(e);
            throw new LibraryServiceException(e.getMessage(), e);
        }
    }

    @Override
    public by.batseko.library.service.user.impl.OnlineUsersCache getOnlineUsersCache() {
        return activeUserCache;
    }
}
