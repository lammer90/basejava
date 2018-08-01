package com.urise.webapp.srorage.strategy;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;

public interface StrategySerialization {
    void doWrite(OutputStream os, Resume r) throws IOException;
    Resume getResumeFromFile(InputStream is) throws IOException;
}
