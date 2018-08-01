package com.urise.webapp.srorage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.util.*;

public abstract class AbstractFileStorage extends AbstractStorage<File> implements FileOrPathStrategy{
    protected File directory;

    protected AbstractFileStorage(String dir) {
        directory = new File(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not read/write");
        }
        this.directory = directory;
    }

    @Override
    protected boolean updateResume(File file, Resume r) {
        if (file.exists()) {
            try {
                doWrite(new BufferedOutputStream(new FileOutputStream(file)), r);
            } catch (IOException e) {
                throw new StorageException("IO error", file.getName(), e);
            }
            return true;
        }
        return false;
    }

    @Override
    protected Resume getResume(File file) {
        if (file.exists()) {
            try {
                return getResumeFromFile(new BufferedInputStream(new FileInputStream(file)));
            } catch (IOException  e) {
                throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
            }
        }
        return null;
    }

    @Override
    protected boolean chekSize() {
        return false;
    }

    @Override
    protected boolean saveResume(File file, Resume r) {
        if (!file.exists()) {
            try {
                file.createNewFile();
                doWrite(new BufferedOutputStream(new FileOutputStream(file)), r);
                return true;
            } catch (IOException e) {
                throw new StorageException("IO error", file.getName(), e);
            }
        }
        return false;
    }

    @Override
    protected boolean deleteResume(File file) {
        if (file.exists()) {
            file.delete();
            return true;
        }
        return false;
    }

    @Override
    protected void clearAllResume() {
        File[] arrayOfFiles = directory.listFiles();
        List<File> files = new ArrayList<>(arrayOfFiles.length);
        Collections.addAll(files, arrayOfFiles);

        for (File file : files){
            file.delete();
        }
    }

    @Override
    protected List<Resume> getAllResume() {
        File[] arrayOfFiles = directory.listFiles();
        List<File> files = new ArrayList<>(arrayOfFiles.length);
        Collections.addAll(files, arrayOfFiles);

        List<Resume> resumes = new ArrayList<>();

        for (File file : files){
           resumes.add(getResume(file));
        }
        return resumes;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    public int size() {
        File[] arrayOfFiles = directory.listFiles();
        return arrayOfFiles == null ? 0 : arrayOfFiles.length;
    }

    protected abstract void doWrite(OutputStream os, Resume r) throws IOException;

    protected abstract Resume getResumeFromFile(InputStream is) throws IOException;
}
