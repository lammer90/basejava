package com.urise.webapp.srorage;

import com.urise.webapp.model.Resume;

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

    public void save(Resume r) {
        if (size == storage.length) {
            System.out.println("The array is full");
            return;
        }

        int i;
        if ((i = getIndex(r.getUuid())) < 0) {
            insertElement(i, r);
            size++;
        } else {
            System.out.println("There is already a resume");
        }
    }

    public void delete(String uuid) {
        int i;
        if ((i = getIndex(uuid)) >= 0) {
            deleteElement(i);
            size--;
        } else {
            System.out.println("Resume not found");
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
