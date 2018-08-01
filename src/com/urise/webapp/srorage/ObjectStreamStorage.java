package com.urise.webapp.srorage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.util.List;

public class ObjectStreamStorage extends AbstractStorage{
    private AbstractStorage strategy;

    public ObjectStreamStorage(String dir, int strategy) {
        this.strategy = getInstance(dir, strategy);
    }

    private AbstractStorage getInstance(String dir, int strategy){
        if (strategy == 1) {
            return new AbstractFileStorage(dir) {
                @Override
                public void doWrite(OutputStream os, Resume r) throws IOException {
                    doWriteOb(os, r);
                }

                @Override
                public Resume getResumeFromFile(InputStream is) throws IOException {
                    return getResumeFromFileOb(is);
                }
            };
        }
        else {
            return new AbstractPathStorage(dir) {
                @Override
                public void doWrite(OutputStream os, Resume r) throws IOException {
                    doWriteOb(os, r);
                }

                @Override
                public Resume getResumeFromFile(InputStream is) throws IOException {
                    return getResumeFromFileOb(is);
                }
            };
        }
    }

    private void doWriteOb(OutputStream os, Resume r) throws IOException {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(os)) {
            outputStream.writeObject(r);
        }
    }

    private Resume getResumeFromFileOb(InputStream is) throws IOException{
        try(ObjectInputStream inputStream = new ObjectInputStream(is)) {
            return (Resume) inputStream.readObject();
        }
        catch (ClassNotFoundException  e) {
            throw new StorageException("Error read resume", null, e);
        }
    }

    @Override
    protected boolean updateResume(Object key, Resume r) {
        return strategy.updateResume(key, r);
    }

    @Override
    protected Resume getResume(Object key) {
        return strategy.getResume(key);
    }

    @Override
    protected boolean chekSize() {
        return strategy.chekSize();
    }

    @Override
    protected boolean saveResume(Object key, Resume r) {
        return strategy.saveResume(key, r);
    }

    @Override
    protected boolean deleteResume(Object key) {
        return strategy.deleteResume(key);
    }

    @Override
    protected void clearAllResume() {
        strategy.clearAllResume();
    }

    @Override
    protected List<Resume> getAllResume() {
        return strategy.getAllResume();
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return strategy.getSearchKey(uuid);
    }

    @Override
    public int size() {
        return strategy.size();
    }
}
