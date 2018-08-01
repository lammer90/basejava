package com.urise.webapp.srorage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class AbstractPathStorage extends AbstractStorage<Path> implements FileOrPathStrategy{
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
        return false;
    }

    @Override
    protected Resume getResume(Path path) {
        return null;
    }

    @Override
    protected boolean chekSize() {
        return false;
    }

    @Override
    protected boolean saveResume(Path path, Resume r) {
        return false;
    }

    @Override
    protected boolean deleteResume(Path path) {
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
        return null;
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    protected abstract void doWrite(OutputStream os, Resume r) throws IOException;

    protected abstract Resume getResumeFromFile(InputStream is) throws IOException;
}
