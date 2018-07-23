package com.urise.webapp.srorage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {
    public void save(Resume r) {
        if (size == storage.length) {
            System.out.println("The array is full");
            return;
        }

        int i;
        if ((i = getIndex(r.getUuid())) < 0) {
            int min = (-i) - 1;
            for (int j = size + 1; j > min; j--){
                storage[j] = storage[j-1];
            }
            storage[min] = r;
            size++;
        } else {
            System.out.println("There is already a resume");
        }
    }

    public void delete(String uuid) {
        int i;
        if ((i = getIndex(uuid)) >= 0) {
            for (int j = i; j < size; j++){
                storage[j] = storage[j+1];
            }
            size--;
        } else {
            System.out.println("Resume not found");
        }
    }

    protected int getIndex(String str) {
        Resume newResume = new Resume(str);
        return Arrays.binarySearch(storage, 0, size, newResume);
    }
}
