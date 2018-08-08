package com.urise.webapp.srorage.sql;

import java.sql.Connection;

public interface ConnectionFactory {
    Connection getConnection();
}
