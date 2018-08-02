package com.urise.webapp.srorage;

import com.urise.webapp.srorage.strategy.JSONStreamStorage;

public class JSONStreamStorageTestFile extends AbstractStorageTest{
    public JSONStreamStorageTestFile() {
        super(new FileStorage(file, new JSONStreamStorage()));
    }
}