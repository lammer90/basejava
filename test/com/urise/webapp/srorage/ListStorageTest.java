package com.urise.webapp.srorage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ListStorageTest extends AbstractStorageTest{
    public ListStorageTest() {
        super(new ListStorage());
    }
}