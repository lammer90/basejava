package com.urise.webapp.srorage;

import com.urise.webapp.srorage.strategy.ObjectStreamStorage;

public class ObjectStreamStorageTestPath extends AbstractStorageTest{
    public ObjectStreamStorageTestPath() {
        super(new PathStorage(file, new ObjectStreamStorage()));
    }
}