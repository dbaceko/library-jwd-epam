package by.batseko.library.pool;

import by.batseko.library.exception.ConnectionPoolException;
import by.batseko.library.exception.ConnectionPoolInitializationRuntimeException;
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

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    private static final String DB_CONNECTION_POOL_PROPERTIES = "ConnectionPool.properties";
    private static final String URL_PROPERTY_NAME = "url";
    private static final int DEFAULT_POOL_SIZE = 10;
    private static final int DEFAULT_CONNECTION_AWAITING_TIMEOUT = 30;

    private final LinkedBlockingQueue<Connection> availableConnections;
    private final List<Connection> usedConnections;
    private final AtomicBoolean isPoolClosing;
    private final Properties jdbcMysqlConfigProperties;

    private String url;

    private ConnectionPool() {
        availableConnections = new LinkedBlockingQueue<>();
        usedConnections = new LinkedList<>();
        isPoolClosing = new AtomicBoolean(false);
        jdbcMysqlConfigProperties = new Properties();
        init();
    }

    private static class ConnectionPoolSingletonHolder {
        static final ConnectionPool INSTANCE = new ConnectionPool();
    }

    public static ConnectionPool getInstance() {
        return ConnectionPoolSingletonHolder.INSTANCE;
    }

    private void init() {
        try {
            DriverManager.registerDriver(new Driver());
            initProperties(DB_CONNECTION_POOL_PROPERTIES);
            createConnections(DEFAULT_POOL_SIZE);
        } catch (ConnectionPoolException | SQLException e) {
            LOGGER.fatal("Connection pool is not initialized ", e);
            throw new ConnectionPoolInitializationRuntimeException("Connection pool is not initialized ", e);
        }
    }

    public Connection getConnection() throws ConnectionPoolException {
        if (!isPoolClosing.get()) {
            Connection connection = null;
            if (!availableConnections.isEmpty()) {
                try {
                    connection = availableConnections.poll(DEFAULT_CONNECTION_AWAITING_TIMEOUT, TimeUnit.SECONDS);
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
        isPoolClosing.set(false);
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
                proxyConnection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                availableConnections.add(proxyConnection);
            } catch (SQLException e) {
                LOGGER.warn("Connection not created", e);
            }
        }
    }

    private void initProperties(String propertiesFileName) throws ConnectionPoolException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFileName)) {
            jdbcMysqlConfigProperties.load(inputStream);
            if (jdbcMysqlConfigProperties.isEmpty()) {
                throw new ConnectionPoolException("DB properties has not been loaded");
            }
            url = jdbcMysqlConfigProperties.getProperty(URL_PROPERTY_NAME);
        } catch (IOException e) {
            throw new ConnectionPoolException("DB properties has not been loaded ", e);
        }
    }
}
