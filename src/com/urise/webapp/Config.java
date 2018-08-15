package com.urise.webapp;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.srorage.SqlStorage;
import com.urise.webapp.srorage.Storage;
import org.postgresql.Driver;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Config {
    private static Config ourInstance = new Config();

    public static Config getInstance() {
        return ourInstance;
    }

    public Storage initStorage(){
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        return new SqlStorage("jdbc:postgresql://localhost:5432/resumes", "postgres", "1234lammer");
    }

    private Config() {
    }
}
