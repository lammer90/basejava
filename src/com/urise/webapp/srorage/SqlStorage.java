package com.urise.webapp.srorage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Contacts;
import com.urise.webapp.model.Resume;
import com.urise.webapp.srorage.sql.ConnectionFactory;
import com.urise.webapp.srorage.sql.ExecutePreparedStatement;
import com.urise.webapp.srorage.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final ConnectionFactory connectionFactory;

    private GetResumeInterface getResumeInterface = this::getOnlyResume;

    private GetResumeInterface getResumeInterfaceContacts = resultSet -> {
        List<Resume> res = getOnlyResume(resultSet);

        return res;
    };

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
            return getResumeInterface.getNewResume(resultSet);
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
        int finalResult = SqlHelper.getTransactionPreparedStatement(connectionFactory, connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE resume SET full_name =? WHERE uuid =?");
            preparedStatement.setString(1, r.getFullname());
            preparedStatement.setString(2, r.getUuid());
            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                PreparedStatement ps = connection.prepareStatement("UPDATE contact SET value =? WHERE resume_uuid =? AND type =?");
                for (Map.Entry<Contacts, String> e : r.getContacts().entrySet()) {
                    ps.setString(1, e.getValue());
                    ps.setString(2, r.getUuid());
                    ps.setString(3, e.getKey().name());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            return result;
        });

        if (finalResult == 0) {
            throw new NotExistStorageException(r.getUuid());
        }

        /*if (SqlHelper.getPreparedStatement(connectionFactory, "UPDATE resume SET full_name =? WHERE uuid =?", executePreparedStatement, r.getFullname(), r.getUuid()) == 0) {
            throw new NotExistStorageException(r.getUuid());
        }*/
    }

    @Override
    public void save(Resume r) {
        List<Resume> resumes = SqlHelper.getPreparedStatement(connectionFactory, "SELECT * FROM resume r WHERE r.uuid=?", executeQueryPreparedStatement, r.getUuid());
        if (resumes.size() != 0) {
            throw new ExistStorageException(r.getUuid());
        }
        SqlHelper.getTransactionPreparedStatement(connectionFactory, connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)");
            preparedStatement.setString(1, r.getUuid());
            preparedStatement.setString(2, r.getFullname());
            preparedStatement.execute();

            PreparedStatement ps = connection.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)");
            for (Map.Entry<Contacts, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
            return true;
        });
    }

    @Override
    public Resume get(String uuid) {
        List<Resume> resumes = SqlHelper.getTransactionPreparedStatement(connectionFactory, connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "    SELECT * FROM resume r " +
                    " LEFT JOIN contact c " +
                    "        ON r.uuid = c.resume_uuid " +
                    "     WHERE r.uuid =? ");
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getResumeInterfaceContacts.getNewResume(resultSet);
        });

        if (resumes.size() == 0) {
            throw new NotExistStorageException(uuid);
        }
        return resumes.get(0);

        /*List<Resume> resumes = SqlHelper.getPreparedStatement(connectionFactory, "SELECT * FROM resume r WHERE r.uuid=?", executeQueryPreparedStatement, uuid);
        if (resumes.size() == 0) {
            throw new NotExistStorageException(uuid);
        }
        return resumes.get(0);*/
    }

    @Override
    public void delete(String uuid) {
        if (SqlHelper.getPreparedStatement(connectionFactory, "DELETE FROM resume r WHERE r.uuid=?", executePreparedStatement, uuid) == 0) {
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

    private interface GetResumeInterface {
        List<Resume> getNewResume(ResultSet resultSet);
    }

    private List<Resume> getOnlyResume(ResultSet resultSet){
        List<Resume> res = new ArrayList<>();
        try {
            while (resultSet.next()) {
                res.add(new Resume(resultSet.getString("full_name"), resultSet.getString("uuid")));
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        return res;
    }
}
