package by.batseko.library.dao.user.impl;

import by.batseko.library.dao.BaseDAO;
import by.batseko.library.dao.SQLQueriesStorage;
import by.batseko.library.dao.user.UserDAO;
import by.batseko.library.entity.user.User;
import by.batseko.library.exception.ConnectionPoolException;
import by.batseko.library.exception.LibraryDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDAOImpl extends BaseDAO implements UserDAO {
    private static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);

    private static final String UNIQUE_LOGIN_MESSAGE = "user.login_UNIQUE";
    private static final String UNIQUE_EMAIL_MESSAGE = "user.email_UNIQUE";

    @Override
    public void registerUser(User user) throws LibraryDAOException {
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.REGISTER_USER)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getFirstName());
            preparedStatement.setString(5, user.getLastName());
            preparedStatement.setString(6, user.getPassportSerialNumber());
            preparedStatement.setString(7, user.getAddress());
            preparedStatement.setString(8, user.getPhoneNumber());
            preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            if(e.getMessage().contains(UNIQUE_LOGIN_MESSAGE)) {
                throw new LibraryDAOException("query.user.registration.emailAlreadyExist", e);
            } else if (e.getMessage().contains(UNIQUE_EMAIL_MESSAGE)) {
                throw new LibraryDAOException("query.user.registration.loginAlreadyExist", e);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("query.user.registration.commonError", e);
        }
    }

    @Override
    public void updateUserProfileData(User user) throws LibraryDAOException {
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.UPDATE_USER_PROFILE_DATA)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getAddress());
            preparedStatement.setString(4, user.getPhoneNumber());
            preparedStatement.setInt(5, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new LibraryDAOException("query.user.registration.emailAlreadyExist", e);
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("service.commonError", e);
        }
    }

    @Override
    public void updateUserBanStatus(User user) throws LibraryDAOException {
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.UPDATE_USER_BAN_STATUS)) {
            preparedStatement.setBoolean(1, user.getBanned());
            preparedStatement.setInt(2, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("service.commonError", e);
        }
    }

    @Override
    public void setRememberUserToken(int userId, String userToken) throws LibraryDAOException {
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.UPDATE_USER_LOG_IN_TOKEN_BY_ID)) {
            preparedStatement.setString(1, userToken);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("service.commonError", e);
        }
    }

    @Override
    public void setRememberUserToken(String userEmail, String userToken) throws LibraryDAOException {
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.UPDATE_USER_LOG_IN_TOKEN_BY_EMAIL)) {
            preparedStatement.setString(1, userToken);
            preparedStatement.setString(2, userEmail);
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("service.commonError", e);
        }
    }

    @Override
    public void deleteRememberUserToken(int userId) throws LibraryDAOException {
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.UPDATE_USER_LOG_IN_TOKEN_TO_NULL)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("service.commonError", e);
        }
    }

    @Override
    public User findUserByLogin(String userLogin) throws LibraryDAOException {
        ResultSet resultSet = null;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_USER_BY_LOGIN)) {
            preparedStatement.setString(1, userLogin);
            resultSet = preparedStatement.executeQuery();
            return extractFoundedUserFromResultSet(resultSet);
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("service.commonError", e);
        } finally {
            closeResultSet(resultSet);
        }
    }

    @Override
    public User findUserByEmail(String userEmail) throws LibraryDAOException {
        ResultSet resultSet = null;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_USER_BY_EMAIL)) {
            preparedStatement.setString(1, userEmail);
            resultSet = preparedStatement.executeQuery();
            return extractFoundedUserFromResultSet(resultSet);
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("service.commonError", e);
        } finally {
            closeResultSet(resultSet);
        }
    }

    @Override
    public User findUserByIdAndToken(int userId, String token) throws LibraryDAOException {
        ResultSet resultSet = null;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_USER_BY_ID_AND_TOKEN)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, token);
            resultSet = preparedStatement.executeQuery();
            return extractFoundedUserFromResultSet(resultSet);
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("service.commonError", e);
        } finally {
            closeResultSet(resultSet);
        }
    }

    @Override
    public User findUserById(int userId) throws LibraryDAOException {
        ResultSet resultSet = null;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_USER_BY_ID)) {
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            return extractFoundedUserFromResultSet(resultSet);
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("service.commonError", e);
        } finally {
            closeResultSet(resultSet);
        }
    }

    @Override
    public List<User> findAllUsers() throws LibraryDAOException {
        List<User> users;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.FIND_ALL_USERS);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            if (!resultSet.isBeforeFirst()) {
                users = Collections.emptyList();
            } else {
                resultSet.last();
                int listSize = resultSet.getRow();
                resultSet.beforeFirst();
                users = new ArrayList<>(listSize);
                while (resultSet.next()) {
                    User user = constructUserByResultSet(resultSet);
                    LOGGER.info(user);
                    users.add(user);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("service.commonError", e);
        }
        return users;
    }

    @Override
    public void deleteUserById(int userId) throws LibraryDAOException {
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.DELETE_USER)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new LibraryDAOException("query.user.deleteUser.commonError");
        }
    }

    private User extractFoundedUserFromResultSet(ResultSet resultSet) throws SQLException, LibraryDAOException {
        if (resultSet.next()) {
            return constructUserByResultSet(resultSet);
        } else {
            throw new LibraryDAOException("query.user.getUser.userNotFound");
        }
    }
}
