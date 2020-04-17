package by.batseko.library.dao;

public class SQLQueriesStorage {

    public static final String REGISTER_USER = "INSERT INTO user " +
            "(email, login, password, first_name, last_name, passport_serial_number, address, phone) " +
            "value (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String GET_USER_BY_LOGIN = "SELECT * FROM user WHERE login = (?)";
    public static final String GET_USER_BY_ID = "SELECT * FROM user WHERE id = (?)";
    public static final String UPDATE_USER_PROFILE_DATA = "UPDATE user SET email = (?), password = (?), address = (?)," +
            "  phone = (?) WHERE id = (?)";
    public static final String UPDATE_USER_BAN_STATUS = "UPDATE user SET is_banned = (?) WHERE id = (?)";
    public static final String DELETE_USER = "DELETE FROM user WHERE id = (?)";

    private SQLQueriesStorage() {}
}
