package com.urise.webapp.srorage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;


public class ListStorage extends AbstractStorage{
    protected List<Resume> storage = new ArrayList<>();

    @Override
    protected boolean updateResume(Object key, Resume r) {
        if ((int)key >= 0) {
            storage.set((int)key, r);
            return true;
        }
        return false;
    }

    @Override
    protected Resume getResume(Object key) {
        if ((int)key >= 0) {
            return storage.get((int)key);
        }
        return null;
    }

    @Override
    protected boolean chekSize() {
        return false;
    }

    @Override
    protected boolean saveResume(Object key, Resume r) {
        if ((int)key < 0) {
            storage.add(r);
            return true;
        }
        return false;
    }

    @Override
    protected boolean deleteResume(Object key) {
        if ((int)key >= 0) {
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
    protected Resume[] getAllResume() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    protected Object getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
