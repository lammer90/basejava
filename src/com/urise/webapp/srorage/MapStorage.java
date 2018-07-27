package com.urise.webapp.srorage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage<String>{
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    protected boolean updateResume(String key, Resume r) {
        String uuid = key;
        if (storage.containsKey(uuid)){
            storage.put(uuid, r);
            return true;
        }
        return false;
    }

    @Override
    protected Resume getResume(String key) {
        String uuid = key;
        if (storage.containsKey(uuid)){
            return storage.get(uuid);
        }
        return null;
    }

    @Override
    protected boolean chekSize() {
        return false;
    }

    @Override
    protected boolean saveResume(String key, Resume r) {
        String uuid = key;
        if (!storage.containsKey(uuid)){
            storage.put(uuid, r);
            return true;
        }
        return false;
    }

    @Override
    protected boolean deleteResume(String key) {
        String uuid = key;
        if (storage.containsKey(uuid)){
            storage.remove(uuid);
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
        return new ArrayList<Resume>(storage.values());
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
