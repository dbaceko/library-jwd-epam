package by.batseko.library.dao.impl;

import by.batseko.library.builder.user.UserBuilder;
import by.batseko.library.dao.AbstractSQLLayer;
import by.batseko.library.dao.SQLQueriesStorage;
import by.batseko.library.dao.UserDAO;
import by.batseko.library.entity.Role;
import by.batseko.library.entity.User;
import by.batseko.library.exception.ConnectionPoolException;
import by.batseko.library.exception.EnumCastException;
import by.batseko.library.exception.LibraryDAOException;
import by.batseko.library.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class UserDAOImpl extends AbstractSQLLayer implements UserDAO {
    private static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);

    private static final String UNIQUE_LOGIN_MESSAGE = "user.login_UNIQUE";
    private static final String UNIQUE_EMAIL_MESSAGE = "user.email_UNIQUE";

    private final ConnectionPool pool;

    public UserDAOImpl(){
        pool = ConnectionPool.getInstance();
    }

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
            LOGGER.debug(e);
            if(e.getMessage().contains(UNIQUE_LOGIN_MESSAGE)) {
                throw new LibraryDAOException("query.registration.emailAlreadyExist");
            } else if (e.getMessage().contains(UNIQUE_EMAIL_MESSAGE)) {
                throw new LibraryDAOException("query.registration.loginAlreadyExist");
            }
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.warn(e);
            throw new LibraryDAOException("query.registration.commonError");
        }
    }

    @Override
    public void updateUser(User user) throws LibraryDAOException {
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.UPDATE_USER)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getAddress());
            preparedStatement.setString(4, user.getPhoneNumber());
            preparedStatement.setInt(5, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            LOGGER.debug(e);
            if(e.getMessage().contains(UNIQUE_LOGIN_MESSAGE)) {
                throw new LibraryDAOException("query.registration.emailAlreadyExist");
            } else if (e.getMessage().contains(UNIQUE_EMAIL_MESSAGE)) {
                throw new LibraryDAOException("query.registration.loginAlreadyExist");
            }
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.warn(e);
            throw new LibraryDAOException("query.registration.commonError");
        }
    }

    @Override
    public User findUserByLogin(String userLogin) throws LibraryDAOException {
        ResultSet resultSet = null;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.GET_USER_BY_LOGIN))
        {
            preparedStatement.setString(1, userLogin);
            resultSet = preparedStatement.executeQuery();
            User user = constructUserByResultSet(resultSet);
            if (user == null) {
                LOGGER.debug(String.format("User not found by login %s", userLogin));
                throw new LibraryDAOException("query.getUser.userNotFound");
            }
            return user;
        } catch (SQLException | EnumCastException | ConnectionPoolException e) {
            LOGGER.warn(e);
            throw new LibraryDAOException("query.getUser.commonError", e);
        } finally {
            closeResultSet(resultSet);
        }
    }

    @Override
    public User findUserByID(int userID) throws LibraryDAOException{
        ResultSet resultSet = null;
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.GET_USER_BY_ID))
        {
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();
            User user = constructUserByResultSet(resultSet);
            if (user == null) {
                LOGGER.debug(String.format("User not found by id %d", userID));
                throw new LibraryDAOException("query.getUser.userNotFound");
            }
            return user;
        } catch (SQLException | EnumCastException | ConnectionPoolException e) {
            LOGGER.warn(e);
            throw new LibraryDAOException("query.getUser.commonError", e);
        } finally {
            closeResultSet(resultSet);
        }
    }

    @Override
    public void deleteUserByID(int userID) throws LibraryDAOException {
        try(Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQueriesStorage.DELETE_USER)) {
            preparedStatement.setInt(1, userID);
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.warn(e);
            throw new LibraryDAOException("query.deleteUser.commonError");
        }
    }

    private User constructUserByResultSet(ResultSet resultSet) throws SQLException, EnumCastException {
        User user = null;
        if (resultSet.next()) {
            user = new UserBuilder().setId(resultSet.getInt(1))
                    .setRole(Role.getRoleById(resultSet.getInt(2)))
                    .setEmail(resultSet.getString(3))
                    .setLogin(resultSet.getString(4))
                    .setPassword(resultSet.getString(5))
                    .setFirstName(resultSet.getNString(6))
                    .setLastName(resultSet.getNString(7))
                    .setPassportSerialNumber(resultSet.getString(8))
                    .setAddress(resultSet.getNString(9))
                    .setPhoneNumber(resultSet.getString(10))
                    .setBanned(resultSet.getBoolean(11))
                    .setRegistrationDate(resultSet.getTimestamp(12))
                    .build();
        }
        return user;
    }
}
