package by.batseko.library.dao;

public class SQLQueriesStorage {
    public static final String REGISTER_USER = "INSERT INTO user " +
            "(email, login, password, first_name, last_name, passport_serial_number, address, phone) " +
            "value (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String FIND_USER_BY_LOGIN = "SELECT * FROM user WHERE login = (?)";
    public static final String FIND_USER_BY_ID = "SELECT * FROM user WHERE id = (?)";
    public static final String FIND_ALL_USERS = "SELECT * FROM user";
    public static final String UPDATE_USER_PROFILE_DATA = "UPDATE user SET email = (?), password = (?), address = (?)," +
            "  phone = (?) WHERE id = (?)";
    public static final String UPDATE_USER_BAN_STATUS = "UPDATE user SET is_banned = (?) WHERE id = (?)";
    public static final String DELETE_USER = "DELETE FROM user WHERE id = (?)";

    public static final String INSERT_BOOK_AUTHOR = "INSERT INTO book_author (uuid, author) value (?, ?)";
    public static final String FIND_BOOK_AUTHOR_BY_UUID = "SELECT * FROM book_author WHERE uuid = (?)";
    public static final String FIND_ALL_BOOK_AUTHORS = "SELECT * FROM book_author";

    public static final String INSERT_BOOK_PUBLISHER = "INSERT INTO book_publisher (uuid, title) value (?, ?)";
    public static final String FIND_BOOK_PUBLISHER_BY_UUID = "SELECT * FROM book_publisher WHERE uuid = (?)";
    public static final String FIND_ALL_BOOK_PUBLISHERS = "SELECT * FROM book_publisher";

    public static final String INSERT_BOOK_GENRE = "INSERT INTO book_genre (uuid, genre) value (?, ?)";
    public static final String FIND_BOOK_GENRE_BY_UUID = "SELECT * FROM book_genre WHERE uuid = (?)";
    public static final String FIND_ALL_BOOK_GENRES = "SELECT * FROM book_genre";

    public static final String INSERT_BOOK_LANGUAGE = "INSERT INTO book_language (uuid, language) value (?, ?)";
    public static final String FIND_BOOK_LANGUAGE_BY_UUID = "SELECT * FROM book_language WHERE uuid = (?)";
    public static final String FIND_ALL_BOOK_LANGUAGES = "SELECT * FROM book_language";

    public static final String INSERT_BOOK = "INSERT INTO book (uuid, genre_uuid, language_uuid, publisher_uuid, author_uuid, " +
            "title, publish_year, pages_quantity, description) value (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String FIND_BOOK_BY_UUID = "SELECT book.*, count(book_instance.uuid) as available_book_quantity " +
            "FROM book LEFT JOIN book_instance ON book.uuid = book_instance.book_uuid " +
            "WHERE book.uuid = (?) AND book_instance.is_available = 1";
    public static final String FIND_ALL_BOOKS = "SELECT * FROM book";

    public static final String INSERT_BOOK_INSTANCE = "INSERT INTO book_instance (book_uuid) value (?)";

    private SQLQueriesStorage() {}
}
