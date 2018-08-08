package com.urise.webapp.exception;

import java.io.IOException;
import java.sql.SQLException;

public class StorageException extends RuntimeException{
    String uuid;

    public StorageException(String message, String uuid) {
        super(message);
        this.uuid = uuid;
    }

    public StorageException(String message, String uuid, Exception e) {
        super(message, e);
        this.uuid = uuid;
    }

    public StorageException(Exception e) {
        super(e.getMessage(), e);
    }
}
