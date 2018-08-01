package com.urise.webapp.srorage;

import static org.junit.Assert.*;

public class ObjectStreamStorageTestFile extends AbstractStorageTest{
    public ObjectStreamStorageTestFile() {
        super(new ObjectStreamStorage(file, 1));
    }
}