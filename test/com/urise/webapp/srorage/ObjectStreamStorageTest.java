package com.urise.webapp.srorage;

import static org.junit.Assert.*;

public class ObjectStreamStorageTest extends AbstractStorageTest{
    public ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(file));
    }
}