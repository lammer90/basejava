package com.urise.webapp.srorage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    public int getIndex(String str) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(str)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void deleteElement(int i) {
        storage[i] = storage[size - 1];
        storage[size - 1] = null;
    }

    @Override
    public void insertElement(int i, Resume r) {
        storage[size] = r;
    }
}
