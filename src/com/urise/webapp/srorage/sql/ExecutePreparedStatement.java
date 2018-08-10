package com.urise.webapp.srorage.sql;

import java.sql.PreparedStatement;

public interface ExecutePreparedStatement<T> {
    T execute(PreparedStatement PreparedStatement);
}
