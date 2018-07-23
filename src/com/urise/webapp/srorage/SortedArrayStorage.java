package com.urise.webapp.srorage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        int i;
        if ((i = getIndex(r.getUuid())) >= 0) {
            storage[i] = r;
        } else {
            System.out.println("Resume not found");
        }
    }

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

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    protected int getIndex(String str) {
        Resume newResume = new Resume(str);
        return Arrays.binarySearch(storage, 0, size, newResume);
    }
}
