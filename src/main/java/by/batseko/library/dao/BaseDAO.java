package by.batseko.library.dao;

import by.batseko.library.exception.LibraryDAOException;
import by.batseko.library.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseDAO {
    private static final Logger LOGGER = LogManager.getLogger(BaseDAO.class);

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
}
