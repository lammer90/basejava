package com.urise.webapp.srorage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Contacts;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.section.ArraySection;
import com.urise.webapp.model.section.Section;
import com.urise.webapp.model.section.SectionType;
import com.urise.webapp.model.section.StringSection;
import com.urise.webapp.srorage.sql.ConnectionFactory;
import com.urise.webapp.srorage.sql.ExecutePreparedStatement;
import com.urise.webapp.srorage.sql.GetResumeInterface;
import com.urise.webapp.srorage.sql.SqlHelper;
import com.urise.webapp.util.JSONConverter;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final ConnectionFactory connectionFactory;

    private GetResumeInterface getResumeInterface = resultSet -> {
        Map<String,Resume> resMap = getOnlyResume(resultSet);
        return new ArrayList<>(resMap.values());
    };

    private GetResumeInterface getResumeInterfaceAll = resultSet -> {
        Map<String,Resume> resMap = getOnlyResume(resultSet);
        addContactsToResume(resMap, resultSet);
        addSectionToResume(resMap, resultSet);
        return new ArrayList<>(resMap.values());
    };

    private ExecutePreparedStatement<Integer> executePreparedStatement = (preparedStatement, getResumeInterface) -> {
        try {
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    };

    private ExecutePreparedStatement<List<Resume>> executeQueryPreparedStatement = (preparedStatement, getResumeInterface) -> {
        try {
            ResultSet newResultSet = preparedStatement.executeQuery();
            return getResumeInterface.getNewResume(newResultSet);
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
        SqlHelper.getPreparedStatement(connectionFactory, "DELETE FROM resume", executePreparedStatement, getResumeInterface);
    }

    @Override
    public void update(Resume r) {
        int finalResult = SqlHelper.getTransactionPreparedStatement(connectionFactory, connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE resume SET full_name =? WHERE uuid =?");
            addParametrs(preparedStatement, r.getFullname(), r.getUuid());
            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                PreparedStatement ps = connection.prepareStatement("UPDATE contact SET value =? WHERE resume_uuid =? AND type =?");
                for (Map.Entry<Contacts, String> e : r.getContacts().entrySet()) {
                    addParametrs(ps, e.getValue(), r.getUuid(), e.getKey().name());
                    ps.addBatch();
                }
                ps.executeBatch();

                PreparedStatement psSection = connection.prepareStatement("UPDATE section SET value =? WHERE resume_uuid =? AND type =?");
                addSection(r, psSection);
                psSection.executeBatch();
            }
            return result;
        });

        if (finalResult == 0) {
            throw new NotExistStorageException(r.getUuid());
        }
    }

    @Override
    public void save(Resume r) {
        List<Resume> resumes = SqlHelper.getPreparedStatement(connectionFactory, "SELECT * FROM resume r WHERE r.uuid=?", executeQueryPreparedStatement, getResumeInterface, r.getUuid());
        if (resumes.size() != 0) {
            throw new ExistStorageException(r.getUuid());
        }
        SqlHelper.getTransactionPreparedStatement(connectionFactory, connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)");
            addParametrs(preparedStatement, r.getUuid(), r.getFullname());
            preparedStatement.execute();

            PreparedStatement ps = connection.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)");
            for (Map.Entry<Contacts, String> e : r.getContacts().entrySet()) {
                addParametrs(ps, r.getUuid(), e.getKey().name(), e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();

            PreparedStatement psSection = connection.prepareStatement("INSERT INTO section (value, resume_uuid, type) VALUES (?,?,?)");
            addSection(r, psSection);
            psSection.executeBatch();
            return true;
        });
    }

    private void addSection(Resume r, PreparedStatement ps) throws SQLException {
        for (Map.Entry<SectionType, Section> e : r.getSections().entrySet()) {
            /*if(e.getValue() instanceof StringSection){
                addParametrs(ps, ((StringSection) e.getValue()).getInformation(), r.getUuid(), e.getKey().name());
            }
            else if(e.getValue() instanceof ArraySection){
                addParametrs(ps, String.join ( System.lineSeparator(), ((ArraySection) e.getValue()).getInformation()), r.getUuid(), e.getKey().name());
            }*/
            try {
                addParametrs(ps, JSONConverter.write(e.getValue()), r.getUuid(), e.getKey().name());
            } catch (IOException e1) {
                throw new StorageException(e1);
            }
            ps.addBatch();
        }
    }

    private void addParametrs(PreparedStatement preparedStatement, String... strings){
        for (int i = 0; i < strings.length; i++) {
            try {
                preparedStatement.setString(i + 1, strings[i]);
            } catch (SQLException e) {
                throw new StorageException(e);
            }
        }
    }

    @Override
    public Resume get(String uuid) {
        String sqlQuery = "" +
                "    SELECT r.uuid, r.full_name," +
                "c.type, c.value, c.resume_uuid," +
                "s.type as typeS, s.value as valueS, s.resume_uuid as resume_uuidS" +
                " FROM resume r " +
                " LEFT JOIN contact c " +
                "        ON r.uuid = c.resume_uuid " +
                " LEFT JOIN section s " +
                "        ON r.uuid = s.resume_uuid " +
                "     WHERE r.uuid =? ";
        List<Resume> resumes = SqlHelper.getPreparedStatement(connectionFactory, sqlQuery, executeQueryPreparedStatement, getResumeInterfaceAll,uuid);
        if (resumes.size() == 0) {
            throw new NotExistStorageException(uuid);
        }
        return resumes.get(0);
    }

    @Override
    public void delete(String uuid) {
        if (SqlHelper.getPreparedStatement(connectionFactory, "DELETE FROM resume r WHERE r.uuid=?", executePreparedStatement, getResumeInterface, uuid) == 0) {
            throw new ExistStorageException(uuid);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        String sqlQuery = "" +
                "    SELECT r.uuid, r.full_name," +
                "c.type, c.value, c.resume_uuid," +
                "s.type as typeS, s.value as valueS, s.resume_uuid as resume_uuidS" +
                " FROM resume r " +
                " LEFT JOIN contact c " +
                "        ON r.uuid = c.resume_uuid " +
                " LEFT JOIN section s " +
                "        ON r.uuid = s.resume_uuid ";
        return SqlHelper.getPreparedStatement(connectionFactory, sqlQuery, executeQueryPreparedStatement, getResumeInterfaceAll);
    }

    @Override
    public int size() {
        List<Resume> resumes = SqlHelper.getPreparedStatement(connectionFactory, "SELECT * FROM resume", executeQueryPreparedStatement, getResumeInterface);
        return resumes.size();
    }

    private Map<String,Resume> getOnlyResume(ResultSet resultSet){
        Map<String,Resume> res = new HashMap<>();
        try {
            while (resultSet.next()) {
                if (!res.containsKey(resultSet.getString("uuid"))) {
                    res.put(resultSet.getString("uuid"), new Resume(resultSet.getString("full_name"), resultSet.getString("uuid")));
                }
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        return res;
    }

    private Map<String,Resume> addContactsToResume(Map<String,Resume> res, ResultSet resultSet){
        try {
            resultSet.beforeFirst();
            while (resultSet.next()) {
                String key = resultSet.getString("resume_uuid");
                if (!res.get(key).getContacts().containsKey(Contacts.valueOf(resultSet.getString("type")))) {
                    res.get(key).addContact(Contacts.valueOf(resultSet.getString("type")), resultSet.getString("value"));
                }
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        return res;
    }

    private Map<String,Resume> addSectionToResume(Map<String,Resume> res, ResultSet resultSet){
        try {
            resultSet.beforeFirst();
            while (resultSet.next()) {
                String key = resultSet.getString("resume_uuidS");

                if (!res.get(key).getSections().containsKey(SectionType.valueOf(resultSet.getString("typeS")))){
                    try {
                        Class tClass = SectionType.valueOf(resultSet.getString("typeS")).getaClass();
                        res.get(key).addSectionn(SectionType.valueOf(resultSet.getString("typeS")), JSONConverter.read(resultSet.getString("valueS"), Section.class));
                    } catch (IOException e) {
                        throw new StorageException(e);
                    }
                }

                /*if (SectionType.valueOf(resultSet.getString("typeS")).getaClass() == StringSection.class &&
                        !res.get(key).getSections().containsKey(SectionType.valueOf(resultSet.getString("typeS")))) {
                    res.get(key).addSectionn(SectionType.valueOf(resultSet.getString("typeS")), new StringSection(resultSet.getString("valueS")));
                }
                else if (SectionType.valueOf(resultSet.getString("typeS")).getaClass() == ArraySection.class &&
                        !res.get(key).getSections().containsKey(SectionType.valueOf(resultSet.getString("typeS")))){
                    res.get(key).addSectionn(SectionType.valueOf(resultSet.getString("typeS")), new ArraySection(resultSet.getString("valueS").split(System.lineSeparator())));
                }*/
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        return res;
    }
}
