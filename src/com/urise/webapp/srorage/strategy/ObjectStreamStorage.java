package com.urise.webapp.srorage.strategy;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;

public class ObjectStreamStorage implements StrategySerialization {
    public void doWrite(OutputStream os, Resume r) throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(os)) {
            outputStream.writeObject(r);
        }
    }

    public Resume getResumeFromFile(InputStream is) throws IOException {
        try (ObjectInputStream inputStream = new ObjectInputStream(is)) {
            return (Resume) inputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }
}
