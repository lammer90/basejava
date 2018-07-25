package com.urise.webapp.srorage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractStorage implements Storage{
    protected int size = 0;

    public void update(Resume r) {
        if (!updateResume(r)){
            throw new NotExistStorageException(r.getUuid());
        }
    }

    public Resume get(String uuid) {
        Resume resume;
        if ((resume = getResume(uuid)) != null) {
            return resume;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public void save(Resume r) {
        if (chekSize()) {
            throw new StorageException("The array is full", r.getUuid());
        }
        if (saveResume(r)) {
            size++;
        }
        else {
            throw new ExistStorageException(r.getUuid());
        }
    }

    public void delete(String uuid){
        if (deleteResume(uuid)){
            size--;
        }
        else {
            throw new NotExistStorageException(uuid);
        }
    }

    public void clear() {
        clearAllResume();
        size = 0;
    }

    public Resume[] getAll() {
        return getAllResume();
    }

    public int size() {
        return size;
    }


    protected abstract boolean updateResume(Resume r);

    protected abstract Resume getResume(String uuid);

    protected abstract boolean chekSize();

    protected abstract boolean saveResume(Resume r);

    protected abstract boolean deleteResume(String uuid);

    protected abstract void clearAllResume();

    protected abstract Resume[] getAllResume();
}
