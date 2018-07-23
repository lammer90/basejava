package com.urise.webapp.srorage;

import com.urise.webapp.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    private static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
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

    protected abstract int getIndex(String uuid);
}
