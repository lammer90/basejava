package com.urise.webapp.srorage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.srorage.sql.ConnectionFactory;
import com.urise.webapp.srorage.sql.ExecutePreparedStatement;
import com.urise.webapp.srorage.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final ConnectionFactory connectionFactory;
    private ExecutePreparedStatement<Integer> executePreparedStatement = preparedStatement -> {
        try {
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    };

    private ExecutePreparedStatement<List<Resume>> executeQueryPreparedStatement = preparedStatement -> {
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            return getNewResume(resultSet);
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
        if ((Integer)SqlHelper.getPreparedStatement(connectionFactory, "UPDATE resume SET full_name =? WHERE uuid =?", executePreparedStatement, r.getFullname(), r.getUuid()) == 0) {
            throw new NotExistStorageException(r.getUuid());
        }
    }

    @Override
    public void save(Resume r) {
        List<Resume> resumes = SqlHelper.getPreparedStatement(connectionFactory, "SELECT * FROM resume r WHERE r.uuid=?", executeQueryPreparedStatement, r.getUuid());
        if (resumes.size() != 0) {
            throw new ExistStorageException(r.getUuid());
        }
        SqlHelper.getPreparedStatement(connectionFactory, "INSERT INTO resume (uuid, full_name) VALUES (?, ?)", executePreparedStatement, r.getUuid(), r.getFullname());
    }

    @Override
    public Resume get(String uuid) {
        List<Resume> resumes = SqlHelper.getPreparedStatement(connectionFactory, "SELECT * FROM resume r WHERE r.uuid=?", executeQueryPreparedStatement, uuid);
        if (resumes.size() == 0) {
            throw new NotExistStorageException(uuid);
        }
        return resumes.get(0);
    }

    @Override
    public void delete(String uuid) {
        if ((Integer)SqlHelper.getPreparedStatement(connectionFactory, "DELETE FROM resume r WHERE r.uuid=?", executePreparedStatement, uuid) == 0) {
            throw new ExistStorageException(uuid);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        return SqlHelper.getPreparedStatement(connectionFactory, "SELECT * FROM resume", executeQueryPreparedStatement);
    }

    @Override
    public int size() {
        List<Resume> resumes = SqlHelper.getPreparedStatement(connectionFactory, "SELECT * FROM resume", executeQueryPreparedStatement);
        return resumes.size();
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
