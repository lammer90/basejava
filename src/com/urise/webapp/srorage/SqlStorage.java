package com.urise.webapp.srorage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.srorage.sql.ConnectionFactory;
import com.urise.webapp.srorage.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final ConnectionFactory connectionFactory;
    private ExecutePreparedStatement executePreparedStatement;
    private ExecutePreparedStatement executeQueryPreparedStatement;

    public SqlStorage(String url, String user, String password) {
        this.connectionFactory = () -> {
            try {
                return DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                throw new StorageException(e);
            }
        };

        executePreparedStatement = preparedStatement -> {
            try {
                return new ExecuteResult(preparedStatement.execute(), null);
            } catch (SQLException e) {
                throw new StorageException(e);
            }
        };

        executeQueryPreparedStatement = preparedStatement -> {
            try {
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    return new ExecuteResult(false, resultSet);
                }
                return new ExecuteResult(true, resultSet);
            } catch (SQLException e) {
                throw new StorageException(e);
            }
        };
    }

    @Override
    public void clear() {
        executePreparedStatement.execute(SqlHelper.getPreparedStatement(connectionFactory, "DELETE FROM resume"));
    }


    @Override
    public void update(Resume r) {
        ExecuteResult exRes = executePreparedStatement.execute(SqlHelper.getPreparedStatement(connectionFactory, "UPDATE resume r SET r.full_name =? WHERE r.uuid =?", r.getFullname(), r.getUuid()));
        if (!exRes.isResult()) {
            throw new NotExistStorageException(r.getUuid());
        }
    }

    @Override
    public void save(Resume r) {
        ExecuteResult exRes = executePreparedStatement.execute(SqlHelper.getPreparedStatement(connectionFactory, "INSERT INTO resume (uuid, full_name) VALUES (?, ?)", r.getUuid(), r.getFullname()));
        if (!exRes.isResult()) {
            throw new ExistStorageException(r.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        ExecuteResult exRes = executeQueryPreparedStatement.execute(SqlHelper.getPreparedStatement(connectionFactory, "SELECT * FROM resume r WHERE r.uuid=?", uuid));
        if (!exRes.isResult()) {
            throw new NotExistStorageException(uuid);
        }
        try {
            return new Resume(exRes.getResultSet().getString("full_name"), uuid);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void delete(String uuid) {
        ExecuteResult exRes = executePreparedStatement.execute(SqlHelper.getPreparedStatement(connectionFactory, "DELETE FROM resume r WHERE r.uuid=?", uuid));
        if (!exRes.isResult()) {
            throw new ExistStorageException(uuid);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        return null;
    }

    @Override
    public int size() {
        ExecuteResult exRes = executeQueryPreparedStatement.execute(SqlHelper.getPreparedStatement(connectionFactory, "SELECT * FROM resume"));
        if (!exRes.isResult()) {
            return 0;
        }
        return 0;
        //while (exRes.getResultSet().next()){

        //}
    }

    private interface ExecutePreparedStatement {
        ExecuteResult execute(PreparedStatement PreparedStatement);
    }

    private class ExecuteResult {
        private ResultSet resultSet;
        private boolean result;

        public ExecuteResult(boolean result, ResultSet resultSet) {
            this.resultSet = resultSet;
            this.result = result;
        }

        public ResultSet getResultSet() {
            return resultSet;
        }

        public boolean isResult() {
            return result;
        }
    }
}
