package com.urise.webapp.exception;

public class ExistStorageException extends StorageException{
    public ExistStorageException(String uuid) {
        super("There is already a resume", uuid);
    }
}
