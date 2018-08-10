package com.urise.webapp.srorage.sql;

import java.sql.PreparedStatement;

public interface ExecutePreparedStatement {
    ExecuteResult execute(PreparedStatement PreparedStatement);
}
