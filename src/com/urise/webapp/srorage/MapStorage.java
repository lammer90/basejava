package com.urise.webapp.srorage;

import com.urise.webapp.model.Resume;

public class MapStorage extends AbstractStorage{
    @Override
    protected boolean updateResume(Object key, Resume r) {
        return false;
    }

    @Override
    protected Resume getResume(Object key) {
        return null;
    }

    @Override
    protected boolean chekSize() {
        return false;
    }

    @Override
    protected boolean saveResume(Object key, Resume r) {
        return false;
    }

    @Override
    protected boolean deleteResume(Object key) {
        return false;
    }

    @Override
    protected void clearAllResume() {

    }

    @Override
    protected Resume[] getAllResume() {
        return new Resume[0];
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
