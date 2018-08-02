package com.urise.webapp.srorage;

import com.urise.webapp.srorage.strategy.XMLStreamStorage;

public class XMLStreamStorageTestPath extends AbstractStorageTest{
    public XMLStreamStorageTestPath() {
        super(new PathStorage(file, new XMLStreamStorage()));
    }
}