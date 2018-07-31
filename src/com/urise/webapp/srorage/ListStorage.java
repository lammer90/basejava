package com.urise.webapp.srorage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;


public class ListStorage extends AbstractStorage<Integer>{
    protected List<Resume> storage = new ArrayList<>();

    @Override
    protected boolean updateResume(Integer key, Resume r) {
        if (key >= 0) {
            storage.set(key, r);
            return true;
        }
        return false;
    }

    @Override
    protected Resume getResume(Integer key) {
        if (key >= 0) {
            return storage.get(key);
        }
        return null;
    }

    @Override
    protected boolean chekSize() {
        return false;
    }

    @Override
    protected boolean saveResume(Integer key, Resume r) {
        if (key < 0) {
            storage.add(r);
            return true;
        }
        return false;
    }

    @Override
    protected boolean deleteResume(Integer key) {
        if (key >= 0) {
            storage.remove((int)key);
            return true;
        }
        return false;
    }

    @Override
    protected void clearAllResume() {
        storage.clear();
    }

    @Override
    protected List<Resume> getAllResume() {
        return storage;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
