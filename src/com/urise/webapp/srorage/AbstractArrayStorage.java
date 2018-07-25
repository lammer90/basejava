package com.urise.webapp.srorage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    private static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    protected boolean updateResume(Resume r){
        int i;
        if ((i = getIndex(r.getUuid())) >= 0) {
             storage[i] = r;
             return true;
        }
        return false;
    }

    protected Resume getResume(String uuid){
        int i;
        if ((i = getIndex(uuid)) >= 0) {
            return storage[i];
        }
        return null;
    }

    protected boolean chekSize(){
        return size == storage.length;
    }

    protected boolean saveResume(Resume r){
        int i;
        if ((i = getIndex(r.getUuid())) < 0) {
            insertElement(i, r);
            return true;
        }
        return false;
    }

    protected boolean deleteResume(String uuid){
        int i;
        if ((i = getIndex(uuid)) >= 0) {
            deleteElement(i);
            return true;
        }
        return false;
    }

    protected void clearAllResume(){
        Arrays.fill(storage, 0, size, null);
    }

    protected Resume[] getAllResume(){
        return Arrays.copyOf(storage, size);
    }


    protected abstract void deleteElement(int i);

    protected abstract void insertElement(int i, Resume r);

    protected abstract int getIndex(String uuid);
}
