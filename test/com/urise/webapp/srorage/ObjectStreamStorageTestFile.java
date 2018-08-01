package com.urise.webapp.srorage;

import com.urise.webapp.srorage.strategy.ObjectStreamStorage;

public class ObjectStreamStorageTestFile extends AbstractStorageTest{
    public ObjectStreamStorageTestFile() {
        super(new FileStorage(file, new ObjectStreamStorage()));
    }
}