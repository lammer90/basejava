package com.urise.webapp.exception;

import java.io.IOException;

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
}
