package by.batseko.library.dao;

import by.batseko.library.builder.BookBuilder;
import by.batseko.library.builder.BookOrderBuilder;
import by.batseko.library.builder.UserBuilder;
import by.batseko.library.entity.book.*;
import by.batseko.library.entity.book.bookcomponent.Author;
import by.batseko.library.entity.book.bookcomponent.BookLanguage;
import by.batseko.library.entity.book.bookcomponent.Genre;
import by.batseko.library.entity.book.bookcomponent.Publisher;
import by.batseko.library.entity.order.BookOrder;
import by.batseko.library.entity.order.OrderStatus;
import by.batseko.library.entity.order.OrderType;
import by.batseko.library.entity.user.User;
import by.batseko.library.entity.user.UserRole;
import by.batseko.library.exception.LibraryDAOException;
import by.batseko.library.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseDAO {
    private static final Logger LOGGER = LogManager.getLogger(BaseDAO.class);

    private static final String USER_ID_COLUMN_NAME = "user.id";
    private static final String USER_ROLE_ID_COLUMN_NAME = "user.role_id";
    private static final String USER_EMAIL_COLUMN_NAME = "user.email";
    private static final String USER_LOGIN_COLUMN_NAME = "user.login";
    private static final String USER_PASSWORD_COLUMN_NAME = "user.password";
    private static final String USER_FIRSTNAME_COLUMN_NAME = "user.firstname";
    private static final String USER_LASTNAME_COLUMN_NAME = "user.lastname";
    private static final String USER_PASSPORT_SN_COLUMN_NAME = "user.passport_serial_number";
    private static final String USER_ADDRESS_COLUMN_NAME = "user.address";
    private static final String USER_PHONE_COLUMN_NAME = "user.phone";
    private static final String USER_BAN_STATUS_COLUMN_NAME = "user.is_banned";
    private static final String USER_REGISTRATION_DATE_COLUMN_NAME = "user.registration_date";

    private static final String BOOK_AUTHOR_UUID_COLUMN_NAME = "book_author.uuid";
    private static final String BOOK_AUTHOR_COLUMN_NAME = "book_author.author";

    private static final String BOOK_GENRE_UUID_COLUMN_NAME = "book_genre.uuid";
    private static final String BOOK_GENRE_GENRE_COLUMN_NAME = "book_genre.genre";

    private static final String BOOK_LANGUAGE_UUID_COLUMN_NAME = "book_language.uuid";
    private static final String BOOK_LANGUAGE_COLUMN_NAME = "book_language.language";

    private static final String BOOK_PUBLISHER_UUID_COLUMN_NAME = "book_publisher.uuid";
    private static final String BOOK_PUBLISHER_COLUMN_NAME = "book_publisher.title";

    private static final String BOOK_UUID_COLUMN_NAME = "book.uuid";
    private static final String BOOK_TITLE_COLUMN_NAME = "book.title";
    private static final String BOOK_PUBLISH_YEAR_COLUMN_NAME = "book.publish_year";
    private static final String BOOK_PAGES_QUANTITY_COLUMN_NAME = "book.pages_quantity";
    private static final String BOOK_PAGES_DESCRIPTION_COLUMN_NAME = "book.description";

    private static final String ORDER_UUID_COLUMN_NAME = "order.uuid";
    private static final String ORDER_ORDER_TYPE_ID_COLUMN_NAME = "order.order_type_id";
    private static final String ORDER_ORDER_STATUS_ID_COLUMN_NAME = "order.order_status_id";
    private static final String ORDER_DATE_ID_COLUMN_NAME = "order.date";

    private static final String BOOK_INSTANCE_UUID_COLUMN_NAME = "book_instance.uuid";
    private static final String BOOK_INSTANCE_IS_AVAILABLE_COLUMN_NAME = "book_instance.is_available";

    protected final ConnectionPool pool;

    protected BaseDAO(){
        pool = ConnectionPool.getInstance();
    }

    protected void closeResultSet(ResultSet resultSet) throws LibraryDAOException {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.error("Can`t close resultSet", e);
                throw new LibraryDAOException("service.commonError", e);
            }
        }
    }

    protected void connectionCommitChanges(Connection connection) throws LibraryDAOException {
        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException e) {
                LOGGER.error("Can`t commit connection's changes", e);
                throw new LibraryDAOException("service.commonError", e);
            }
        }
    }

    protected void connectionSetAutoCommit(Connection connection, boolean value) throws LibraryDAOException {
        if (connection != null) {
            try {
                connection.setAutoCommit(value);
            } catch (SQLException e) {
                LOGGER.error("Can`t set autocommmit to connection", e);
                throw new LibraryDAOException("service.commonError", e);
            }
        }
    }

    protected void connectionsRollback(Connection connection) throws LibraryDAOException {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                LOGGER.error("Can`t rollback connection result", e);
                throw new LibraryDAOException("service.commonError", e);
            }
        }
    }

    protected User constructUserByResultSet(ResultSet resultSet) throws SQLException {
        return new UserBuilder().setId(resultSet.getInt(USER_ID_COLUMN_NAME))
                .setUserRole(UserRole.getRoleById(resultSet.getInt(USER_ROLE_ID_COLUMN_NAME)))
                .setEmail(resultSet.getString(USER_EMAIL_COLUMN_NAME))
                .setLogin(resultSet.getString(USER_LOGIN_COLUMN_NAME))
                .setPassword(resultSet.getString(USER_PASSWORD_COLUMN_NAME))
                .setFirstName(resultSet.getNString(USER_FIRSTNAME_COLUMN_NAME))
                .setLastName(resultSet.getNString(USER_LASTNAME_COLUMN_NAME))
                .setPassportSerialNumber(resultSet.getString(USER_PASSPORT_SN_COLUMN_NAME))
                .setAddress(resultSet.getNString(USER_ADDRESS_COLUMN_NAME))
                .setPhoneNumber(resultSet.getString(USER_PHONE_COLUMN_NAME))
                .setBanned(resultSet.getBoolean(USER_BAN_STATUS_COLUMN_NAME))
                .setRegistrationDate(resultSet.getTimestamp(USER_REGISTRATION_DATE_COLUMN_NAME))
                .build();
    }

    protected Author constructAuthorByResultSet(ResultSet resultSet) throws SQLException {
        Author author = new Author();
        author.setUuid(resultSet.getString(BOOK_AUTHOR_UUID_COLUMN_NAME));
        author.setAuthorName(resultSet.getString(BOOK_AUTHOR_COLUMN_NAME));
        return author;
    }

    protected Genre constructGenreByResultSet(ResultSet resultSet) throws SQLException {
        Genre genre = new Genre();
        genre.setUuid(resultSet.getString(BOOK_GENRE_UUID_COLUMN_NAME));
        genre.setGenreTitle(resultSet.getString(BOOK_GENRE_GENRE_COLUMN_NAME));
        return genre;
    }

    protected BookLanguage constructBookLanguageByResultSet(ResultSet resultSet) throws SQLException {
        BookLanguage bookLanguage = new BookLanguage();
        bookLanguage.setUuid(resultSet.getString(BOOK_LANGUAGE_UUID_COLUMN_NAME));
        bookLanguage.setLanguageTitle(resultSet.getString(BOOK_LANGUAGE_COLUMN_NAME));
        return bookLanguage;
    }

    protected Publisher constructPublisherByResultSet(ResultSet resultSet) throws SQLException {
        Publisher publisher = new Publisher();
        publisher.setUuid(resultSet.getString(BOOK_PUBLISHER_UUID_COLUMN_NAME));
        publisher.setPublisherTitle(resultSet.getString(BOOK_PUBLISHER_COLUMN_NAME));
        return publisher;
    }

    protected Book constructBookByResultSet(ResultSet resultSet) throws SQLException {
        return new BookBuilder().setUuid(resultSet.getString(BOOK_UUID_COLUMN_NAME))
                .setGenre(constructGenreByResultSet(resultSet))
                .setBookLanguage(constructBookLanguageByResultSet(resultSet))
                .setPublisher(constructPublisherByResultSet(resultSet))
                .setAuthor(constructAuthorByResultSet(resultSet))
                .setTitle(resultSet.getString(BOOK_TITLE_COLUMN_NAME))
                .setPublishYear(resultSet.getInt(BOOK_PUBLISH_YEAR_COLUMN_NAME))
                .setPagesQuantity(resultSet.getInt(BOOK_PAGES_QUANTITY_COLUMN_NAME))
                .setDescription(resultSet.getString(BOOK_PAGES_DESCRIPTION_COLUMN_NAME))
                .build();
    }


    protected BookOrder constructOrderByResultSet(ResultSet resultSet) throws SQLException {
        BookInstance bookInstance = new BookInstance();
        bookInstance.setAvailable(resultSet.getBoolean(BOOK_INSTANCE_IS_AVAILABLE_COLUMN_NAME));
        bookInstance.setUuid(resultSet.getString(BOOK_INSTANCE_UUID_COLUMN_NAME));
        bookInstance.setBook(constructBookByResultSet(resultSet));
        return new BookOrderBuilder().setUuid(resultSet.getString(ORDER_UUID_COLUMN_NAME))
                .setUser(new UserBuilder()
                        .setId(resultSet.getInt(USER_ID_COLUMN_NAME))
                        .setLogin(resultSet.getString(USER_LOGIN_COLUMN_NAME))
                        .build()
                )
                .setBookInstance(bookInstance)
                .setOrderType(OrderType.getOrderTypeById(resultSet.getInt(ORDER_ORDER_TYPE_ID_COLUMN_NAME)))
                .setOrderStatus(OrderStatus.getOrderStatusById(resultSet.getInt(ORDER_ORDER_STATUS_ID_COLUMN_NAME)))
                .setData(resultSet.getTimestamp(ORDER_DATE_ID_COLUMN_NAME))
                .build();
    }
}
