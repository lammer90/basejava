package com.urise.webapp.srorage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {
    public void update(Resume r) {
        Object key = getSearchKey(r.getUuid());
        if (!updateResume(key, r)) {
            throw new NotExistStorageException(r.getUuid());
        }
    }

    public Resume get(String uuid) {
        Resume resume;
        Object key = getSearchKey(uuid);
        if ((resume = getResume(key)) != null) {
            return resume;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public void save(Resume r) {
        Object key = getSearchKey(r.getUuid());
        if (chekSize()) {
            throw new StorageException("The array is full", r.getUuid());
        }
        if (!saveResume(key, r)) {
            throw new ExistStorageException(r.getUuid());
        }
    }

    public void delete(String uuid) {
        Object key = getSearchKey(uuid);
        if (!deleteResume(key)) {
            throw new NotExistStorageException(uuid);
        }
    }

    public void clear() {
        clearAllResume();
    }

    public List<Resume> getAllSorted() {
        List<Resume> resumes = getAllResume();
        resumes.sort(Comparator.comparing(Resume::getFullname).thenComparing(Resume::getUuid));
        return resumes;
    }


    protected abstract boolean updateResume(Object key, Resume r);

    protected abstract Resume getResume(Object key);

    protected abstract boolean chekSize();

    protected abstract boolean saveResume(Object key, Resume r);

    protected abstract boolean deleteResume(Object key);

    protected abstract void clearAllResume();

    protected abstract List<Resume> getAllResume();

    protected abstract Object getSearchKey(String uuid);
}
