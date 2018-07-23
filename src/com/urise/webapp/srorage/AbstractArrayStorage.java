package com.urise.webapp.srorage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    private static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

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

    public void update(Resume r) {
        int i;
        if ((i = getIndex(r.getUuid())) >= 0) {
            storage[i] = r;
        } else {
            System.out.println("Resume not found");
        }
    }

    public Resume get(String uuid) {
        int i;
        if ((i = getIndex(uuid)) >= 0) {
            return storage[i];
        } else {
            System.out.println("Resume not found");
            return null;
        }
    }

    public abstract void save(Resume r);

    public abstract void delete(String uuid);

    protected abstract int getIndex(String uuid);
}
