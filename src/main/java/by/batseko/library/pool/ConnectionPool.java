package by.batseko.library.pool;

import by.batseko.library.exception.ConnectionPoolException;
import by.batseko.library.exception.ValidatorException;
import by.batseko.library.factory.ValidatorFactory;
import by.batseko.library.validatior.PoolValidator;
import com.mysql.cj.jdbc.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    private static final String DB_CONNECTION_POOL_PROPERTIES = "ConnectionPool.properties";
    private static final String URL_PROPERTY_NAME = "url";
    private static final String POOL_SIZE_PROPERTY_NAME = "poolSize";
    private static final String CONNECTION_AWAITING_TIMEOUT_PROPERTY_NAME = "connectionAwaitingTimeout";
    private static final int DEFAULT_POOL_SIZE = 10;
    private static final int DEFAULT_CONNECTION_AWAITING_TIMEOUT = 30;

    private final LinkedBlockingQueue<Connection> availableConnections;
    private final List<Connection> usedConnections;
    private final AtomicBoolean isInitialized;
    private final AtomicBoolean isPoolClosing;
    private final PoolValidator poolValidator;
    private final Lock initLock;
    private final Properties jdbcMysqlConfigProperties;

    private String url;

    private int poolSize;
    private int connectionAwaitingTimeout;

    private ConnectionPool() {
        availableConnections = new LinkedBlockingQueue<>();
        usedConnections = new LinkedList<>();
        poolValidator = ValidatorFactory.getInstance().getPoolValidator();
        isInitialized = new AtomicBoolean(false);
        isPoolClosing = new AtomicBoolean(false);
        initLock = new ReentrantLock();
        jdbcMysqlConfigProperties = new Properties();
    }

    private static class ConnectionPoolSingletonHolder {
        static final ConnectionPool INSTANCE = new ConnectionPool();
    }

    public static ConnectionPool getInstance() {
        return ConnectionPoolSingletonHolder.INSTANCE;
    }

    public void init() throws ConnectionPoolException {
        initLock.lock();
        if (!isInitialized.get()) {
            try {
                Driver jdbcMySQLDriver = new Driver();
                DriverManager.registerDriver(jdbcMySQLDriver);
                initProperties(DB_CONNECTION_POOL_PROPERTIES);
                createConnections(poolSize);
                isInitialized.set(true);
            } catch (SQLException e) {
                throw new ConnectionPoolException("Connection pool is not initialized ", e);
            } finally {
                initLock.unlock();
            }
        }
    }

    public Connection getConnection() throws ConnectionPoolException {
        if (!isPoolClosing.get()) {
            Connection connection = null;
            if (!availableConnections.isEmpty()) {
                try {
                    connection = availableConnections.poll(connectionAwaitingTimeout, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    LOGGER.error("Can't get connection", e);
                    Thread.currentThread().interrupt();
                }
                usedConnections.add(connection);
            }
            return connection;
        }
        throw new ConnectionPoolException("Connections are closing now");
    }

    public void destroy() {
        isPoolClosing.set(true);
        initLock.lock();
        for (Connection connection : availableConnections) {
            closeConnection((ProxyConnection) connection);
        }
        for (Connection connection : usedConnections) {
            closeConnection((ProxyConnection) connection);
        }
        availableConnections.clear();
        usedConnections.clear();
        Enumeration<java.sql.Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = (Driver) drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                LOGGER.warn(String.format("Error to deregister driver %s", driver), e);
            }
        }
        isInitialized.set(false);
        isPoolClosing.set(false);
        initLock.unlock();
    }

    private void closeConnection(ProxyConnection connection) {
        try {
            connection.forceClose();
        } catch (SQLException e) {
            LOGGER.warn("Can't close connection", e);
        }
    }

    void releaseConnection(Connection connection) {
        if (connection != null) {
            usedConnections.remove(connection);
            availableConnections.add(connection);
        } else {
            LOGGER.warn("Attempted to release null connection");
        }
    }

    private void createConnections(int connectionsCount) {
        for (int i = 0; i < connectionsCount; i++) {
            try {
                ProxyConnection proxyConnection = new ProxyConnection(DriverManager.getConnection(url, jdbcMysqlConfigProperties));
                availableConnections.add(proxyConnection);
            } catch (SQLException e) {
                LOGGER.warn("Connection not created", e);
            }
        }
    }

    private void initProperties(String propertiesFileName) throws ConnectionPoolException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFileName)) {
            jdbcMysqlConfigProperties.load(inputStream);
            initDBPoolSettings(jdbcMysqlConfigProperties);
            initSizeAndTimeoutPoolSettings(jdbcMysqlConfigProperties);
        } catch (IOException e) {
            throw new ConnectionPoolException("DB properties has not been loaded ", e);
        }
    }

    private void initDBPoolSettings(Properties properties) throws ConnectionPoolException {
        try {
            url = properties.getProperty(URL_PROPERTY_NAME);
            poolValidator.checkContainsNull(properties);
        } catch (ValidatorException e) {
            throw new ConnectionPoolException("Invalid primary DB properties ", e);
        }
    }

    private void initSizeAndTimeoutPoolSettings(Properties properties) {
        try {
            poolSize = Integer.parseInt(properties.getProperty(POOL_SIZE_PROPERTY_NAME));
            connectionAwaitingTimeout = Integer.parseInt(properties.getProperty(CONNECTION_AWAITING_TIMEOUT_PROPERTY_NAME));
            if (poolValidator.isLessOrEqualsZero(poolSize)) {
                poolSize = DEFAULT_POOL_SIZE;
                LOGGER.warn("Invalid pool size in property settings");
            }
            if (poolValidator.isLessOrEqualsZero(connectionAwaitingTimeout)) {
                connectionAwaitingTimeout = DEFAULT_CONNECTION_AWAITING_TIMEOUT;
                LOGGER.warn("Invalid connection awaiting timeout in property settings");
            }
        } catch (NumberFormatException e) {
            poolSize = DEFAULT_POOL_SIZE;
            connectionAwaitingTimeout = DEFAULT_CONNECTION_AWAITING_TIMEOUT;
            LOGGER.warn("Unparseable property field", e);
        }
    }
}
