package by.batseko.library.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseDAO {
    private static final Logger LOGGER = LogManager.getLogger(BaseDAO.class);

    protected void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.error("Can`t close resultSet", e);
            }
        }
    }

    protected int parseBooleanToInt(boolean b) {
        return b ? 1 : 0;
    }
}
