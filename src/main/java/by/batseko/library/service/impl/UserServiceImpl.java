package by.batseko.library.service.impl;

import by.batseko.library.dao.UserDAO;
import by.batseko.library.entity.User;
import by.batseko.library.exception.EncryptionException;
import by.batseko.library.exception.LibraryDAOException;
import by.batseko.library.exception.LibraryServiceException;
import by.batseko.library.exception.ValidatorException;
import by.batseko.library.factory.DAOFactory;
import by.batseko.library.factory.UtilFactory;
import by.batseko.library.factory.ValidatorFactory;
import by.batseko.library.service.UserService;
import by.batseko.library.util.Encryption;
import by.batseko.library.validatior.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private final UserValidator validator;
    private final Encryption encryption;
    private final ActiveUsersCache activeUserCache;
    private final UserDAO userDAO;

    public UserServiceImpl(){
        validator = ValidatorFactory.getInstance().getUserValidator();
        encryption = UtilFactory.getInstance().getEncryption();
        activeUserCache = ActiveUsersCache.getInstance();
        userDAO = DAOFactory.getInstance().getUserDAO();
    }

    @Override
    public User logIn(String login, String password) throws LibraryServiceException {
        try {
            User user = userDAO.getUserByLogin(login);
            if (encryption.validatePassword(password, user.getPassword())) {
                activeUserCache.put(user.getLogin(), user);
                return user;
            } else {
                LOGGER.info("Password dont match");
                throw new LibraryServiceException("validation.login.incorrect");
            }
        } catch (EncryptionException e) {
            throw new LibraryServiceException("service.commonError");
        } catch (LibraryDAOException e) {
            LOGGER.info(e);
            throw new LibraryServiceException(e.getMessage());
        }
    }

    @Override
    public void logOut(String user) {
        activeUserCache.remove(user);
    }

    @Override
    public User getUserByLogin(String login) throws LibraryServiceException {
        User user = null;
        try {
            user = activeUserCache.get(login);
        } catch (LibraryServiceException e) {
            LOGGER.warn(e);
        }
        if (user == null) {
            LOGGER.warn("User is not in cache");
            try {
                user = userDAO.getUserByLogin(login);
            } catch (LibraryDAOException e) {
                throw new LibraryServiceException(e.getMessage());
            }
        }
        return user;
    }

    @Override
    public User getUserByID(int id) throws LibraryServiceException {
        try {
            return userDAO.getUserByID(id);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage());
        }
    }

    @Override
    public void registerUser(User user) throws LibraryServiceException {
        try {
            validator.validateNewUser(user);
        } catch (ValidatorException e) {
            LOGGER.info(String.format("invalid %s %n registration data %s", user.toString(), e.getMessage()));
            throw new LibraryServiceException(e.getMessage());
        }
        try {
            user.setPassword(encryption.generateHash(user.getPassword()));
            userDAO.registerUser(user);
        } catch (EncryptionException e) {
            throw new LibraryServiceException("service.commonError");
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage());
        }
    }

    @Override
    public void updateUser(User user) throws LibraryServiceException {
        try {
            validator.validateUpdatedUser(user);
        } catch (ValidatorException e) {
            LOGGER.info(String.format("invalid %s %n registration data %s", user.toString(), e.getMessage()));
            throw new LibraryServiceException(e.getMessage());
        }
        try {
            user.setPassword(encryption.generateHash(user.getPassword()));
            userDAO.updateUser(user);
        } catch (EncryptionException | LibraryDAOException e) {
            throw new LibraryServiceException(e);
        }
    }

    @Override
    public void deleteUserByID(int userID) throws LibraryServiceException {
        try {
            userDAO.getUserByID(userID);
        } catch (LibraryDAOException e) {
            throw new LibraryServiceException(e.getMessage());
        }
    }

    @Override
    public ActiveUsersCache getActiveUsersCache() {
        return activeUserCache;
    }
}
