package com.urise.webapp.srorage;

import static org.junit.Assert.*;

public class ObjectStreamStorageTestPath extends AbstractStorageTest{
    public ObjectStreamStorageTestPath() {
        super(new ObjectStreamStorage(file, 0));
    }
}