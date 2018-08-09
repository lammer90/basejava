package com.urise.webapp.srorage.sql;

import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlHelper {
    public static PreparedStatement getPreparedStatement(ConnectionFactory connectionFactory, String sql, String... params){
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            for(int i = 1; i <= params.length; i++){
                preparedStatement.setString(i, params[i]);
            }
            return preparedStatement;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
