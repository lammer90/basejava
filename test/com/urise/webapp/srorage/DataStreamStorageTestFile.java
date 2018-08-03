package com.urise.webapp.srorage;

import com.urise.webapp.srorage.strategy.DataStreamSerializer;

public class DataStreamStorageTestFile extends AbstractStorageTest{
    public DataStreamStorageTestFile() {
        super(new FileStorage(file, new DataStreamSerializer()));
    }
}