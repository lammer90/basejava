package com.urise.webapp.srorage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.ToIntFunction;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {
    public Object getSearchKey(String str) {
        Resume newResume = new Resume(str);
        return Arrays.binarySearch(storage, 0, size, newResume, (o1, o2) -> o1.getUuid().compareTo(o2.getUuid()));
    }

    @Override
    public void deleteElement(int i) {
        for (int j = i; j < size; j++){
            storage[j] = storage[j+1];
        }
    }

    @Override
    public void insertElement(int i, Resume r) {
        int min = (-i) - 1;
        for (int j = size; j > min; j--){
            storage[j] = storage[j-1];
        }
        storage[min] = r;
    }
}
