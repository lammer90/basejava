package com.urise.webapp.srorage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    private static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void update(Resume r) {
        int i;
        if ((i = getIndex(r.getUuid())) >= 0) {
            storage[i] = r;
        } else {
            throw new NotExistStorageException(r.getUuid());
        }
    }

    public Resume get(String uuid) {
        int i;
        if ((i = getIndex(uuid)) >= 0) {
            return storage[i];
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public void save(Resume r) {
        if (size == storage.length) {
            throw new StorageException("The array is full", r.getUuid());
        }

        int i;
        if ((i = getIndex(r.getUuid())) < 0) {
            insertElement(i, r);
            size++;
        } else {
            throw new ExistStorageException(r.getUuid());
        }
    }

    public void delete(String uuid){
        int i;
        if ((i = getIndex(uuid)) >= 0) {
            deleteElement(i);
            size--;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    protected abstract void deleteElement(int i);

    protected abstract void insertElement(int i, Resume r);

    protected abstract int getIndex(String uuid);
}
