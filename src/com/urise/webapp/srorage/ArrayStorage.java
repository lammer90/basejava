package com.urise.webapp.srorage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    public void save(Resume r) {
        if (size == storage.length) {
            System.out.println("The array is full");
            return;
        }

        int i;
        if ((i = getIndex(r.getUuid())) < 0) {
            storage[size] = r;
            size++;
        } else {
            System.out.println("There is already a resume");
        }
    }

    public void delete(String uuid) {
        int i;
        if ((i = getIndex(uuid)) >= 0) {
            storage[i] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Resume not found");
        }
    }

    protected int getIndex(String str) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(str)) {
                return i;
            }
        }
        return -1;
    }
}
