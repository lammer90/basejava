package com.urise.webapp.srorage.sql;

import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlHelper {
    public static <T> T getPreparedStatement(ConnectionFactory connectionFactory, String sql, ExecutePreparedStatement<T> executePreparedStatement, String... params){
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            for(int i = 0; i < params.length; i++){
                preparedStatement.setString(i+1, params[i]);
            }
            return executePreparedStatement.execute(preparedStatement);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
