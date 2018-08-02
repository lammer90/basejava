package com.urise.webapp.srorage.strategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urise.webapp.model.Resume;

import java.io.*;

public class JSONStreamStorage implements StrategySerialization{
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void doWrite(OutputStream os, Resume r) throws IOException {
        try (OutputStreamWriter osw = new OutputStreamWriter(os)) {
            mapper.writeValue(osw, r);
        }
    }

    @Override
    public Resume getResumeFromFile(InputStream is) throws IOException {
        try (InputStreamReader isr = new InputStreamReader(is)) {
            return mapper.readValue(isr, Resume.class);
        }
    }
}
