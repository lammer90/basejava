package com.urise.webapp.srorage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected int size = 0;
    private static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    protected boolean updateResume(Integer key, Resume r){
        if (key >= 0) {
             storage[(int)key] = r;
             return true;
        }
        return false;
    }

    protected Resume getResume(Integer key){
        if (key >= 0) {
            return storage[(int)key];
        }
        return null;
    }

    protected boolean chekSize(){
        return size == storage.length;
    }

    protected boolean saveResume(Integer key, Resume r){
        if (key < 0) {
            insertElement((int)key, r);
            size++;
            return true;
        }
        return false;
    }

    protected boolean deleteResume(Integer key){
        if (key >= 0) {
            deleteElement((int)key);
            size--;
            return true;
        }
        return false;
    }

    protected void clearAllResume(){
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    protected List<Resume> getAllResume(){
        Resume[] resumes = Arrays.copyOf(storage, size);
        List<Resume> listOfObjects = new ArrayList<>(resumes.length);
        Collections.addAll(listOfObjects, resumes);
        return listOfObjects;
    }

    public int size() {
        return size;
    }


    protected abstract void deleteElement(int i);

    protected abstract void insertElement(int i, Resume r);
}
