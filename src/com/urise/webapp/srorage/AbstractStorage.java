package com.urise.webapp.srorage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<T> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public void update(Resume r) {
        T key = getSearchKey(r.getUuid());
        if (!updateResume(key, r)) {
            throw new NotExistStorageException(r.getUuid());
        }
        LOG.info("update " + r);
    }

    public Resume get(String uuid) {
        Resume resume;
        T key = getSearchKey(uuid);
        if ((resume = getResume(key)) != null) {
            LOG.info("get " + resume);
            return resume;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public void save(Resume r) {
        T key = getSearchKey(r.getUuid());
        if (chekSize()) {
            throw new StorageException("The array is full", r.getUuid());
        }
        if (!saveResume(key, r)) {
            throw new ExistStorageException(r.getUuid());
        }
        LOG.info("save " + r);
    }

    public void delete(String uuid) {
        T key = getSearchKey(uuid);
        if (!deleteResume(key)) {
            throw new NotExistStorageException(uuid);
        }
        LOG.info("delete " + uuid);
    }

    public void clear() {
        clearAllResume();
        LOG.info("clear");
    }

    public List<Resume> getAllSorted() {
        List<Resume> resumes = getAllResume();
        resumes.sort(Comparator.comparing(Resume::getFullname).thenComparing(Resume::getUuid));
        return resumes;
    }


    protected abstract boolean updateResume(T key, Resume r);

    protected abstract Resume getResume(T key);

    protected abstract boolean chekSize();

    protected abstract boolean saveResume(T key, Resume r);

    protected abstract boolean deleteResume(T key);

    protected abstract void clearAllResume();

    protected abstract List<Resume> getAllResume();

    protected abstract T getSearchKey(String uuid);
}
