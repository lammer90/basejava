package com.urise.webapp.srorage;

import com.urise.webapp.model.Resume;

public class MapStorage extends AbstractStorage{
    @Override
    protected boolean updateResume(Resume r) {
        return false;
    }

    @Override
    protected Resume getResume(String uuid) {
        return null;
    }

    @Override
    protected boolean chekSize() {
        return false;
    }

    @Override
    protected boolean saveResume(Resume r) {
        return false;
    }

    @Override
    protected boolean deleteResume(String uuid) {
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
    public int size() {
        return 0;
    }
}
