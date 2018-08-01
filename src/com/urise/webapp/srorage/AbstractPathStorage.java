package com.urise.webapp.srorage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class AbstractPathStorage extends AbstractStorage<Path>{
    protected Path directory;

    public AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException(directory + " is not directory");
        }
        if (!Files.isWritable(directory) || !Files.isReadable(directory)) {
            throw new IllegalArgumentException(directory + " is not write/read");
        }
        this.directory = directory;
    }

    @Override
    protected boolean updateResume(Path path, Resume r) {
        if (Files.exists(path)) {
            try {
                doWrite(new BufferedOutputStream(Files.newOutputStream(path)), r);
                return true;
            } catch (IOException e) {
                throw new StorageException("IO error", path.toString(), e);
            }
        }
        return false;
    }

    @Override
    protected Resume getResume(Path path) {
        if (Files.exists(path)) {
            try {
                return getResumeFromFile(new BufferedInputStream(Files.newInputStream(path)));
            } catch (IOException  e) {
                throw new StorageException("IO error", path.toString(), e);
            }
        }
        return null;
    }

    @Override
    protected boolean chekSize() {
        return false;
    }

    @Override
    protected boolean saveResume(Path path, Resume r) {
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
                doWrite(new BufferedOutputStream(Files.newOutputStream(path)), r);
                return true;
            } catch (IOException e) {
                throw new StorageException("IO error", path.toString(), e);
            }
        }
        return false;
    }

    @Override
    protected boolean deleteResume(Path path) {
        if (Files.exists(path)) {
            try {
                Files.delete(path);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    protected void clearAllResume() {
        try {
            Files.list(directory).forEach(this::deleteResume);
        } catch (IOException e) {
            throw new StorageException("IO Exception", directory.toString(), e);
        }
    }

    @Override
    protected List<Resume> getAllResume() {
        List<Resume> resumes = new ArrayList<>();
        try {
            Files.list(directory).forEach(path -> resumes.add(getResume(path)));
        } catch (IOException e) {
            throw new StorageException("IO Exception", directory.toString(), e);
        }
        return resumes;
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("IO Exception", directory.toString(), e);
        }
    }

    protected abstract void doWrite(OutputStream os, Resume r) throws IOException;

    protected abstract Resume getResumeFromFile(InputStream is) throws IOException;
}
