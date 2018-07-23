package com.urise.webapp.srorage;

import com.urise.webapp.model.Resume;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        int i;
        if ((i = chekResume(r.getUuid())) >= 0) {
            storage[i] = r;
        }
        else {
            System.out.println("Resume not found");
        }
    }

    public void save(Resume r) {
        if (size == storage.length){
            System.out.println("The array is full");
            return;
        }

        int i;
        if ((i = chekResume(r.getUuid())) < 0) {
            storage[size] = r;
            size++;
        }
        else {
            System.out.println("There is already a resume");
        }
    }

    public Resume get(String uuid) {
        int i;
        if ((i = chekResume(uuid)) >= 0) {
            return storage[i];
        }
        else {
            System.out.println("Resume not found");
            return null;
        }
    }

    public void delete(String uuid) {
        int i;
        if ((i = chekResume(uuid)) >= 0) {
            storage[i] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
        else {
            System.out.println("Resume not found");
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int chekResume(String str){
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(str)) {
                return i;
            }
        }
        return -1;
    }
}
