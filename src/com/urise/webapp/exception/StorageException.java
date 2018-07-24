package com.urise.webapp.exception;

public class StorageException extends RuntimeException{
    String uuid;

    public StorageException(String message, String uuid) {
        super(message);
        this.uuid = uuid;
    }
}
