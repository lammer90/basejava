package com.urise.webapp.srorage;

import com.urise.webapp.srorage.strategy.XMLStreamStorage;

public class XMLStreamStorageTestFile extends AbstractStorageTest{
    public XMLStreamStorageTestFile() {
        super(new FileStorage(file, new XMLStreamStorage()));
    }
}