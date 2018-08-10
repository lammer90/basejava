package com.urise.webapp.srorage.sql;

import com.urise.webapp.srorage.SqlStorage;

import java.sql.PreparedStatement;

public interface ExecutePreparedStatement<T> {
    T execute(PreparedStatement preparedStatement, GetResumeInterface getResumeInterface);
}
