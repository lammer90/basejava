package com.urise.webapp.srorage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected int size = 0;
    private static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    protected boolean updateResume(Object key, Resume r){
        if ((int)key >= 0) {
             storage[(int)key] = r;
             return true;
        }
        return false;
    }

    protected Resume getResume(Object key){
        if ((int)key >= 0) {
            return storage[(int)key];
        }
        return null;
    }

    protected boolean chekSize(){
        return size == storage.length;
    }

    protected boolean saveResume(Object key, Resume r){
        if ((int)key < 0) {
            insertElement((int)key, r);
            size++;
            return true;
        }
        return false;
    }

    protected boolean deleteResume(Object key){
        if ((int)key >= 0) {
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

    protected Resume[] getAllResume(){
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }


    protected abstract void deleteElement(int i);

    protected abstract void insertElement(int i, Resume r);
}
