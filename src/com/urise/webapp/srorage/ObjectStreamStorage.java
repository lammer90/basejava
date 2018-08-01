package com.urise.webapp.srorage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;

public class ObjectStreamStorage extends AbstractFileStorage{
    public FileOrPathStrategy strategy;

    public ObjectStreamStorage(String directory) {
        super(directory);
    }

    @Override
    protected void doWrite(OutputStream os, Resume r) throws IOException {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(os)) {
            outputStream.writeObject(r);
        }
    }

    @Override
    protected Resume getResumeFromFile(InputStream is) throws IOException{
        try(ObjectInputStream inputStream = new ObjectInputStream(is)) {
            return (Resume) inputStream.readObject();
        }
        catch (ClassNotFoundException  e) {
            throw new StorageException("Error read resume", null, e);
        }
    }
}
