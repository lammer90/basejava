package com.urise.webapp.srorage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.srorage.sql.ConnectionFactory;
import com.urise.webapp.srorage.sql.ExecutePreparedStatement;
import com.urise.webapp.srorage.sql.ExecuteResult;
import com.urise.webapp.srorage.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final ConnectionFactory connectionFactory;
    ExecutePreparedStatement executePreparedStatement = preparedStatement -> {
        try {
            return new ExecuteResult(preparedStatement.executeUpdate(), null);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    };

    ExecutePreparedStatement executeQueryPreparedStatement = preparedStatement -> {
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            return new ExecuteResult(1, getNewResume(resultSet));
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    };

    public SqlStorage(String url, String user, String password) {
        this.connectionFactory = () -> {
            try {
                return DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                throw new StorageException(e);
            }
        };
    }

    @Override
    public void clear() {
        SqlHelper.getPreparedStatement(connectionFactory, "DELETE FROM resume", executePreparedStatement);
    }


    @Override
    public void update(Resume r) {
        ExecuteResult exRes = SqlHelper.getPreparedStatement(connectionFactory, "UPDATE resume SET full_name =? WHERE uuid =?", executePreparedStatement, r.getFullname(), r.getUuid());
        if (exRes.isResult() == 0) {
            throw new NotExistStorageException(r.getUuid());
        }
    }

    @Override
    public void save(Resume r) {
        ExecuteResult exRes = SqlHelper.getPreparedStatement(connectionFactory, "SELECT * FROM resume r WHERE r.uuid=?", executeQueryPreparedStatement, r.getUuid());
        if (exRes.getResultSet().size() != 0) {
            throw new ExistStorageException(r.getUuid());
        }
        SqlHelper.getPreparedStatement(connectionFactory, "INSERT INTO resume (uuid, full_name) VALUES (?, ?)", executePreparedStatement, r.getUuid(), r.getFullname());
    }

    @Override
    public Resume get(String uuid) {
        ExecuteResult exRes = SqlHelper.getPreparedStatement(connectionFactory, "SELECT * FROM resume r WHERE r.uuid=?", executeQueryPreparedStatement, uuid);
        if (exRes.getResultSet().size() == 0) {
            throw new NotExistStorageException(uuid);
        }
        return exRes.getResultSet().get(0);
    }

    @Override
    public void delete(String uuid) {
        ExecuteResult exRes = SqlHelper.getPreparedStatement(connectionFactory, "DELETE FROM resume r WHERE r.uuid=?", executePreparedStatement, uuid);
        if (exRes.isResult() == 0) {
            throw new ExistStorageException(uuid);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        ExecuteResult exRes = SqlHelper.getPreparedStatement(connectionFactory, "SELECT * FROM resume", executeQueryPreparedStatement);
        return exRes.getResultSet();
    }

    @Override
    public int size() {
        ExecuteResult exRes = SqlHelper.getPreparedStatement(connectionFactory, "SELECT * FROM resume", executeQueryPreparedStatement);
        return exRes.getResultSet().size();
    }

    private List<Resume> getNewResume(ResultSet resultSet){
        List<Resume> resumes = new ArrayList<>();
        try {
            while (resultSet.next()){
                resumes.add(new Resume(resultSet.getString("full_name"), resultSet.getString("uuid")));
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        return resumes;
    }
}
