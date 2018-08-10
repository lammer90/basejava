package com.urise.webapp.srorage.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface SqlTransactExecutor<T> {
    T execute(Connection connection) throws SQLException;
}
