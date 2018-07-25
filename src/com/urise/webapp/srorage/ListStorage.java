package com.urise.webapp.srorage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;


public class ListStorage extends AbstractStorage{
    protected List<Resume> storage = new ArrayList<>();

    @Override
    protected boolean updateResume(Resume r) {
        int i;
        if ((i = getIndex(r.getUuid())) >= 0) {
            storage.set(i, r);
            return true;
        }
        return false;
    }

    @Override
    protected Resume getResume(String uuid) {
        int i;
        if ((i = getIndex(uuid)) >= 0) {
            return storage.get(i);
        }
        return null;
    }

    @Override
    protected boolean chekSize() {
        return false;
    }

    @Override
    protected boolean saveResume(Resume r) {
        int i;
        if ((i = getIndex(r.getUuid())) < 0) {
            storage.add(r);
            return true;
        }
        return false;
    }

    @Override
    protected boolean deleteResume(String uuid) {
        int i;
        if ((i = getIndex(uuid)) >= 0) {
            storage.remove(i);
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

    protected int getIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
