package com.urise.webapp.srorage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {
    protected int getIndex(String str) {
        Resume newResume = new Resume(str);
        return Arrays.binarySearch(storage, 0, size, newResume);
    }

    @Override
    protected void deleteElement(int i) {
        for (int j = i; j < size; j++){
            storage[j] = storage[j+1];
        }
    }

    @Override
    protected void insertElement(int i, Resume r) {
        int min = (-i) - 1;
        for (int j = size + 1; j > min; j--){
            storage[j] = storage[j-1];
        }
        storage[min] = r;
    }
}
